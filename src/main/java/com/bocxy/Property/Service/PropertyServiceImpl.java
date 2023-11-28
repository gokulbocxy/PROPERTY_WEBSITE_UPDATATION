package com.bocxy.Property.Service;
import com.bocxy.Property.Entity.*;
import com.bocxy.Property.Model.*;
import com.bocxy.Property.Repository.*;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


@Service
public class PropertyServiceImpl implements PropertyService {
    @Value("${upload.dir}")
    private String uploadDir;
    private final AllotteeRepo allotteeRepo;
    private final SchemeDataRepo schemeDataRepo;
    private final UnitDataRepo unitDataRepo;
    private final SalesDeedDataRepo saleddeeddataRepo;
    private final WebsiteDataRepo websiteDataRepo;
    private final EnquiryRepo enquiryRepo;
    private final Financial_Calc_Repo financial_calc_repo;
    private final FinancialRepo financialRepo;
    private final CollectionRepo collectionRepo;
    private String cronExpression;
    private Financial_Calc financialCalc;
    private Financial financial;
    private Financial financialDetails;
    private CustomerApplicationRepo customerApplicationRepo;
    private SequentialNumberRepository sequenceNumberRepository;
    private CAllotteeRepos cAllotteeRepos;


    @Autowired
    public PropertyServiceImpl(AllotteeRepo allotteeRepo, SchemeDataRepo schemeDataRepo, UnitDataRepo unitDataRepo,
                               SalesDeedDataRepo salesdeeddataRepo, WebsiteDataRepo websiteDataRepo, EnquiryRepo enquiryRepo,
                               Financial_Calc_Repo financial_calc_repo, FinancialRepo financialRepo, CollectionRepo collectionRepo,
                               CustomerApplicationRepo customerApplicationRepo,SequentialNumberRepository sequenceNumberRepository,CAllotteeRepos cAllotteeRepos) {
        this.allotteeRepo = allotteeRepo;
        this.schemeDataRepo = schemeDataRepo;
        this.unitDataRepo = unitDataRepo;
        this.saleddeeddataRepo = salesdeeddataRepo;
        this.websiteDataRepo = websiteDataRepo;
        this.enquiryRepo = enquiryRepo;
        this.financial_calc_repo = financial_calc_repo;
        this.financialRepo = financialRepo;
        this.collectionRepo = collectionRepo;
        this.customerApplicationRepo = customerApplicationRepo;
        this.sequenceNumberRepository=sequenceNumberRepository;
        this.cAllotteeRepos=cAllotteeRepos;
    }

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public List<Allottee> getAllotteesBySchemeId(Long schemeId) {
        return allotteeRepo.findAllByNSchemeId(schemeId);
    }

    @Transactional
    @Override
    public List<Allottee> saveAllottees(List<Allottee> allottees) {
        List<Allottee> savedAllottees = new ArrayList<>();

        for (Allottee allottee : allottees) {
            if (allottee.getV_ALLOTTE_FILE() != null) {
                String base64FileData = allottee.getV_ALLOTTE_FILE();
                byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
                String generatedFileName = allottee.getV_ALLOTTE_FILE_NAME();
                String uniqueFileName = UUID.randomUUID().toString() + "_" + generatedFileName;
                File dest = new File(uploadDir, uniqueFileName);

                try {
                    Files.write(dest.toPath(), decodedFileData);
                    allottee.setV_ALLOTTE_FILE_NAME(generatedFileName);
                    allottee.setV_ALLOTTE_FILE_PATH(dest.getAbsolutePath());
                } catch (IOException e) {

                }
            }
            if (allottee.getV_AADHAAR_FILE() != null) {
                String base64FileData = allottee.getV_AADHAAR_FILE();
                byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
                String generatedFileName = allottee.getV_AADHAAR_FILE_NAME();
                String uniqueFileName = UUID.randomUUID().toString() + "_" + generatedFileName;
                File dest = new File(uploadDir, uniqueFileName);

                try {
                    Files.write(dest.toPath(), decodedFileData);
                    allottee.setV_AADHAAR_FILE_NAME(generatedFileName);
                    allottee.setV_AADHAAR_FILE_PATH(dest.getAbsolutePath());
                } catch (IOException e) {

                }
            }
            if (allottee.getV_OTHER_FILE() != null) {
                String base64FileData = allottee.getV_OTHER_FILE();
                byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
                String generatedFileName = allottee.getV_OTHER_FILE_NAME();
                String uniqueFileName = UUID.randomUUID().toString() + "_" + generatedFileName;
                File dest = new File(uploadDir, uniqueFileName);

                try {
                    Files.write(dest.toPath(), decodedFileData);
                    allottee.setV_OTHER_FILE_NAME(generatedFileName);
                    allottee.setV_OTHER_FILE_PATH(dest.getAbsolutePath());
                } catch (IOException e) {

                }
            }
            if (allottee.getNID() != null) {
                System.out.println(allottee.getNID());
                List<Allottee> existingAllottees = allotteeRepo.findByNID(allottee.getNID());

                for (Allottee existingAllottee : existingAllottees) {
                    // Check if EMAILID is being updated
                    System.out.println(allottee.getEMAILID());
                    System.out.println(existingAllottee.getEMAILID());
                    if (!allottee.getEMAILID().equals(existingAllottee.getEMAILID())) {
                        System.out.println(allottee.getEMAILID());
                        // Send email for email update
                        sendEmailForEmailUpdate(allottee);
                    }
                }
            } else {
                sendEmailForNewAllottee(allottee);
            }

            Allottee savedAllottee = allotteeRepo.save(allottee);
            savedAllottees.add(savedAllottee);


        }

        return savedAllottees;
    }

    //EMAIL CONTENTS

    private void sendEmailForNewAllottee(Allottee allottee) {
        String emailSubject = "TNHB - Property Management Dashboard Registration";
        String emailContent = "Hi Sir/Madam,\n\n" +
                "Dear " + allottee.getV_ALLOTTEE_NAME() + ",,,\n\n" + "Your details have been registered in TamilNadu Housing Board for buying the property. Please visit your Dashboard through the below link:\n\n" +
                "URL - \"https://www.bocxy.com/\".\n\n" +
                "Note: Your registered email is your Username.\n\n" +
                "Thanks & Regards,\n" +
                "TamilNadu Housing Board.";

        sendEmailNotification(allottee.getEMAILID(), emailSubject, emailContent);
    }

    private void sendEmailForEmailUpdate(Allottee allottee) {
        String emailSubject = "TNHB - Property Management Dashboard Login Details Update";
        String emailContent = "Hi Sir/Madam,\n\n" +
                "Dear " + allottee.getV_ALLOTTEE_NAME() + ", your login details have been updated in TamilNadu Housing Board for buying the property. Please visit your Dashboard through the below link:\n\n" +
                "URL - \"https://www.bocxy.com/\".\n\n" +
                "Note: Your registered email is your Username.\n\n" +
                "Thanks & Regards,\n" +
                "TamilNadu Housing Board.";

        sendEmailNotification(allottee.getEMAILID(), emailSubject, emailContent);
    }

    public void sendEmailNotification(String email, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);

        javaMailSender.send(message);
    }

    @Override
    public List<SchemeData> getAllSchemeData() {
        List<SchemeData> schemeDataList = schemeDataRepo.findAll();

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Iterate through the list and update ReservationStatus
        for (SchemeData scheme : schemeDataList) {
            // Parse the V_TO_DATE string as a LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate toDate = LocalDate.parse(scheme.getV_TO_DATE(), formatter);

            // Check if the current date is before toDate
            if (currentDate.isBefore(toDate)) {
                scheme.setV_RESERVATION_STATUS("Yes");
            } else {
                scheme.setV_RESERVATION_STATUS("No");
            }
            schemeDataRepo.save(scheme);
        }

        return schemeDataList;
    }


    @Override
    public SchemeData getSchemeData(Long id) {
        Optional<SchemeData> schemeDataOptional = schemeDataRepo.findById(id);

        if (schemeDataOptional.isPresent()) {
            SchemeData scheme = schemeDataOptional.get();

            // Parse the V_TO_DATE string as a LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate toDate = LocalDate.parse(scheme.getV_TO_DATE(), formatter);

            // Get the current date
            LocalDate currentDate = LocalDate.now();

            // Check if the current date is before toDate
            if (currentDate.isBefore(toDate)) {
                scheme.setV_RESERVATION_STATUS("Yes");
            } else {
                scheme.setV_RESERVATION_STATUS("No");
            }

            return schemeDataRepo.save(scheme);
        } else {
            // Handle the case where the SchemeData with the given ID is not found
            return null; // Or throw an exception, depending on your use case
        }
    }

    @Override
    public List<SchemeData> saveSchemeData(List<SchemeData> schemeDataList) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<SchemeData> schemeList = new ArrayList<>();
        for (SchemeData scheme : schemeDataList) {
            if (scheme.getV_TO_DATE() != null) {
                try {
                    LocalDate toDate = LocalDate.parse(scheme.getV_TO_DATE(), formatter);

                    if (toDate.isAfter(currentDate)) {
                        scheme.setV_RESERVATION_STATUS("Yes");
                    } else {
                        scheme.setV_RESERVATION_STATUS("No");
                    }
                } catch (DateTimeParseException e) {
                    // Handle invalid date format in V_TO_DATE
                    // You can log the error or handle it as needed
                }
            }

            Long totalAllotment = scheme.getN_TOTAL_UNITS();
            // Scheduled Caste
            Long artist = Math.round(0.18 * 0.05 * totalAllotment);
            scheme.setV_ARTISTS(artist);
            scheme.setV_ARTISTS_UNSOLD_UNITS(artist);
            Long politicalSufferers = Math.round(0.18 * 0.01 * totalAllotment);
            scheme.setV_POLITICAL_SUFFERERS(politicalSufferers);
            scheme.setV_POLITICAL_SUFFERERS_UNSOLD_UNITS(politicalSufferers);
            Long physicallyChallenged = Math.round(0.18 * 0.03 * totalAllotment) == 0 ? 1 : Math.round(0.18 * 0.03 * totalAllotment);
            scheme.setV_PHYSICALLY_CHALLENGED(physicallyChallenged);
            scheme.setV_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(physicallyChallenged);
            scheme.setV_SCHEDULED_CASTE(Math.round(0.18 * totalAllotment) - artist - politicalSufferers - physicallyChallenged);
            scheme.setV_SCHEDULED_CASTE_UNSOLD_UNITS(scheme.getV_SCHEDULED_CASTE());
            // Scheduled Tribes
            Long stPhysicallYChallenged =  Math.round(0.01 * 0.03 * totalAllotment) == 0 ? 1 : Math.round(0.01 * 0.03 * totalAllotment);
            scheme.setV_ST_PHYSICALLY_CHALLENGED(stPhysicallYChallenged);
            scheme.setV_ST_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(stPhysicallYChallenged);
            scheme.setV_SCHEDULED_TRIBES(Math.round(0.01 * totalAllotment) - stPhysicallYChallenged);
            scheme.setV_SCHEDULED_TRIBES_UNSOLD_UNITS(scheme.getV_SCHEDULED_TRIBES());
            // State Government
            Long stateJudicialOfficers = Math.round(0.18 * 0.02 * totalAllotment);
            scheme.setV_STATE_GOVT_JUDICIAL_OFFICERS(stateJudicialOfficers);
            scheme.setV_STATE_GOVT_JUDICIAL_OFFICERS_UNSOLD_UNITS(stateJudicialOfficers);
            Long statePhysicallYChallenged = Math.round(0.18 * 0.03 * totalAllotment) == 0 ? 1 : Math.round(0.18 * 0.03 * totalAllotment);
            scheme.setV_STATE_GOVT_PHYSICALLY_CHALLENGED(statePhysicallYChallenged);
            scheme.setV_STATE_GOVT_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(statePhysicallYChallenged);
            scheme.setV_STATE_GOVERNMENT(Math.round(0.18 * totalAllotment) - stateJudicialOfficers - statePhysicallYChallenged);
            scheme.setV_STATE_GOVERNMENT_UNSOLD_UNITS(scheme.getV_STATE_GOVERNMENT());
            // Central Government
            Long centralPhysicallyChallenged = Math.round(0.08 * 0.03 * totalAllotment) == 0 ? 1 : Math.round(0.08 * 0.03 * totalAllotment);
            scheme.setV_CENTRAL_GOVT_PHYSICALLY_CHALLENGED(centralPhysicallyChallenged);
            scheme.setV_CENTRAL_GOVT_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(centralPhysicallyChallenged);
            scheme.setV_CENTRAL_TNEB_LOCAL_BODIES(Math.round(0.08 * totalAllotment) - centralPhysicallyChallenged);
            scheme.setV_CENTRAL_TNEB_LOCAL_BODIES_UNSOLD_UNITS(scheme.getV_CENTRAL_TNEB_LOCAL_BODIES());
            // Defence
            Long awardees = Math.round(0.07 * 0.01 * totalAllotment);
            scheme.setV_AWARDEES(awardees);
            scheme.setV_AWARDEES_UNSOLD_UNITS(awardees);
            Long defencePhysicallyChallenged = Math.round(0.07 * 0.03 * totalAllotment) == 0 ? 1 : Math.round(0.07 * 0.03 * totalAllotment);
            scheme.setV_DEFENCE_PHYSICALLY_CHALLENGED(defencePhysicallyChallenged);
            scheme.setV_DEFENCE_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(defencePhysicallyChallenged);
            scheme.setV_DEFENCE(Math.round(0.07 * totalAllotment) - awardees - defencePhysicallyChallenged);
            scheme.setV_DEFENCE_UNSOLD_UNITS(scheme.getV_DEFENCE());
            // Dhobbies And Barbers
            Long artists = Math.round(0.04 * 0.05 * totalAllotment);
            scheme.setV_DHOBBIES_ARTISTS(artists);
            scheme.setV_DHOBBIES_ARTISTS_UNSOLD_UNITS(artists);
            Long dhobbiesPhysicallyChallenged = Math.round(0.04 * 0.03 * totalAllotment) == 0 ? 1 : Math.round(0.04 * 0.03 * totalAllotment);
            scheme.setV_DHOBBIES_PHYSICALLY_CHALLENGED(dhobbiesPhysicallyChallenged);
            scheme.setV_DHOBBIES_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(dhobbiesPhysicallyChallenged);
            scheme.setV_DHOBBIES_BARBERS(Math.round(0.04 * totalAllotment) - artists - dhobbiesPhysicallyChallenged);
            scheme.setV_DHOBBIES_BARBERS_UNSOLD_UNITS(scheme.getV_DHOBBIES_BARBERS());
            // Working Journalist
            Long journalistPhysicallyChallenged = Math.round(0.03 * 0.03 * totalAllotment) == 0 ? 1 : Math.round(0.03 * 0.03 * totalAllotment);
            scheme.setV_JOURNALIST_PHYSICALLY_CHALLENGED(journalistPhysicallyChallenged);
            scheme.setV_JOURNALIST_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(journalistPhysicallyChallenged);
            scheme.setV_WORKING_JOURNALIST(Math.round(0.03 * totalAllotment) - journalistPhysicallyChallenged);
            scheme.setV_WORKING_JOURNALIST_UNSOLD_UNITS(scheme.getV_WORKING_JOURNALIST());
            // Language Crusaders
            Long languagePhysicallyChallenged = Math.round(0.01 * 0.03 * totalAllotment) == 0 ? 1 : Math.round(0.18 * 0.03 * totalAllotment);
            scheme.setV_LANGUAGE_PHYSICALLY_CHALLENGED(languagePhysicallyChallenged);
            scheme.setV_LANGUAGE_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(languagePhysicallyChallenged);
            scheme.setV_LANGUAGE_CRUSADERS(Math.round(0.01 * totalAllotment) - languagePhysicallyChallenged);
            scheme.setV_LANGUAGE_CRUSADERS_UNSOLD_UNITS(scheme.getV_LANGUAGE_CRUSADERS());
            // TNHB Employees
            Long tnhbPhysicallyChallenged = Math.round(0.02 * 0.03 * totalAllotment) == 0 ? 1 : Math.round(0.02 * 0.03 * totalAllotment);
            scheme.setV_TNHB_PHYSICALLY_CHALLENGED(tnhbPhysicallyChallenged);
            scheme.setV_TNHB_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(tnhbPhysicallyChallenged);
            scheme.setV_TNHB_EMPLOYEES(Math.round(0.02 * totalAllotment) - tnhbPhysicallyChallenged);
            scheme.setV_TNHB_EMPLOYEES_UNSOLD_UNITS(scheme.getV_TNHB_EMPLOYEES());
            // General Public
            Long publicArtist = Math.round(0.38 * 0.05 * totalAllotment);
            scheme.setV_GENERAL_PUBLIC_ARTISTS(publicArtist);
            scheme.setV_GENERAL_PUBLIC_ARTISTS_UNSOLD_UNITS(publicArtist);
            Long publicPoliticalSufferers = Math.round(0.38 * 0.01 * totalAllotment);
            scheme.setV_GENERAL_PUBLIC_POLITICAL_SUFFERERS(publicPoliticalSufferers);
            scheme.setV_GENERAL_PUBLIC_POLITICAL_SUFFERERS_UNSOLD_UNITS(publicPoliticalSufferers);
            Long publicPhysicallyChallenged = Math.round(0.38 * 0.03 * totalAllotment) == 0 ? 1 : Math.round(0.38 * 0.03 * totalAllotment);
            scheme.setV_GENERAL_PUBLIC_PHYSICALLY_CHALLENGED(publicPhysicallyChallenged);
            scheme.setV_GENERAL_PUBLIC_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(publicPhysicallyChallenged);
            scheme.setV_GENERAL_PUBLIC(Math.round(0.38 * totalAllotment) - publicArtist - publicPoliticalSufferers - publicPhysicallyChallenged);
            scheme.setV_GENERAL_PUBLIC_UNSOLD_UNITS(scheme.getV_GENERAL_PUBLIC());

            scheme.setN_TOTAL_ALLOTTED_UNITS(0L);
            scheme.setN_TOTAL_UNSOLD_UNITS(scheme.getN_TOTAL_UNITS());
            schemeDataRepo.save(scheme);
            schemeList.add(scheme);
        }
        return schemeList;
    }
    @Override
    public String getDivisionCode(String divisionName) {
        return schemeDataRepo.getDivisionCode(divisionName);
    }

    @Override
    public int getDivSchemeCount(String divName) {
        return schemeDataRepo.getDivSchemeCount(divName);
    }

    @Override
    public List<String> getAllDivision() {
        return schemeDataRepo.getAllDivision();
    }

    @Override
    public List<UnitData> saveUnitData(List<UnitData> unitData) {
        unitDataRepo.saveAll(unitData);
        Long id = unitData.get(0).getN_SCHEME_ID();
        Optional<SchemeData> schemeDataOptional = schemeDataRepo.findById(id);
        SchemeData schemeData = schemeDataOptional.orElseThrow();
        schemeData.setN_TOTAL_ALLOTTED_UNITS(unitDataRepo.updateTotalAllottedUnits(id));
        schemeData.setN_TOTAL_ALLOTTED_UNITS_FOR_RESIDENTIAL(unitDataRepo.allottedResidential(id));
        schemeData.setN_TOTAL_ALLOTTED_UNITS_FOR_COMMERCIAL(unitDataRepo.allottedCommercial(id));
        schemeData.setN_TOTAL_ALLOTTED_UNITS_FOR_HIRE_PURCHASE(unitDataRepo.allotedHirePurchase(id));
        schemeData.setN_TOTAL_ALLOTTED_UNITS_FOR_SFS(unitDataRepo.allottedSelfFinance(id));
        schemeData.setN_TOTAL_ALLOTTED_UNITS_FOR_OUTRIGHT(unitDataRepo.allottedOutright(id));
        schemeData.setN_TOTAL_ALLOTTED_UNITS_FOR_HIG(unitDataRepo.allottedHIG(id));
        schemeData.setN_TOTAL_ALLOTTED_UNITS_FOR_MIG(unitDataRepo.allottedMIG(id));
        schemeData.setN_TOTAL_ALLOTTED_UNITS_FOR_LIG(unitDataRepo.allottedLIG(id));
        schemeData.setN_TOTAL_ALLOTTED_UNITS_FOR_EWS(unitDataRepo.allottedEWS(id));
        schemeDataRepo.save(schemeData);

        return unitData;
    }

    @Override
    public SchemeData editSchemeData( SchemeData updatedSchemeData) {
        Long id= updatedSchemeData.getN_ID();
        Optional<SchemeData> existingSchemeDataOptional = schemeDataRepo.findById(id);

        if (existingSchemeDataOptional.isPresent()) {
            SchemeData existingSchemeData = existingSchemeDataOptional.get();
            Field[] fields = SchemeData.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object updatedValue = field.get(updatedSchemeData);
                    if (updatedValue != null) {
                        field.set(existingSchemeData, updatedValue);
                    }
                } catch (IllegalAccessException e) {

                    e.printStackTrace();
                }
            }

            schemeDataRepo.save(existingSchemeData);

            return existingSchemeData;
        } else {

            return null;
        }
    }

    public List<UnitData> getUnits(Long nSchemeId) {
        return unitDataRepo.findByNSchemeId(nSchemeId);
    }

    // View One Unit Data
    @Override
    public UnitData getOneUnit(Long nId) {
        Optional<UnitData> optionalSavedUnit = unitDataRepo.findById(nId);
        UnitData savedUnit = optionalSavedUnit.get();
        return savedUnit;
    }

    //Save Single Unit
    @Override
    @Transactional
    public UnitData saveOneUnitData(UnitData unitData) {

        UnitData savedunitData = unitDataRepo.save(unitData);
        unitDataRepo.updateSchemeData(unitData.getN_SCHEME_ID());
//        unitDataRepo.schemeDataUpdated(unitData.getN_SCHEME_ID());
        return savedunitData;

    }

    @Override
    public void deleteSchemeData(Long id) {
        schemeDataRepo.deleteById(id);
    }

    @Override
    public void deleteAllotteeData(Long id) {
        allotteeRepo.deleteById(id);
    }

    @Override
    public void deleteUnitData(Long id) {
        unitDataRepo.deleteById(id);
    }

    //Sales Deed Document Upload
    @Transactional
    @Override
    public List<SalesDeedData> saveSalesDeed(List<SalesDeedData> salesdeed) {
        List<SalesDeedData> savedSalesDeeds = new ArrayList<>();

        for (SalesDeedData salesdeedData : salesdeed) {
            if (salesdeedData.getAllotmentOrderFile() != null) {
                String base64FileData = salesdeedData.getAllotmentOrderFile();
                byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
                String generatedFileName = salesdeedData.getAllotmentOrderFileName();
                String uniqueFileName = UUID.randomUUID().toString() + "_" + generatedFileName;
                File dest = new File(uploadDir, uniqueFileName);

                try {
                    Files.write(dest.toPath(), decodedFileData);
                    salesdeedData.setAllotmentOrderFileName(generatedFileName);
                    salesdeedData.setAllotmentOrderFilePath(dest.getAbsolutePath());
                } catch (IOException e) {

                }
            }
            if (salesdeedData.getLCSFile() != null) {
                String base64FileData = salesdeedData.getLCSFile();
                byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
                String generatedFileName = salesdeedData.getLCSFileName();
                String uniqueFileName = UUID.randomUUID().toString() + "_" + generatedFileName;
                File dest = new File(uploadDir, uniqueFileName);

                try {
                    Files.write(dest.toPath(), decodedFileData);
                    salesdeedData.setLCSFileName(generatedFileName);
                    salesdeedData.setLCSFilePath(dest.getAbsolutePath());
                } catch (IOException e) {

                }
            }
            if (salesdeedData.getHandingReportFile() != null) {
                String base64FileData = salesdeedData.getHandingReportFile();
                byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
                String generatedFileName = salesdeedData.getHandingReportFileName();
                String uniqueFileName = UUID.randomUUID().toString() + "_" + generatedFileName;
                File dest = new File(uploadDir, uniqueFileName);

                try {
                    Files.write(dest.toPath(), decodedFileData);
                    salesdeedData.setHandingReportFileName(generatedFileName);
                    salesdeedData.setHandingReportFilePath(dest.getAbsolutePath());
                } catch (IOException e) {

                }
            }
            if (salesdeedData.getFieldBookFile() != null) {
                String base64FileData = salesdeedData.getFieldBookFile();
                byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
                String generatedFileName = salesdeedData.getFieldBookFileName();
                String uniqueFileName = UUID.randomUUID().toString() + "_" + generatedFileName;
                File dest = new File(uploadDir, uniqueFileName);

                try {
                    Files.write(dest.toPath(), decodedFileData);
                    salesdeedData.setFieldBookFileName(generatedFileName);
                    salesdeedData.setFieldBookFilePath(dest.getAbsolutePath());
                } catch (IOException e) {

                }
            }
            if (salesdeedData.getLoanFile() != null) {
                String base64FileData = salesdeedData.getLoanFile();
                byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
                String generatedFileName = salesdeedData.getLoanFileName();
                String uniqueFileName = UUID.randomUUID().toString() + "_" + generatedFileName;
                File dest = new File(uploadDir, uniqueFileName);

                try {
                    Files.write(dest.toPath(), decodedFileData);
                    salesdeedData.setLoanFileName(generatedFileName);
                    salesdeedData.setLoanFilePath(dest.getAbsolutePath());
                } catch (IOException e) {

                }
            }
            if (salesdeedData.getSaleDeedFile() != null) {
                String base64FileData = salesdeedData.getSaleDeedFile();
                byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
                String generatedFileName = salesdeedData.getSaleDeedFileName();
                String uniqueFileName = UUID.randomUUID().toString() + "_" + generatedFileName;
                File dest = new File(uploadDir, uniqueFileName);

                try {
                    Files.write(dest.toPath(), decodedFileData);
                    salesdeedData.setSaleDeedFileName(generatedFileName);
                    salesdeedData.setSaleDeedFilePath(dest.getAbsolutePath());
                } catch (IOException e) {

                }
            }

            SalesDeedData savedsalesdeed = saleddeeddataRepo.save(salesdeedData);
            savedSalesDeeds.add(savedsalesdeed);
        }

        return savedSalesDeeds;
    }

    //Sales Deed get by Scheme ID

    public List<SalesDeedData> getAllSalesDeeds(Long nSchemeId) {
        return saleddeeddataRepo.findByNSchemeId(nSchemeId);
    }


    //ALLOTEE DASHBOARD

    //Allotte OTP for Login

    public boolean emailExists(String email) {
        List<Allottee> allottees = allotteeRepo.findAllByEMAILID(email);
        return !allottees.isEmpty();
    }

    public class OtpGenerator {
        public static String generateOtp(int length) {
            return RandomStringUtils.randomNumeric(length);
        }
    }

    public void sendOtpToAllottee(String email) {
        List<Allottee> allottees = allotteeRepo.findAllByEMAILID(email);

        String otp = OtpGenerator.generateOtp(6);

        for (Allottee allottee : allottees) {
            allottee.setV_OTP(otp);
            allotteeRepo.save(allottee);

            sendOtpEmail(allottee.getEMAILID(), otp);
        }
    }

    public void sendOtpEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code for TNHB-Property Dashboard");
        message.setText("Hi! Your OTP code is: " + otp);

        javaMailSender.send(message);
    }

    //Verify OTP
    @Override
    public List<Allottee> verifyOtpAndGetAllottee(String email, String otp) {
        List<Allottee> allottees = allotteeRepo.findAllByEMAILID(email);
        List<Allottee> responseList = new ArrayList<>();
        for (Allottee allottee : allottees) {
            if (allottee.getV_OTP().equals(otp)) {
                responseList.add(allottee);
            }
        }
        return responseList;
    }


    // Allotte by Id
    public List<Allottee> getAllotteebyId(Long id) {
        return allotteeRepo.findByNID(id);
    }

    //Combined Allotte Details
    @Transactional
    @Override
    public CombinedDataModel getDetailsById(Long id) {
        List<Object[]> queryResult = allotteeRepo.findDetailsById(id);

        if (!queryResult.isEmpty()) {
            Object[] resultRow = queryResult.get(0);

            CombinedDataModel combinedDataModel = new CombinedDataModel();
            combinedDataModel.setAllotteeDate((String) resultRow[0]);
            combinedDataModel.setAllotteeNo((String) resultRow[1]);
            combinedDataModel.setBlockNo((String) resultRow[2]);
            combinedDataModel.setCarpetArea((String) resultRow[3]);
            combinedDataModel.setFloorNo((String) resultRow[4]);
            combinedDataModel.setPlotArea((String) resultRow[5]);
            combinedDataModel.setTypeName((String) resultRow[6]);
            combinedDataModel.setUdsArea((String) resultRow[7]);
            combinedDataModel.setUnitNo((String) resultRow[8]);
            combinedDataModel.setAssetCategory((String) resultRow[9]);
            combinedDataModel.setAssetSubCategory((String) resultRow[10]);
            combinedDataModel.setAssetType((String) resultRow[11]);
            combinedDataModel.setPlinthArea((String) resultRow[12]);
            combinedDataModel.setFinalLandCost(resultRow[13] != null ? resultRow[13].toString() : null);
            combinedDataModel.setSchemeName((String) resultRow[14]);

            return combinedDataModel;
        } else {
            return null;
        }
    }

    //Get Allottee Documents
    public List<SalesDeedData> getSalesDeedDatabyId(Long id) {
        return saleddeeddataRepo.findByNAllotteeId(id);
    }



    //WEBSITE MODULE - AWS
    @Transactional
    @Override
    public List<WebsiteData> saveWebsiteData(List<WebsiteData> websiteDataList) {
        List<WebsiteData> savedWebsiteDataList = new ArrayList<>();
        List<String> savedPhotos = new ArrayList<>();
        for (WebsiteData websiteData : websiteDataList) {
            List<String> photoList = websiteData.getFPhoto();
            if (photoList != null && !photoList.isEmpty()) {

                for (String base64FileData : photoList) {
                    byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
                    String generatedFileName = UUID.randomUUID().toString() + ".jpg";
                    String bucketUrl = "http://tnhb-property-docs.s3-website.ap-south-1.amazonaws.com/";
                    String filepath = bucketUrl + generatedFileName;
                    byte[] fileBytes = decodedFileData;
                    AwsBasicCredentials credentials = AwsBasicCredentials.create("AKIAR4WRUXSBYP5V7J42", "b8YXyxvNaQpNVr63UxCm51KckgMV8ZZ1WRLFwYl/");

                    S3Client s3Client = S3Client.builder()
                            .region(Region.AP_SOUTH_1)
                            .credentialsProvider(() -> credentials)
                            .build();

                    PutObjectRequest request = PutObjectRequest.builder()
                            .bucket("tnhb-property-docs")
                            .key(generatedFileName)
                            .build();

                    PutObjectResponse response = s3Client.putObject(request, RequestBody.fromBytes(fileBytes));

                    savedPhotos.add(filepath);
                }
                websiteData.setFPhoto(savedPhotos);

            }

            if (websiteData.getFFloorPlanPicture() != null) {
                String base64FileData = websiteData.getFFloorPlanPicture();
                byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
                String generatedFileName = UUID.randomUUID().toString() + ".jpg";
                String bucketUrl = "http://tnhb-property-docs.s3-website.ap-south-1.amazonaws.com/";
                String filepath = bucketUrl + generatedFileName;
                byte[] fileBytes = decodedFileData;
                AwsBasicCredentials credentials = AwsBasicCredentials.create("AKIAR4WRUXSBYP5V7J42", "b8YXyxvNaQpNVr63UxCm51KckgMV8ZZ1WRLFwYl/");

                S3Client s3Client = S3Client.builder()
                        .region(Region.AP_SOUTH_1)
                        .credentialsProvider(() -> credentials)
                        .build();

                PutObjectRequest request = PutObjectRequest.builder()
                        .bucket("tnhb-property-docs")
                        .key(generatedFileName)
                        .build();

                PutObjectResponse response = s3Client.putObject(request, RequestBody.fromBytes(fileBytes));

                websiteData.setFFloorPlanPicture(filepath);

            }

            if (websiteData.getFFloorPlanPdf() != null) {
                String base64PdfData = websiteData.getFFloorPlanPdf();
                byte[] decodedPdfData = Base64.getDecoder().decode(base64PdfData);
                String generatedPdfFileName = UUID.randomUUID().toString() + ".pdf";
                String bucketUrl = "http://tnhb-property-docs.s3-website.ap-south-1.amazonaws.com/";
                String filepath = bucketUrl + generatedPdfFileName;
                byte[] fileBytes = decodedPdfData;
                AwsBasicCredentials credentials = AwsBasicCredentials.create("AKIAR4WRUXSBYP5V7J42", "b8YXyxvNaQpNVr63UxCm51KckgMV8ZZ1WRLFwYl/");

                S3Client s3Client = S3Client.builder()
                        .region(Region.AP_SOUTH_1)
                        .credentialsProvider(() -> credentials)
                        .build();

                PutObjectRequest request = PutObjectRequest.builder()
                        .bucket("tnhb-property-docs")
                        .key(generatedPdfFileName)
                        .contentType("application/pdf")
                        .build();

                PutObjectResponse response = s3Client.putObject(request, RequestBody.fromBytes(fileBytes));

                websiteData.setFFloorPlanPdf(filepath);

            }

            if (websiteData.getFPocPicture() != null) {
                String base64FileData = websiteData.getFPocPicture();
                byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
                String generatedFileName = UUID.randomUUID().toString() + ".jpg";
                String bucketUrl = "http://tnhb-property-docs.s3-website.ap-south-1.amazonaws.com/";
                String filepath = bucketUrl + generatedFileName;
                byte[] fileBytes = decodedFileData;
                AwsBasicCredentials credentials = AwsBasicCredentials.create("AKIAR4WRUXSBYP5V7J42", "b8YXyxvNaQpNVr63UxCm51KckgMV8ZZ1WRLFwYl/");

                S3Client s3Client = S3Client.builder()
                        .region(Region.AP_SOUTH_1)
                        .credentialsProvider(() -> credentials)
                        .build();

                PutObjectRequest request = PutObjectRequest.builder()
                        .bucket("tnhb-property-docs")
                        .key(generatedFileName)
                        .build();

                PutObjectResponse response = s3Client.putObject(request, RequestBody.fromBytes(fileBytes));

                websiteData.setFPocPicture(filepath);

            }

            if (websiteData.getFVideo() != null) {
                String base64VideoData = websiteData.getFVideo();
                byte[] decodedVideoData = Base64.getDecoder().decode(base64VideoData);
                String generatedVideoFileName = UUID.randomUUID().toString() + ".mp4";
                String bucketUrl = "http://tnhb-property-docs.s3-website.ap-south-1.amazonaws.com/";
                String filepath = bucketUrl + generatedVideoFileName;
                byte[] fileBytes = decodedVideoData;
                AwsBasicCredentials credentials = AwsBasicCredentials.create("AKIAR4WRUXSBYP5V7J42", "b8YXyxvNaQpNVr63UxCm51KckgMV8ZZ1WRLFwYl/");

                S3Client s3Client = S3Client.builder()
                        .region(Region.AP_SOUTH_1)
                        .credentialsProvider(() -> credentials)
                        .build();

                PutObjectRequest request = PutObjectRequest.builder()
                        .bucket("tnhb-property-docs")
                        .key(generatedVideoFileName)
                        .build();

                PutObjectResponse response = s3Client.putObject(request, RequestBody.fromBytes(fileBytes));

                websiteData.setFVideo(filepath);

            }

            savedWebsiteDataList.add(websiteData);
            savedWebsiteDataList = websiteDataRepo.saveAll(savedWebsiteDataList);
        }

        return savedWebsiteDataList;
    }



    //Website MODULE - LOCAL FILE PATH
//    @Transactional
//    @Override
//    public List<WebsiteData> saveWebsiteData(List<WebsiteData> websiteDataList) {
//        List<WebsiteData> savedWebsiteDataList = new ArrayList<>();
//        List<String> savedPhotos = new ArrayList<>();
//        for (WebsiteData websiteData : websiteDataList) {
//            List<String> photoList = websiteData.getFPhoto();
//            if (photoList != null && !photoList.isEmpty()) {
//
//                for (String base64FileData : photoList) {
//                    byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
//                    String generatedFileName = UUID.randomUUID().toString() + ".jpg";
//
//                    File destFolder = new File(uploadDir);
//                    if (!destFolder.exists()) {
//                        destFolder.mkdirs();
//                    }
//
//                    File dest = new File(destFolder, generatedFileName);
//
//                    try {
//                        Files.write(dest.toPath(), decodedFileData);
//                        savedPhotos.add(dest.getAbsolutePath());
//                    } catch (IOException e) {
//                        // Handle the exception
//                    }
//
//
//                }
//                websiteData.setFPhoto(savedPhotos);
//
//            }
//
//            if (websiteData.getFFloorPlanPicture() != null) {
//                String base64FileData = websiteData.getFFloorPlanPicture();
//                byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
//                String generatedFileName = UUID.randomUUID().toString() + ".jpg";
//
//                File destFolder = new File(uploadDir);
//                if (!destFolder.exists()) {
//                    destFolder.mkdirs();
//                }
//
//                File dest = new File(destFolder, generatedFileName);
//
//                try {
//                    Files.write(dest.toPath(), decodedFileData);
//                    websiteData.setFFloorPlanPicture(dest.getAbsolutePath());
//                } catch (IOException e) {
//
//                }
//            }
//
//            if (websiteData.getFFloorPlanPdf() != null) {
//                String base64PdfData = websiteData.getFFloorPlanPdf();
//                byte[] decodedPdfData = Base64.getDecoder().decode(base64PdfData);
//                String generatedPdfFileName = UUID.randomUUID().toString() + ".pdf";
//
//                File destFolder = new File(uploadDir);
//                if (!destFolder.exists()) {
//                    destFolder.mkdirs();
//                }
//
//                File dest = new File(destFolder, generatedPdfFileName);
//
//                try {
//                    Files.write(dest.toPath(), decodedPdfData);
//                    websiteData.setFFloorPlanPdf(dest.getAbsolutePath());
//                } catch (IOException e) {
//
//                }
//            }
//
//            if (websiteData.getFPocPicture() != null) {
//                String base64FileData = websiteData.getFPocPicture();
//                byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
//                String generatedFileName = UUID.randomUUID().toString() + ".jpg";
//
//                File destFolder = new File(uploadDir);
//                if (!destFolder.exists()) {
//                    destFolder.mkdirs();
//                }
//
//                File dest = new File(destFolder, generatedFileName);
//
//                try {
//                    Files.write(dest.toPath(), decodedFileData);
//                    websiteData.setFPocPicture(dest.getAbsolutePath());
//                } catch (IOException e) {
//
//                }
//            }
//
//            if (websiteData.getFVideo() != null) {
//                String base64VideoData = websiteData.getFVideo();
//                byte[] decodedVideoData = Base64.getDecoder().decode(base64VideoData);
//                String generatedVideoFileName = UUID.randomUUID().toString() + ".mp4";
//
//                File destFolder = new File(uploadDir);
//                if (!destFolder.exists()) {
//                    destFolder.mkdirs();
//                }
//
//                File dest = new File(destFolder, generatedVideoFileName);
//
//                try {
//                    Files.write(dest.toPath(), decodedVideoData);
//                    websiteData.setFVideo(dest.getAbsolutePath());
//                } catch (IOException e) {
//
//                }
//            }
//
//
//            savedWebsiteDataList.add(websiteData);
//            savedWebsiteDataList = websiteDataRepo.saveAll(savedWebsiteDataList);
//        }
//
//        return savedWebsiteDataList;
//    }


    public List<WebsiteModel> getAllWebsiteData() {
        List<Map<String, Object>> queryResults = websiteDataRepo.findAllUnitData();

        List<WebsiteModel> websiteModels = new ArrayList<>();
        for (Map<String, Object> result : queryResults) {
            WebsiteModel websiteModel = new WebsiteModel();
            websiteModel.setV_SCHEME_NAME((String) result.get("v_scheme_name"));
            websiteModel.setV_DIVISION((String) result.get("v_division"));
            websiteModel.setN_TOTAL_UNSOLD_UNITS((Long) result.get("n_total_unsold_units"));
            websiteModel.setN_NO_OF_MIG_UNITS((Long) result.get("n_no_of_mig_units"));
            websiteModel.setN_NO_OF_LIG_UNITS((Long) result.get("n_no_of_lig_units"));
            websiteModel.setN_NO_OF_EWS_UNITS((Long) result.get("n_no_of_ews_units"));
            websiteModel.setN_NO_OF_HIG_UNITS((Long) result.get("n_no_of_hig_units"));
            websiteModel.setN_SCHEME_ID((Long) result.get("n_scheme_id"));
            websiteModel.setN_ID((Long) result.get("n_id"));
            websiteModel.setV_UNIT_ALLOTTED_STATUS((String) result.get("v_unit_allotted_status"));
            websiteModel.setV_UNIT_TYPE((String) result.get("v_unit_type"));
            websiteModel.setF_PHOTO((String) result.get("f_photo"));
            websiteModels.add(websiteModel);
        }

        return websiteModels;
    }

    @Override
    public Map<String, Object> getSchemeDataBySchemeId(Long schemeId) {
        List<Map<String, Object>> originalData = websiteDataRepo.findSchemeDataBySchemeId(schemeId);

        // Create a new map to store the transformed data
        Map<String, Object> transformedData = new HashMap<>();

        if (!originalData.isEmpty()) {
            Map<String, Object> firstData = originalData.get(0);

            transformedData.put("v_division", firstData.get("v_division"));
            transformedData.put("v_poc_mobile", firstData.get("v_poc_mobile"));
            transformedData.put("v_poc_name", firstData.get("v_poc_name"));
            transformedData.put("n_no_of_ews_units", firstData.get("n_no_of_ews_units"));
            transformedData.put("n_total_unsold_units", firstData.get("n_total_unsold_units"));
            transformedData.put("v_project_description", firstData.get("v_project_description"));
            transformedData.put("n_no_of_lig_units", firstData.get("n_no_of_lig_units"));
            transformedData.put("n_scheme_id", firstData.get("n_scheme_id"));
            transformedData.put("f_floor_plan_picture", firstData.get("f_floor_plan_picture"));
            transformedData.put("v_geo_tag_link", firstData.get("v_geo_tag_link"));
            transformedData.put("v_scheme_name", firstData.get("v_scheme_name"));
            transformedData.put("f_video", firstData.get("f_video"));
            transformedData.put("v_unit_type", firstData.get("v_unit_type"));
            transformedData.put("v_unit_allotted_status", firstData.get("v_unit_allotted_status"));

            List<String> photoList = new ArrayList<>();

            for (Map<String, Object> data : originalData) {
                photoList.add((String) data.get("f_photo"));
            }

            transformedData.put("f_photo", photoList);
            transformedData.put("n_no_of_hig_units", firstData.get("n_no_of_hig_units"));
            transformedData.put("v_amenities", firstData.get("v_amenities"));
            transformedData.put("n_no_of_mig_units", firstData.get("n_no_of_mig_units"));
            transformedData.put("f_poc_picture", firstData.get("f_poc_picture"));
            transformedData.put("v_poc_email", firstData.get("v_poc_email"));
        }

        return transformedData;
    }


    @Override
    public void deleteWebsiteData(Long id) {
        websiteDataRepo.deleteById(id);
    }

    @Override
    public List<WebsiteData> getWebsiteData(Long nschemeId) {
        return websiteDataRepo.findBynSchemeId(nschemeId);
    }




    @Override
    public List<Map<String, Object>> findUnitDetailsBySchemeId(Long schemeId) {
        return (List<Map<String, Object>>) websiteDataRepo.findUnitDetailsBySchemeId(schemeId);
    }


    public List<Map<String, Object>> getBlocksForWebsite(Long schemeId) {
        return websiteDataRepo.findBlocksBySchemeId(schemeId);
    }

    @Override
    public List<Map<String, Object>> getFloorForWebsite(Long schemeId) {
        return websiteDataRepo.findFloorDetailsBySchemeId(schemeId);
    }

    @Override
    public String findById(Long unitId) {
        Map<String, Object> unitDataWithStatus = websiteDataRepo.findUnitDataWithStatus(unitId);
        if (unitDataWithStatus.isEmpty()) {
            return "Unit not found";
        }

        String unitStatus = (String) unitDataWithStatus.get("V_UNIT_ALLOTTED_STATUS");

        if ("No".equalsIgnoreCase(unitStatus)) {
            return "Your Apartment has been booked";
        } else if ("Yes".equalsIgnoreCase(unitStatus)) {
            return "You can't book this Apartment";
        } else {
            return "Invalid status";
        }
    }

    @Override
    public ResponseEntity<Object> bookNow(Long unitId) {
        Optional<WebsiteData> unitDataWithStatus = websiteDataRepo.findById(unitId);
        if (unitDataWithStatus.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        String unitStatus = (String) unitDataWithStatus.get().get("V_UNIT_ALLOTTED_STATUS");

        if ("No".equalsIgnoreCase(unitStatus)) {
            return ResponseEntity.ok("Your Apartment has been booked");
        } else if ("Yes".equalsIgnoreCase(unitStatus)) {
            return ResponseEntity.ok("You can't book this Apartment");
        } else {
            return ResponseEntity.ok("Invalid status"); // Handle other cases as needed
        }
    }



    //Enquiry
    public String sendMailToCustomer(Enquiry enquiry, String vPocEmail, String vEmail) {
        String infoMsg = "Success";

        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            helper.setFrom("yazhinikuzhali98@gmail.com");
            helper.addTo(vPocEmail);
            helper.addTo(vEmail);
            helper.setSubject("TNHB- Property Request");
            String emailContent =
                    "Hello " + enquiry.getVName() + ",<br>" +
                    "Your below enquiry has been successfully submitted.<br>" +
                    "Name: " + enquiry.getVName() + "<br>" +
                    "Phone No: " + enquiry.getNContactNo() + "<br>" +
                    "Email: " + enquiry.getVEmail() + "<br>" +
                    "Query: " + enquiry.getVMessage();

            helper.setText(emailContent, true);


            javaMailSender.send(msg);
        } catch (MessagingException e) {
            infoMsg = "Error sending email: " + e.getMessage();
            e.printStackTrace();
        }

        return infoMsg;
    }




    //FINANCIAL

    // Create EMI Calculation

    public void calculateEmiRows(CalculationRequestModel request) {
        int P = request.getV_PRINCIPAL_AMOUNT();
        int r = request.getV_RATE_OF_INTEREST();
        int n = request.getV_PAYMENT_PERIOD_IN_YEARS();

        double p = (double) P / 3;
        double R = (double) r / 100;
        double div1 = R;
        double S = 3.0 / r;
        double div2 = S;

        double rr = div1 * div2;

        double c = n * r;
        double d = c / 3;

        double t, u, v;

        t = rr + 1;
        u = Math.pow(t, d);
        v = u - 1;

        double e = p * rr * u;
        double f = v;

        double ans = e / f;

        int years = n;
        double totalBalance = P;
        double emi = ans;

        double balance = totalBalance;
        double interest = 0, principal = 0;
        LocalDate currentDate = request.getEMI_START_DATE();

        DecimalFormat df = new DecimalFormat("#.##");

        for (int month = 1; month <= years * 12; month++) {
            double previousInterest;
            double previousPrincipal;

            if ((month - 1) % 3 == 0) {
                interest = balance * (r / 1200.0);
                principal = emi - interest;
                previousInterest = interest;
                previousPrincipal = principal;
            } else {
                previousInterest = interest;
                previousPrincipal = principal;
            }

            balance = balance - principal;

            Financial_Calc financialCalc = new Financial_Calc();
            Financial_Calc.setFinancialCalc(financialCalc);
            financialCalc.setNUNITID(request.getN_UNIT_ID());
            financialCalc.setV_EMI_OPENING(emi);
            financialCalc.setN_INTEREST_FOR_EMI(interest);
            financialCalc.setV_PRINCIPAL_FOR_EMI(principal);
            financialCalc.setN_BALANCE_FOR_EMI(balance);
            financialCalc.setDATE(currentDate);
            this.financialCalc = financialCalc;

            int year = currentDate.getYear();
            int months = currentDate.getMonthValue();
            String monthYearStr = String.format("%04d-%02d", year, months);
            financialCalc.setMONTHNYEAR(monthYearStr);

            financial_calc_repo.save(financialCalc);

            if (currentDate.getMonthValue() == 12) {
                currentDate = currentDate.plusYears(1).withMonth(1);
            } else {
                currentDate = currentDate.plusMonths(1);
            }
        }

        Financial financial = new Financial();
        this.financialDetails = financial;
        financial.setNUNITID(request.getN_UNIT_ID());
        financial.setV_RATE_OF_INTEREST(request.getV_RATE_OF_INTEREST());
        financial.setV_PAYMENT_PERIOD_IN_YEARS(request.getV_PAYMENT_PERIOD_IN_YEARS());
        financial.setV_PRINCIPAL_AMOUNT(request.getV_PRINCIPAL_AMOUNT());
        financial.setEMI_START_DATE(request.getEMI_START_DATE());
        financial.setN_INITIAL_DEPOSIT(request.getN_INITIAL_DEPOSIT());
        financial.setN_INITIAL_DEPOSIT_PAID(request.getN_INITIAL_DEPOSIT_PAID());
        double tobePaid = (request.getN_INITIAL_DEPOSIT() - request.getN_INITIAL_DEPOSIT_PAID());
        financial.setN_INITIAL_DEPOSIT_TO_BE_PAID(tobePaid);

        financialRepo.save(financial);

    }

    @Scheduled(cron = "0 29 17 21 9 ?")
    public void calculateDemandRows() {

        try {
//        Calendar nextExecutionTime;
            String cronExpression = ("0 29 17 21 9 ?");

            CronTrigger cronTrigger = new CronTrigger(cronExpression);
            Date nextExecutionTime = cronTrigger.nextExecutionTime(new SimpleTriggerContext());

            LocalDate fixedMonth = nextExecutionTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate currentDate = LocalDate.now();
            System.out.println(currentDate);
            int currentMonth = currentDate.getMonthValue();
            System.out.println(currentMonth);
//            LocalDate currentDay = LocalDate.ofEpochDay(currentDate.getDayOfMonth());
//            System.out.println(currentDay);
            fixedMonth = fixedMonth.withYear(currentDate.getYear());

                Financial_Calc financialCalc1 = this.financialCalc;
                financialCalc1.setDATE(fixedMonth);

            String monthyear = financialDetails.getV_EMI_DUE_DATE();
        financialCalc1 = financial_calc_repo.findByMONTHNYEAR(monthyear);


//            financialCalc1  = fixedMonth.getMonthValue() == currentMonth) ;
//                System.out.println(fixedMonth);

//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
//            LocalDate monthyear = LocalDate.parse(financialCalc.getMONTHNYEAR());
//            String nextMonthFormatted = monthyear.format(formatter);
//            System.out.println(currentMonth);
//            Financial_Calc nextMonthData = financial_calc_repo.findByMONTHNYEAR(nextMonthFormatted);
//            System.out.println(nextMonthData);

//            if(nextMonthData != null){

            System.out.println("After If condition");

            if (financialCalc1 != null) {
                System.out.println("After If condition");


                financialCalc1.setN_EMI_CD(financialDetails.getV_EMI_OPENING());

                //SET INITIAL DEPOSIT
                double initialDepo = financialCalc1.getN_INITIAL_DEPOSIT_TO_BE_PAID();
                financialCalc1.setN_INITIAL_DEPOSIT_CD(initialDepo);

                //SET IDPI
                double idpi = ((financialCalc1.getN_INITIAL_DEPOSIT_CD() * (financialDetails.getV_RATE_OF_INTEREST() + 3)) / 1200);
                financialCalc1.setN_INITIAL_DEPOSIT_PI_CD(idpi);

                //SET ARREAR_CD
                financialCalc1.setN_ARREAR_CD(financialCalc1.getN_ARREAR_BALANCE() != null ? financialCalc1.getN_ARREAR_CD() : 0);

                //SET TOTAL DEMAND

                double total = financialCalc1.getN_ARREAR_CD() + financialCalc1.getN_EMI_CD() + financialCalc1.getN_INITIAL_DEPOSIT_CD() + financialCalc1.getN_INITIAL_DEPOSIT_PI_CD();
                financialCalc1.setTOTAL_CD(total);

                financialCalc1.setN_EMI_BALANCE(financialCalc1.getN_EMI_CD() == null ? financialCalc1.getN_EMI_CD() : 0);
                financialCalc1.setN_INITIAL_DEPOSIT_BALANCE(financialCalc1.getN_INITIAL_DEPOSIT_CD() == null ? financialCalc1.getN_INITIAL_DEPOSIT_CD() : 0);
                financialCalc1.setN_INITIAL_DEPOSIT_PI_BALANCE(financialCalc1.getN_INITIAL_DEPOSIT_CD() == null ? financialCalc1.getN_INITIAL_DEPOSIT_CD() : 0);
                financialCalc1.setTOTAL_BALANCE(financialCalc1.getTOTAL_CD() == null ? financialCalc1.getTOTAL_CD() : 0);
                financialCalc1.setN_PENAL_INTEREST_BALANCE(financialCalc1.getN_PENAL_INTEREST_CD() != null ? financialCalc1.getN_PENAL_INTEREST_CD() : 0);
                financialCalc1.setN_ARREAR_BALANCE(financialCalc1.getN_ARREAR_CD() == null ? financialCalc1.getN_ARREAR_CD() : 0);

                //SET PI
                String cronExpression1 = ("0 4 13 21 9 ?");
                double penalInterest = ((financialCalc1.getN_EMI_CD() * (financialDetails.getV_RATE_OF_INTEREST() + 3)) / 1200);
                financialCalc1.setN_PENAL_INTEREST_CD(penalInterest);


//            financialCalc1.setN_PENAL_INTEREST_BALANCE(financialCalc1.getN_PENAL_INTEREST_CD() == null ? financialCalc1.getN_PENAL_INTEREST_CD() : 0);

                financial_calc_repo.save(financialCalc1);
                System.out.println("Saved data for the fixed MONTH: " + financialCalc1.getDATE());
//                } else {
//                    System.out.println("financial1 is null. Data not saved.");
//                }

                if (financialCalc1 != null) {
                    financial_calc_repo.save(financialCalc1);
                    System.out.println("Saved data for the fixed MONTH: " + financialCalc1.getDATE());
                } else {
                    System.out.println("Data not saved as the fixed MONTH and DATE do not match the current month and date.");
                }

            }
//            } else {
//                System.out.println("Fixed month is not in the current year.");
//            }
//            double initialDepo = financialCalc.getN_INITIAL_DEPOSIT_TO_BE_PAID();
//            financialCalc.setN_INITIAL_DEPOSIT_CD(initialDepo);
//
//            financialCalc.setN_INITIAL_DEPOSIT_CD(financial1.getN_INITIAL_DEPOSIT_TO_BE_PAID());
////            } else {
////                data.setN_INITIAL_DEPOSIT_CD(financialData.getN_INITIAL_DEPOSIT_TO_BE_PAID());
////            }
//
//            // Example of handling null when parsing LocalDate
//            LocalDate currentDate = LocalDate.now();
//            LocalDate month = LocalDate.ofEpochDay(currentDate.getMonthValue());
//
//// Example of handling null when parsing decimal values

//            boolean isNEMICol = financialCalc.isN_EMI_COL();
//
//            // Check the year condition (1993 or before)
//            LocalDate year = LocalDate.ofEpochDay(Year.now().getValue());
//
////                if (year >= 1993) {
//            // Calculate N_PENAL_INTEREST_CD for years 1993 or before
//            double penalInterestCd = (financialCalc.getV_PRINCIPAL_FOR_EMI() + 3) * (financialCalc.getN_INTEREST_FOR_EMI() / 1200);
//            financialCalc.setN_PENAL_INTEREST_CD((long) penalInterestCd);
////                } else if (isNEMICol) {
////                    // Calculate N_PENAL_INTEREST_CD if N_EMI_COL is true for years after 1993
////                    double penalInterestCd = (cronGenerator.getV_PRINCIPAL_FOR_EMI() + 3) * (cronGenerator.getN_INTEREST_FOR_EMI() / 1200);
////                    cronGenerator.setN_PENAL_INTEREST_CD((long) penalInterestCd);
////                }
//
//            financial_calc_repo.save(financialCalc1);
//
//            financialCalc = financial_calc_repo.findByDATE(month);
//
//            // Calculate ARREAR balance
//            double arrearBalance = financialCalc.getN_ARREAR_CD() - (financialCalc.getN_ARREAR_COL() != null ? financialCalc.getN_ARREAR_COL() : 0.0);
//            financialCalc.setN_ARREAR_BALENCE(arrearBalance);
//
//            // Calculate EMI balance
//            double emiBalance = financialCalc.getN_EMI_CD() - (financialCalc.getN_EMI_COL() != null ? financialCalc.getN_EMI_COL() : 0.0);
//            financialCalc.setN_EMI_BALENCE(emiBalance);
//
//            // Calculate INITIAL DEPOSIT balance
//            double initialDepositBalance = financialCalc.getN_INITIAL_DEPOSIT_CD() - (financialCalc.getN_INITIAL_DEPOSIT_COL() != null ? financialCalc.getN_INITIAL_DEPOSIT_COL() : 0.0);
//            financialCalc.setN_INITIAL_DEPOSIT_BALENCE(initialDepositBalance);
//
//            // Calculate PENAL INTEREST balance
//            double penalInterestBalance = financialCalc.getN_PENAL_INTEREST_CD() - (financialCalc.getN_PENAL_INTEREST_COL() != null ? financialCalc.getN_PENAL_INTEREST_COL() : 0.0);
//            financialCalc.setN_PENAL_INTEREST_BALENCE(penalInterestBalance);
//
//            // Calculate INITIAL DEPOSIT PI balance
//            double initialDepositPIBalance = financialCalc.getN_INITIAL_DEPOSIT_PI_CD() - (financialCalc.getN_INITIAL_DEPOSIT_PI_COL() != null ? financialCalc.getN_INITIAL_DEPOSIT_PI_COL() : 0.0);
//            financialCalc.setN_INITIAL_DEPOSIT_PI_BALANCE(initialDepositPIBalance);
//
//            // Calculate TOTAL balance
//            double totalBalance = financialCalc.getN_TOTAL_EMI_DEMAND() - (financialCalc.getTOTAL_COL() != null ? financialCalc.getTOTAL_COL() : 0.0);
//            financialCalc.setTOTAL_BALANCE(totalBalance);
//
//            // If it's the first month, set initial values for ARREAR_BALANCE and N_ARREAR_COL
//            boolean month1 = false;
//            if (month1) {
//                financialCalc.setN_ARREAR_BALENCE(0.0); // Ini`tial ARREAR_BALENCE is 0
//                financialCalc.setN_ARREAR_COL(0.0); // Initial N_ARREAR_COL is 0
//            }
//
//            // If N_EMI_COL is not paid, carry forward to next month's N_ARREAR_BALENCE
//            if (emiBalance > 0) {
//                financialCalc.setN_ARREAR_BALENCE(financialCalc.getN_ARREAR_BALENCE() + emiBalance);
//            }
//
//            // Carry forward N_ARREAR_BALENCE to N_ARREAR_CD
//            financialCalc.setN_ARREAR_CD(financialCalc.getN_ARREAR_BALENCE());
//
////            Financial_Calc calculatedData = performCalculationsAndCreateFinancialCalc(fixedMonth, financialRecords, cronGenerator);
//
//
//            financial_calc_repo.save(financialCalc);
//

            } catch (IllegalArgumentException e) {
            // Handle the case where the cron expression is invalid
            // Log the error or take appropriate action
            System.err.println("Invalid cron expression: " + e.getMessage());
        }



    }

//    private Financial_Calc performCalculationsAndCreateFinancialCalc(LocalDate fixedMonth, List<Financial> financialRecords, Financial_Calc cronGenerator){
//
//        double emiBalance = cronGenerator.getV_EMI_OPENING(); // Implement this method
//        cronGenerator.setN_EMI_CD(emiBalance);
//
//        Financial financial = new Financial();
//
//        double initialDepo = financial.getN_INITIAL_DEPOSIT_TO_BE_PAID();
//        cronGenerator.setN_INITIAL_DEPOSIT_CD(initialDepo);
//
//
//        return cronGenerator;


    //Create/update COLLECTION
    public Financial_Calc updateCollectionData(CollectionRequestModel request) {

        Long nUnitId  =request.getN_UNIT_ID();
        LocalDate month = request.getMONTH();
        String transno = request.getV_TRANSACTION_NO();
        Double nEmiCol = request.getN_EMI_COL();
        Double nInitialDepositCol = request.getN_INITIAL_DEPOSIT_COL();
        Double nArrearCol  = request.getN_ARREAR_COL();
        Double nPenalInterestCol = request.getN_PENAL_INTEREST_COL();
        Double nInitialDepositPiCol = request.getN_INITIAL_DEPOSIT_PI_COL();
        String monthyear = request.getMONTHNYEAR();

        Financial_Calc existingData = financial_calc_repo.findByNUNITIDAndMONTHNYEAR(nUnitId, monthyear);


        if (existingData == null) {
            throw new EntityNotFoundException("Data not found for the provided N_UNIT_ID and MONTH");
        }
        else {
            double checkvalue = nEmiCol +  (existingData.getN_EMI_COL() !=null ? existingData.getN_EMI_COL() : 0 );

            // Check if nEmiCol is greater than N_EMI_CD
            if (checkvalue > existingData.getN_EMI_CD()) {
                // Calculate the excess amount
                double excessEmi = checkvalue - existingData.getN_EMI_CD();

                // Save the excess amount in the next month's row
                LocalDate nextMonth = month.plusMonths(1);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
                String nextMonthFormatted = nextMonth.format(formatter);
                System.out.println(nextMonth);
                Financial_Calc nextMonthData = financial_calc_repo.findByNUNITIDAndMONTHNYEAR(nUnitId, nextMonthFormatted);

                if (nextMonthData != null) {
                    nextMonthData.setN_EMI_COL((nextMonthData.getN_EMI_COL() != null ? nextMonthData.getN_EMI_COL() : 0) + excessEmi);
                    // Update the total for next month
                    double nextMonthTotal = (nextMonthData.getTOTAL_COL() != null ? nextMonthData.getTOTAL_COL() : 0) + excessEmi;
                    nextMonthData.setTOTAL_COL(nextMonthTotal);
                    financial_calc_repo.save(nextMonthData);
                }
            }

            else {
                existingData.setN_EMI_COL(checkvalue);
                financial_calc_repo.save(existingData);
            }
            existingData.setN_INITIAL_DEPOSIT_COL((existingData.getN_INITIAL_DEPOSIT_COL() != null ? existingData.getN_INITIAL_DEPOSIT_COL() : 0) + nInitialDepositCol);
            existingData.setN_ARREAR_COL((existingData.getN_ARREAR_COL() != null ? existingData.getN_ARREAR_COL() : 0) + nArrearCol);
            existingData.setN_PENAL_INTEREST_COL((existingData.getN_PENAL_INTEREST_COL() != null ? existingData.getN_PENAL_INTEREST_COL() : 0) + nPenalInterestCol);
            existingData.setN_INITIAL_DEPOSIT_PI_COL((existingData.getN_INITIAL_DEPOSIT_PI_COL() != null ? existingData.getN_INITIAL_DEPOSIT_PI_COL() : 0) + nInitialDepositPiCol);

            double totalCol = (existingData.getTOTAL_COL() != null ? existingData.getTOTAL_COL() : 0) +
                    nEmiCol + nInitialDepositCol + nArrearCol + nPenalInterestCol + nInitialDepositPiCol;
            existingData.setTOTAL_COL(totalCol);

            CollectionDateData collectionDateData = new CollectionDateData();
            collectionDateData.setNUNITID(nUnitId);
            collectionDateData.setDATE(month);
            collectionDateData.setV_TRANSACTION_NO(transno);
            collectionDateData.setN_EMI_COL(nEmiCol);
            collectionDateData.setN_INITIAL_DEPOSIT_COL(nInitialDepositCol);
            collectionDateData.setN_ARREAR_COL(nArrearCol);
            collectionDateData.setN_PENAL_INTEREST_COL(nPenalInterestCol);
            collectionDateData.setN_INITIAL_DEPOSIT_PI_COL(nInitialDepositPiCol);

            collectionRepo.save(collectionDateData);
            existingData.updateBalanceColumns();

            return financial_calc_repo.save(existingData);
        }
    }

    @Override
    public Financial_Calc GetDemandModel(GetDemandModel request) {
        return null;
    }


//____________________________CUSTOMERAPPLICATION____________________________________


    //Get Scheme Wise List for Booking
    @Override
    public List<SchemeData> getAllWebsite() {
        return schemeDataRepo.findAll();
    }


    @Override
    public List<Map<String, Object>> getAllSchemeForWebsite() {
        List<Object[]> schemeData = schemeDataRepo.findSchemeDetail();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] row : schemeData) {
            Map<String, Object> propertyMap = new HashMap<>();
            propertyMap.put("N_ID", row[0]);        // Scheme N_ID
            propertyMap.put("From_Date", row[1]);
            propertyMap.put("To_Date", row[2]);
            propertyMap.put("Reservation_Status", row[3]);
            propertyMap.put("Project_Status", row[4]);
            propertyMap.put("Circle",  row[5]);
            propertyMap.put("Division",  row[6]);
            propertyMap.put("District",  row[7]);
            propertyMap.put("Scheme_Name",  row[8]);
            propertyMap.put("Unit_Type",  row[9]);
            propertyMap.put("Scheme_Type",  row[10]);
            propertyMap.put("Total_Units",  row[11]);
            propertyMap.put("Allotted_Units",  row[12]);
            propertyMap.put("Unsold_Units",  row[13]);
            propertyMap.put("Selling_Extent",  row[14]);
            propertyMap.put("Starting_From",  row[15]);
            propertyMap.put("Photo",  row[16]);

            result.add(propertyMap);
        }

        return result;
    }


    //Save Application
//    @Transactional
//    @Override
//    public CustomerApplication saveCustomerApplication(CustomerApplication customerApplication) {
//
//        if (customerApplication.getNativeOfTamilnadu() != null) {
//            String base64FileData = customerApplication.getNativeOfTamilnadu();
//            byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
//            String generatedFileName = UUID.randomUUID().toString() + ".pdf";
//            String bucketUrl = "http://tnhb-property-docs.s3-website.ap-south-1.amazonaws.com/";
//            String filepath = bucketUrl + generatedFileName;
//            byte[] fileBytes = decodedFileData;
//            AwsBasicCredentials credentials = AwsBasicCredentials.create("AKIAR4WRUXSBYP5V7J42", "b8YXyxvNaQpNVr63UxCm51KckgMV8ZZ1WRLFwYl/");
//
//            S3Client s3Client = S3Client.builder()
//                    .region(Region.AP_SOUTH_1)
//                    .credentialsProvider(() -> credentials)
//                    .build();
//
//            PutObjectRequest request = PutObjectRequest.builder()
//                    .bucket("tnhb-property-docs")
//                    .key(generatedFileName)
//                    .contentType("application/pdf")
//                    .build();
//
//            PutObjectResponse response = s3Client.putObject(request, RequestBody.fromBytes(fileBytes));
//            customerApplication.setNativeOfTamilnadu_filename(generatedFileName);
//            customerApplication.setNativeOfTamilnadu_filepath(filepath);
//        }
//
//        if (customerApplication.getBirthCertificate() != null) {
//            String base64FileData = customerApplication.getBirthCertificate();
//            byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
//            String generatedFileName = UUID.randomUUID().toString() + ".pdf";
//            String bucketUrl = "http://tnhb-property-docs.s3-website.ap-south-1.amazonaws.com/";
//            String filepath = bucketUrl + generatedFileName;
//            byte[] fileBytes = decodedFileData;
//            AwsBasicCredentials credentials = AwsBasicCredentials.create("AKIAR4WRUXSBYP5V7J42", "b8YXyxvNaQpNVr63UxCm51KckgMV8ZZ1WRLFwYl/");
//
//            S3Client s3Client = S3Client.builder()
//                    .region(Region.AP_SOUTH_1)
//                    .credentialsProvider(() -> credentials)
//                    .build();
//
//            PutObjectRequest request = PutObjectRequest.builder()
//                    .bucket("tnhb-property-docs")
//                    .key(generatedFileName)
//                    .contentType("application/pdf")
//                    .build();
//
//            PutObjectResponse response = s3Client.putObject(request, RequestBody.fromBytes(fileBytes));
//                customerApplication.setBirthCertificate_filename(generatedFileName);
//                customerApplication.setBirthCertificate_filepath(filepath);
//
//        }
//
//        if (customerApplication.getAadhaarProof() != null) {
//            String base64FileData = customerApplication.getAadhaarProof();
//            byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
//            String generatedFileName = UUID.randomUUID().toString() + ".pdf";
//            String bucketUrl = "http://tnhb-property-docs.s3-website.ap-south-1.amazonaws.com/";
//            String filepath = bucketUrl + generatedFileName;
//            byte[] fileBytes = decodedFileData;
//            AwsBasicCredentials credentials = AwsBasicCredentials.create("AKIAR4WRUXSBYP5V7J42", "b8YXyxvNaQpNVr63UxCm51KckgMV8ZZ1WRLFwYl/");
//
//            S3Client s3Client = S3Client.builder()
//                    .region(Region.AP_SOUTH_1)
//                    .credentialsProvider(() -> credentials)
//                    .build();
//
//            PutObjectRequest request = PutObjectRequest.builder()
//                    .bucket("tnhb-property-docs")
//                    .key(generatedFileName)
//                    .contentType("application/pdf")
//                    .build();
//
//            PutObjectResponse response = s3Client.putObject(request, RequestBody.fromBytes(fileBytes));
//                customerApplication.setAadhaarProof_filename(generatedFileName);
//                customerApplication.setAadhaarProof_filepath(filepath);
//
//        }
//
//        if (customerApplication.getPanProof() != null) {
//            String base64FileData = customerApplication.getPanProof();
//            byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
//            String generatedFileName = UUID.randomUUID().toString() + ".pdf";
//            String bucketUrl = "http://tnhb-property-docs.s3-website.ap-south-1.amazonaws.com/";
//            String filepath = bucketUrl + generatedFileName;
//            byte[] fileBytes = decodedFileData;
//            AwsBasicCredentials credentials = AwsBasicCredentials.create("AKIAR4WRUXSBYP5V7J42", "b8YXyxvNaQpNVr63UxCm51KckgMV8ZZ1WRLFwYl/");
//
//            S3Client s3Client = S3Client.builder()
//                    .region(Region.AP_SOUTH_1)
//                    .credentialsProvider(() -> credentials)
//                    .build();
//
//            PutObjectRequest request = PutObjectRequest.builder()
//                    .bucket("tnhb-property-docs")
//                    .key(generatedFileName)
//                    .contentType("application/pdf")
//                    .build();
//
//            PutObjectResponse response = s3Client.putObject(request, RequestBody.fromBytes(fileBytes));
//                customerApplication.setPanProof_filename(generatedFileName);
//                customerApplication.setPanProof_filepath(filepath);
//
//        }
//
//        if (customerApplication.getIncomeCertificate() != null) {
//            String base64FileData = customerApplication.getIncomeCertificate();
//            byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
//            String generatedFileName = UUID.randomUUID().toString() + ".pdf";
//            String bucketUrl = "http://tnhb-property-docs.s3-website.ap-south-1.amazonaws.com/";
//            String filepath = bucketUrl + generatedFileName;
//            byte[] fileBytes = decodedFileData;
//            AwsBasicCredentials credentials = AwsBasicCredentials.create("AKIAR4WRUXSBYP5V7J42", "b8YXyxvNaQpNVr63UxCm51KckgMV8ZZ1WRLFwYl/");
//
//            S3Client s3Client = S3Client.builder()
//                    .region(Region.AP_SOUTH_1)
//                    .credentialsProvider(() -> credentials)
//                    .build();
//
//            PutObjectRequest request = PutObjectRequest.builder()
//                    .bucket("tnhb-property-docs")
//                    .key(generatedFileName)
//                    .contentType("application/pdf")
//                    .build();
//
//            PutObjectResponse response = s3Client.putObject(request, RequestBody.fromBytes(fileBytes));
//                customerApplication.setIncomeCertificate_filename(generatedFileName);
//                customerApplication.setIncomeCertificate_filepath(filepath);
//
//        }
//
//        if (customerApplication.getReservationCategoryProof() != null) {
//            String base64FileData = customerApplication.getReservationCategoryProof();
//            byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
//            String generatedFileName = UUID.randomUUID().toString() + ".pdf";
//            String bucketUrl = "http://tnhb-property-docs.s3-website.ap-south-1.amazonaws.com/";
//            String filepath = bucketUrl + generatedFileName;
//            byte[] fileBytes = decodedFileData;
//            AwsBasicCredentials credentials = AwsBasicCredentials.create("AKIAR4WRUXSBYP5V7J42", "b8YXyxvNaQpNVr63UxCm51KckgMV8ZZ1WRLFwYl/");
//
//            S3Client s3Client = S3Client.builder()
//                    .region(Region.AP_SOUTH_1)
//                    .credentialsProvider(() -> credentials)
//                    .build();
//
//            PutObjectRequest request = PutObjectRequest.builder()
//                    .bucket("tnhb-property-docs")
//                    .key(generatedFileName)
//                    .contentType("application/pdf")
//                    .build();
//
//            PutObjectResponse response = s3Client.putObject(request, RequestBody.fromBytes(fileBytes));
//                customerApplication.setReservationCategoryProof_filename(generatedFileName);
//                customerApplication.setReservationCategoryProof_filepath(filepath);
//
//        }
//
//        if (customerApplication.getReservationSubCategoryProof() != null) {
//            String base64FileData = customerApplication.getReservationSubCategoryProof();
//            byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
//            String generatedFileName = UUID.randomUUID().toString() + ".pdf";
//            String bucketUrl = "http://tnhb-property-docs.s3-website.ap-south-1.amazonaws.com/";
//            String filepath = bucketUrl + generatedFileName;
//            byte[] fileBytes = decodedFileData;
//            AwsBasicCredentials credentials = AwsBasicCredentials.create("AKIAR4WRUXSBYP5V7J42", "b8YXyxvNaQpNVr63UxCm51KckgMV8ZZ1WRLFwYl/");
//
//            S3Client s3Client = S3Client.builder()
//                    .region(Region.AP_SOUTH_1)
//                    .credentialsProvider(() -> credentials)
//                    .build();
//
//            PutObjectRequest request = PutObjectRequest.builder()
//                    .bucket("tnhb-property-docs")
//                    .key(generatedFileName)
//                    .contentType("application/pdf")
//                    .build();
//
//            PutObjectResponse response = s3Client.putObject(request, RequestBody.fromBytes(fileBytes));
//                customerApplication.setReservationSubCategoryProof_filename(generatedFileName);
//                customerApplication.setReservationSubCategoryProof_filepath(filepath);
//
//        }
//
//
//        if (customerApplication.getPhoto() != null) {
//            String base64FileData = customerApplication.getPhoto();
//            byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
//            String generatedFileName = customerApplication.getPhoto_filename();
//            String uniqueFileName = generatedFileName;
//            String bucketUrl = "http://tnhb-property-docs.s3-website.ap-south-1.amazonaws.com/";
//            String filepath = bucketUrl + generatedFileName;
//            byte[] fileBytes = decodedFileData;
//            AwsBasicCredentials credentials = AwsBasicCredentials.create("AKIAR4WRUXSBYP5V7J42", "b8YXyxvNaQpNVr63UxCm51KckgMV8ZZ1WRLFwYl/");
//
//            S3Client s3Client = S3Client.builder()
//                    .region(Region.AP_SOUTH_1)
//                    .credentialsProvider(() -> credentials)
//                    .build();
//
//            PutObjectRequest request = PutObjectRequest.builder()
//                    .bucket("tnhb-property-docs")
//                    .key(generatedFileName)
//
//                    .build();
//
//            PutObjectResponse response = s3Client.putObject(request, RequestBody.fromBytes(fileBytes));
//                customerApplication.setPhoto_filename(generatedFileName);
//                customerApplication.setPhoto_filepath(filepath);
//
//        }
//
//        if (customerApplication.getSignature() != null) {
//            String base64FileData = customerApplication.getSignature();
//            byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
//            String generatedFileName = customerApplication.getSignature_filename();
//            String uniqueFileName = generatedFileName;
//            String bucketUrl = "http://tnhb-property-docs.s3-website.ap-south-1.amazonaws.com/";
//            String filepath = bucketUrl + generatedFileName;
//            byte[] fileBytes = decodedFileData;
//            AwsBasicCredentials credentials = AwsBasicCredentials.create("AKIAR4WRUXSBYP5V7J42", "b8YXyxvNaQpNVr63UxCm51KckgMV8ZZ1WRLFwYl/");
//
//            S3Client s3Client = S3Client.builder()
//                    .region(Region.AP_SOUTH_1)
//                    .credentialsProvider(() -> credentials)
//                    .build();
//
//            PutObjectRequest request = PutObjectRequest.builder()
//                    .bucket("tnhb-property-docs")
//                    .key(generatedFileName)
//
//                    .build();
//
//            PutObjectResponse response = s3Client.putObject(request, RequestBody.fromBytes(fileBytes));
//                customerApplication.setSignature_filename(generatedFileName);
//                customerApplication.setSignature_filepath(filepath);
//
//          }
//         if (customerApplication.getAPPLICATION_UPLOAD() != null) {
//        String base64FileData = customerApplication.getAPPLICATION_UPLOAD();
//        byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
//        String generatedFileName = customerApplication.getApplication_upload_filename();
//        String uniqueFileName = generatedFileName;
//        String bucketUrl = "http://tnhb-property-docs.s3-website.ap-south-1.amazonaws.com/";
//        String filepath = bucketUrl + generatedFileName;
//        byte[] fileBytes = decodedFileData;
//        AwsBasicCredentials credentials = AwsBasicCredentials.create("AKIAR4WRUXSBWFW47VVO", "iG602vjmGQXcs8CCk2NHyWaVGyfHMcN3eGhVsygl");
//
//        S3Client s3Client = S3Client.builder()
//                .region(Region.AP_SOUTH_1)
//                .credentialsProvider(() -> credentials)
//                .build();
//
//        PutObjectRequest request = PutObjectRequest.builder()
//                .bucket("tnhb-property-docs")
//                .key(generatedFileName)
//                .contentType("application/pdf")
//                .build();
//
//        PutObjectResponse response = s3Client.putObject(request, RequestBody.fromBytes(fileBytes));
//
//        customerApplication.setApplication_upload_filename(generatedFileName);
//        customerApplication.setApplication_upload_filepath(filepath);
//
//    }
//
//        int generatedApplicationNo = getNextSequentialApplicationNo();
//        customerApplication.setApplicationNo(String.format("%07d", generatedApplicationNo));
//        return customerApplicationRepo.save(customerApplication);
//    }






    //LOCAL PATH SAVE APPLICATION
    @Transactional
    @Override
    public CustomerApplication saveCustomerApplication(CustomerApplication customerApplication) {
        if (customerApplication.getNativeOfTamilnadu() != null) {
            String base64FileData = customerApplication.getNativeOfTamilnadu();
            byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
            String generatedFileName = UUID.randomUUID().toString() + ".pdf";
            File dest = new File(uploadDir, generatedFileName);

            try {
                Files.write(dest.toPath(), decodedFileData);
                customerApplication.setNativeOfTamilnadu_filename(generatedFileName);
                customerApplication.setNativeOfTamilnadu_filepath(dest.getAbsolutePath());
            } catch (IOException e) {

            }
        }

        if (customerApplication.getBirthCertificate() != null) {
            String base64FileData = customerApplication.getBirthCertificate();
            byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
            String generatedFileName = UUID.randomUUID().toString() + ".pdf";
            File dest = new File(uploadDir, generatedFileName);

            try {
                Files.write(dest.toPath(), decodedFileData);
                customerApplication.setBirthCertificate_filename(generatedFileName);
                customerApplication.setBirthCertificate_filepath(dest.getAbsolutePath());
            } catch (IOException e) {

            }
        }

        if (customerApplication.getAadhaarProof() != null) {
            String base64FileData = customerApplication.getAadhaarProof();
            byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
            String generatedFileName = UUID.randomUUID().toString() + ".pdf";
            File dest = new File(uploadDir, generatedFileName);

            try {
                Files.write(dest.toPath(), decodedFileData);
                customerApplication.setAadhaarProof_filename(generatedFileName);
                customerApplication.setAadhaarProof_filepath(dest.getAbsolutePath());
            } catch (IOException e) {

            }
        }

        if (customerApplication.getPanProof() != null) {
            String base64FileData = customerApplication.getPanProof();
            byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
            String generatedFileName = UUID.randomUUID().toString() + ".pdf";
            File dest = new File(uploadDir, generatedFileName);

            try {
                Files.write(dest.toPath(), decodedFileData);
                customerApplication.setPanProof_filename(generatedFileName);
                customerApplication.setPanProof_filepath(dest.getAbsolutePath());
            } catch (IOException e) {

            }
        }

        if (customerApplication.getIncomeCertificate() != null) {
            String base64FileData = customerApplication.getIncomeCertificate();
            byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
            String generatedFileName = UUID.randomUUID().toString() + ".pdf";
            File dest = new File(uploadDir, generatedFileName);

            try {
                Files.write(dest.toPath(), decodedFileData);
                customerApplication.setIncomeCertificate_filename(generatedFileName);
                customerApplication.setIncomeCertificate_filepath(dest.getAbsolutePath());
            } catch (IOException e) {

            }
        }

        if (customerApplication.getReservationCategoryProof() != null) {
            String base64FileData = customerApplication.getReservationCategoryProof();
            byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
            String generatedFileName = UUID.randomUUID().toString() + ".pdf";
            File dest = new File(uploadDir, generatedFileName);

            try {
                Files.write(dest.toPath(), decodedFileData);
                customerApplication.setReservationCategoryProof_filename(generatedFileName);
                customerApplication.setReservationCategoryProof_filepath(dest.getAbsolutePath());
            } catch (IOException e) {

            }
        }

        if (customerApplication.getReservationSubCategoryProof() != null) {
            String base64FileData = customerApplication.getReservationSubCategoryProof();
            byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
            String generatedFileName = UUID.randomUUID().toString() + ".pdf";
            File dest = new File(uploadDir, generatedFileName);

            try {
                Files.write(dest.toPath(), decodedFileData);
                customerApplication.setReservationSubCategoryProof_filename(generatedFileName);
                customerApplication.setReservationSubCategoryProof_filepath(dest.getAbsolutePath());
            } catch (IOException e) {

            }
        }


        if (customerApplication.getPhoto() != null) {
            String base64FileData = customerApplication.getPhoto();
            byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
            String generatedFileName = customerApplication.getPhoto_filename();
            String uniqueFileName = generatedFileName;
            File dest = new File(uploadDir, generatedFileName);

            try {
                Files.write(dest.toPath(), decodedFileData);
                customerApplication.setPhoto_filename(generatedFileName);
                customerApplication.setPhoto_filepath(dest.getAbsolutePath());
            } catch (IOException e) {

            }
        }
    if (customerApplication.getAPPLICATION_UPLOAD() != null) {
        String base64FileData = customerApplication.getAPPLICATION_UPLOAD();
        byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
        String generatedFileName = customerApplication.getApplication_upload_filename();
        String uniqueFileName = generatedFileName;
        File dest = new File(uploadDir, generatedFileName);

        try {
            Files.write(dest.toPath(), decodedFileData);
            customerApplication.setApplication_upload_filename(generatedFileName);
            customerApplication.setApplication_upload_filepath(dest.getAbsolutePath());
        } catch (IOException e) {

        }
    }

        if (customerApplication.getSignature() != null) {
            String base64FileData = customerApplication.getSignature();
            byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
            String generatedFileName = customerApplication.getSignature_filename();
            String uniqueFileName = generatedFileName;

            File dest = new File(uploadDir, generatedFileName);

            try {
                Files.write(dest.toPath(), decodedFileData);
                customerApplication.setSignature_filename(generatedFileName);
                customerApplication.setSignature_filepath(dest.getAbsolutePath());
            } catch (IOException e) {

            }
        }

        int generatedApplicationNo = getNextSequentialApplicationNo();
        customerApplication.setApplicationNo(String.format("%07d", generatedApplicationNo));
        return customerApplicationRepo.save(customerApplication);
    }






    //Auto-Generated Number

    private int getNextSequentialApplicationNo() {
        // Fetch the current sequential number from the database
        SequentialNumber sequentialNumber = sequenceNumberRepository.findById(1).orElse(null);

        if (sequentialNumber == null) {
            // Initialize the sequential number if it doesn't exist
            sequentialNumber = new SequentialNumber();
            sequentialNumber.setId(1);
            sequentialNumber.setSequenceValue(1);
        } else {
            // Increment the sequential number
            sequentialNumber.setSequenceValue(sequentialNumber.getSequenceValue() + 1);
        }

        // Save the updated sequential number back to the database
        sequenceNumberRepository.save(sequentialNumber);

        return sequentialNumber.getSequenceValue();
    }


    //Edit Customer Application
    @Override
    public CustomerApplication updateCustomerApplicationById(Long N_ID, CustomerApplication updatedCustomerApplication) {
        Optional<CustomerApplication> existingCustomerApplication = customerApplicationRepo.findById(N_ID);
        if (existingCustomerApplication.isPresent()) {
            CustomerApplication currentCustomerApplication = existingCustomerApplication.get();

            Field[] fields = CustomerApplication.class.getDeclaredFields();

            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    Object updatedValue = field.get(updatedCustomerApplication);


                    if (updatedValue != null) {

                        field.set(currentCustomerApplication, updatedValue);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }


            CustomerApplication updatedEntity = customerApplicationRepo.save(currentCustomerApplication);
            return updatedEntity;
        } else {
            return null;
        }
    }



    //Get all Application for Admin
    @Override
    public List<CustomerApplication> getAllCustomerApplications() {
        return customerApplicationRepo.findAll();
    }

    //Get Single Application of Customer
    @Override
    public CustomerApplication getCustomerApplication(Long id) {
        Optional<CustomerApplication> optionalCustomerApplication = customerApplicationRepo.findById(id);
        return optionalCustomerApplication.orElse(null);
    }

    //Get Applications for Single Customer
    @Override
    public List<CustomerApplication> getCustomerApplications(Long id) {
        return customerApplicationRepo.findByCustomerid(id);
    }

    //Get all Schemes with Customer Application
    @Override
    public List<Map<String, Object>> getAllSchemesWithAppl() {
        List<Object[]> schemeDetail = customerApplicationRepo.getSchemeDetail();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] row : schemeDetail) {
            Map<String, Object> propertyMap = new HashMap<>();
            propertyMap.put("scheme", (String) row[0]);
            propertyMap.put("cr_code", (String) row[1]);
            propertyMap.put("cc_code", (String) row[2]);
            propertyMap.put("d_code", (String) row[3]);
            propertyMap.put("scheme_code", (String) row[4]);
            propertyMap.put("splace", (String) row[5]);
            propertyMap.put("district", (String) row[6]);
            propertyMap.put("unit_type", (String) row[7]);
            propertyMap.put("type", (String) row[8]);

            result.add(propertyMap);
        }

        return result;
    }

    //Get all Applications Of One Scheme
    @Override
    public List<CustomerApplication> getAllApplOfOneScheme(String scheme) {
        return customerApplicationRepo.findByScheme(scheme);
    }

    //Get All Pending Applications
    @Override
    public List<CustomerApplication> getPendingCustomerApplications() {
        return customerApplicationRepo.getPendingCustomerApplication();
    }

    //Get Category Wise Count List
    @Override
    public Optional<SchemeData> getbyidSchemedata(Long n_id){
        return schemeDataRepo.findById(n_id);
    }


    //Get Block/Floor Details for Booking
    @Override
    public List<Map<String, Object>> getPropertyData(Long scheme) {
        List<Map<String, Object>> formattedOutput = new ArrayList<>();
        List<Object[]> queryResults = unitDataRepo.findPropertyData(scheme);

        Map<String, Map<String, Object>> schemeMap = new HashMap<>();

        for (Object[] row : queryResults) {
            String schemeCode = (String) row[0];
            String schemeName = (String) row[1];
            String type = (String) row[2];
            String unitType = (String) row[3];
            int totalUnits = ((Number) row[4]).intValue();
            String unit = (String) row[5];
            String floor = (String) row[6];
            String block = (String) row[7];
            String status = (String) row[8];
            int nId = ((Number) row[9]).intValue();
            String unitCost = (String) row[10];

            Map<String, Object> currentScheme = schemeMap.get(schemeCode);

            if (currentScheme == null) {
                currentScheme = new HashMap<>();
                currentScheme.put("scheme_code", schemeCode);
                currentScheme.put("scheme_name", schemeName);
                currentScheme.put("Type", type);
                currentScheme.put("UnitType", unitType);
                currentScheme.put("total_units", totalUnits);
                currentScheme.put("Unsold", 0);
                currentScheme.put("Sold", 0);
                currentScheme.put("units", new ArrayList<>());
                schemeMap.put(schemeCode, currentScheme);
                formattedOutput.add(currentScheme);
            }

            Map<String, Object> unitData = new HashMap<>();
            unitData.put("unit", unit);
            if (floor != null) {
                unitData.put("floor", floor);
            }
            if (block != null) {
                unitData.put("block", block);
            }
            unitData.put("status", status);
            unitData.put("n_id", nId);
            unitData.put("unit_cost", unitCost);
            ((List<Map<String, Object>>) currentScheme.get("units")).add(unitData);

            // Update Sold and Unsold counts
            if ("No".equalsIgnoreCase(status) || "pending".equalsIgnoreCase(status)) {
                currentScheme.put("Unsold", (int) currentScheme.get("Unsold") + 1);
            }
            currentScheme.put("Sold", totalUnits - ((int) currentScheme.get("Unsold") ));
        }

        return formattedOutput;
    }

    //Get Block/Floor Booking for AllSchemes
    @Override
    public List<Map<String, Object>> getUnitAllScheme() {
        List<Map<String, Object>> formattedOutput = new ArrayList<>();
        List<Object[]> queryResults = unitDataRepo.findAllSchemeUnit();

        Map<String, Map<String, Object>> schemeMap = new HashMap<>();

        for (Object[] row : queryResults) {
            String schemeCode = (String) row[0];
            String schemeName = (String) row[1];
            String type = (String) row[2];
            String unitType = (String) row[3];
            int totalUnits = ((Number) row[4]).intValue();
            String unit = (String) row[5];
            String floor = (String) row[6];
            String block = (String) row[7];
            String status = (String) row[8];
            int nId = ((Number) row[9]).intValue();
            String unitCost = (String) row[10];
            String circle = (String) row[11];
            String division = (String) row[12];
            String district = (String) row[13];

            Map<String, Object> currentScheme = schemeMap.get(schemeCode);

            if (currentScheme == null) {
                currentScheme = new HashMap<>();
                currentScheme.put("scheme_code", schemeCode);
                currentScheme.put("scheme_name", schemeName);
                currentScheme.put("Type", type);
                currentScheme.put("UnitType", unitType);
                currentScheme.put("total_units", totalUnits);
                currentScheme.put("Unsold", 0);
                currentScheme.put("Sold", 0);
                currentScheme.put("circle", circle);
                currentScheme.put("division", division);
                currentScheme.put("district", district);

                currentScheme.put("units", new ArrayList<>());
                schemeMap.put(schemeCode, currentScheme);
                formattedOutput.add(currentScheme);
            }

            Map<String, Object> unitData = new HashMap<>();
            unitData.put("unit", unit);
            if (floor != null) {
                unitData.put("floor", floor);
            }
            if (block != null) {
                unitData.put("block", block);
            }
            unitData.put("status", status);
            unitData.put("n_id", nId);
            unitData.put("unit_cost", unitCost);
            ((List<Map<String, Object>>) currentScheme.get("units")).add(unitData);

            // Update Sold and Unsold counts
            if ("No".equalsIgnoreCase(status) || "pending".equalsIgnoreCase(status)) {
                currentScheme.put("Unsold", (int) currentScheme.get("Unsold") + 1);
            }
            currentScheme.put("Sold", totalUnits - ((int) currentScheme.get("Unsold") ));
        }

        return formattedOutput;
    }
       //Get Scheme name by scheme id
    @Override
    public String getSchemeName(Long scheme) {
        return schemeDataRepo.findSchemeName(scheme);
    }

    //Category Wise Count List Increment/Decrement
    @Transactional
    @Override
    public ResponseEntity<String> updateDetailsOnBooking(Long schemeId, Long unitId, String reservation, String status, String category, String subCategory) {
        Optional<SchemeData> schemeData = schemeDataRepo.findById(schemeId);
        SchemeData scheme = schemeData.get();

        Optional<UnitData> unitData = unitDataRepo.findById(unitId);
        UnitData unit = unitData.get();

        if(reservation.equalsIgnoreCase("Yes")) {

            if (status.equalsIgnoreCase("Pending")) {
                decrementCategoryField(scheme, category, subCategory);// Change the UnitData status to 'pending'
                unit.setV_UNIT_ALLOTTED_STATUS("Pending");
            } else if (status.equalsIgnoreCase("accept")) {
                // Change the UnitData status to 'Yes'
                unit.setV_UNIT_ALLOTTED_STATUS("Yes");
            } else if (status.equalsIgnoreCase("reject")) {
                // Increment the corresponding category field in SchemeData
                incrementCategoryField(scheme, category, subCategory);
                // Change the UnitData status to 'No'
                unit.setV_UNIT_ALLOTTED_STATUS("No");
            }
        }else { // Handle the case when reservation is 'No'
            if (status.equalsIgnoreCase("Pending")) {
                unit.setV_UNIT_ALLOTTED_STATUS("Pending");
            } else if (status.equalsIgnoreCase("accept")) {
                unit.setV_UNIT_ALLOTTED_STATUS("Yes");
            } else if (status.equalsIgnoreCase("reject")) {
                unit.setV_UNIT_ALLOTTED_STATUS("No");
            }
        }

        // Update both SchemeData and UnitData
        schemeDataRepo.save(scheme);
        unitDataRepo.save(unit);
        unitDataRepo.updateSchemeData(schemeId);
        return ResponseEntity.ok("Data updated successfully.");
    }

    private void incrementCategoryField(SchemeData scheme, String category, String subCategory) {
        switch (category) {
            case "Scheduled Caste":
                if(subCategory!=null){
                    if (subCategory.equalsIgnoreCase("Artists")) {
                        scheme.setV_ARTISTS_UNSOLD_UNITS(scheme.getV_ARTISTS_UNSOLD_UNITS() + 1);
                    }else if (subCategory.equalsIgnoreCase("PoliticalSufferers")) {
                        scheme.setV_POLITICAL_SUFFERERS_UNSOLD_UNITS(scheme.getV_POLITICAL_SUFFERERS_UNSOLD_UNITS() + 1);
                    }else if (subCategory.equalsIgnoreCase("Physically Challenged")) {
                        scheme.setV_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(scheme.getV_PHYSICALLY_CHALLENGED_UNSOLD_UNITS() + 1);
                    }
                }else{
                    scheme.setV_SCHEDULED_CASTE_UNSOLD_UNITS(scheme.getV_SCHEDULED_CASTE_UNSOLD_UNITS() + 1);
                }
                break;

            case "Scheduled Tribes":
                if(subCategory!=null && subCategory.equalsIgnoreCase("Physically Challenged")) {
                    scheme.setV_ST_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(scheme.getV_ST_PHYSICALLY_CHALLENGED_UNSOLD_UNITS() + 1);
                }else{
                    scheme.setV_SCHEDULED_TRIBES_UNSOLD_UNITS(scheme.getV_SCHEDULED_TRIBES_UNSOLD_UNITS() + 1);
                }
                break;

            case "State government":
                if(subCategory!=null){
                    if (subCategory.equalsIgnoreCase("Physically Challenged")) {
                        scheme.setV_STATE_GOVT_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(scheme.getV_STATE_GOVT_PHYSICALLY_CHALLENGED_UNSOLD_UNITS() + 1);
                    }else if (subCategory.equalsIgnoreCase("Judicial Officers")) {
                        scheme.setV_STATE_GOVT_JUDICIAL_OFFICERS_UNSOLD_UNITS(scheme.getV_STATE_GOVT_JUDICIAL_OFFICERS_UNSOLD_UNITS() + 1);
                    }
                }else{
                    scheme.setV_STATE_GOVERNMENT_UNSOLD_UNITS(scheme.getV_STATE_GOVERNMENT_UNSOLD_UNITS() + 1);
                }
                break;

            case "Central Govt, TNEB, Local Bodies":
                if(subCategory!=null && subCategory.equalsIgnoreCase("Physically Challenged")) {
                    scheme.setV_CENTRAL_GOVT_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(scheme.getV_CENTRAL_GOVT_PHYSICALLY_CHALLENGED_UNSOLD_UNITS() + 1);
                }else{
                    scheme.setV_CENTRAL_TNEB_LOCAL_BODIES_UNSOLD_UNITS(scheme.getV_CENTRAL_TNEB_LOCAL_BODIES_UNSOLD_UNITS() + 1);
                }
                break;

            case "Defence":
                if(subCategory!=null){
                    if (subCategory.equalsIgnoreCase("Awardees")) {
                        scheme.setV_AWARDEES_UNSOLD_UNITS(scheme.getV_AWARDEES_UNSOLD_UNITS() + 1);
                    }else if (subCategory.equalsIgnoreCase("Physically Challenged")) {
                        scheme.setV_DEFENCE_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(scheme.getV_DEFENCE_PHYSICALLY_CHALLENGED_UNSOLD_UNITS() + 1);
                    }
                }else{
                    scheme.setV_DEFENCE_UNSOLD_UNITS(scheme.getV_DEFENCE_UNSOLD_UNITS() + 1);
                }
                break;

            case "Dhobbies & Barbers":
                if(subCategory!=null){
                    if (subCategory.equalsIgnoreCase("Artists")) {
                        scheme.setV_DHOBBIES_ARTISTS_UNSOLD_UNITS(scheme.getV_DHOBBIES_ARTISTS_UNSOLD_UNITS() + 1);
                    }else if (subCategory.equalsIgnoreCase("Physically Challenged")) {
                        scheme.setV_DHOBBIES_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(scheme.getV_DHOBBIES_PHYSICALLY_CHALLENGED_UNSOLD_UNITS() + 1);
                    }
                }else{
                    scheme.setV_DHOBBIES_BARBERS_UNSOLD_UNITS(scheme.getV_DHOBBIES_BARBERS_UNSOLD_UNITS() + 1);
                }
                break;

            case "Working Journalist":
                if(subCategory!=null && subCategory.equalsIgnoreCase("Physically Challenged")) {
                    scheme.setV_JOURNALIST_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(scheme.getV_JOURNALIST_PHYSICALLY_CHALLENGED_UNSOLD_UNITS() + 1);
                }else{
                    scheme.setV_WORKING_JOURNALIST_UNSOLD_UNITS(scheme.getV_WORKING_JOURNALIST_UNSOLD_UNITS() + 1);
                }
                break;

            case "Language Crusaders":
                if(subCategory!=null && subCategory.equalsIgnoreCase("Physically Challenged")) {
                    scheme.setV_LANGUAGE_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(scheme.getV_LANGUAGE_PHYSICALLY_CHALLENGED_UNSOLD_UNITS() + 1);
                }else{
                    scheme.setV_LANGUAGE_CRUSADERS_UNSOLD_UNITS(scheme.getV_LANGUAGE_CRUSADERS_UNSOLD_UNITS() + 1);
                }
                break;

            case "TNHB Employees":
                if(subCategory!=null && subCategory.equalsIgnoreCase("Physically Challenged")) {
                    scheme.setV_TNHB_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(scheme.getV_TNHB_PHYSICALLY_CHALLENGED_UNSOLD_UNITS() + 1);
                }else{
                    scheme.setV_TNHB_EMPLOYEES_UNSOLD_UNITS(scheme.getV_TNHB_EMPLOYEES_UNSOLD_UNITS() + 1);
                }
                break;

            case "General Public":
                if(subCategory!=null){
                    if (subCategory.equalsIgnoreCase("Artists")) {
                        scheme.setV_GENERAL_PUBLIC_ARTISTS_UNSOLD_UNITS(scheme.getV_GENERAL_PUBLIC_ARTISTS_UNSOLD_UNITS() + 1);
                    }else if (subCategory.equalsIgnoreCase("Political Sufferers")) {
                        scheme.setV_GENERAL_PUBLIC_POLITICAL_SUFFERERS_UNSOLD_UNITS(scheme.getV_GENERAL_PUBLIC_POLITICAL_SUFFERERS_UNSOLD_UNITS() + 1);
                    }else if (subCategory.equalsIgnoreCase("Physically Challenged")) {
                        scheme.setV_GENERAL_PUBLIC_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(scheme.getV_GENERAL_PUBLIC_PHYSICALLY_CHALLENGED_UNSOLD_UNITS() + 1);
                    }
                }else{
                    scheme.setV_GENERAL_PUBLIC_UNSOLD_UNITS(scheme.getV_GENERAL_PUBLIC_UNSOLD_UNITS() + 1);
                }
                break;
        }
    }

    private void decrementCategoryField(SchemeData scheme, String category, String subCategory) {
        switch (category) {
            case "Scheduled Caste":
                if(subCategory!=null){
                    if (subCategory.equalsIgnoreCase("Artists")) {
                        scheme.setV_ARTISTS_UNSOLD_UNITS(scheme.getV_ARTISTS_UNSOLD_UNITS() - 1);
                    }else if (subCategory.equalsIgnoreCase("Political Sufferers")) {
                        scheme.setV_POLITICAL_SUFFERERS_UNSOLD_UNITS(scheme.getV_POLITICAL_SUFFERERS_UNSOLD_UNITS() - 1);
                    }else if (subCategory.equalsIgnoreCase("Physically Challenged")) {
                        scheme.setV_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(scheme.getV_PHYSICALLY_CHALLENGED_UNSOLD_UNITS() - 1);
                    }
                }else{
                    scheme.setV_SCHEDULED_CASTE_UNSOLD_UNITS(scheme.getV_SCHEDULED_CASTE_UNSOLD_UNITS() - 1);
                }
                break;

            case "Scheduled Tribes":
                if(subCategory!=null && subCategory.equalsIgnoreCase("Physically Challenged")) {
                    scheme.setV_ST_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(scheme.getV_ST_PHYSICALLY_CHALLENGED_UNSOLD_UNITS() - 1);
                }else{
                    scheme.setV_SCHEDULED_TRIBES_UNSOLD_UNITS(scheme.getV_SCHEDULED_TRIBES_UNSOLD_UNITS() - 1);
                }
                break;

            case "State government":
                if(subCategory!=null){
                    if (subCategory.equalsIgnoreCase("Physically Challenged")) {
                        scheme.setV_STATE_GOVT_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(scheme.getV_STATE_GOVT_PHYSICALLY_CHALLENGED_UNSOLD_UNITS() - 1);
                    }else if (subCategory.equalsIgnoreCase("Judicial Officers")) {
                        scheme.setV_STATE_GOVT_JUDICIAL_OFFICERS_UNSOLD_UNITS(scheme.getV_STATE_GOVT_JUDICIAL_OFFICERS_UNSOLD_UNITS() - 1);
                    }
                }else{
                    scheme.setV_STATE_GOVERNMENT_UNSOLD_UNITS(scheme.getV_STATE_GOVERNMENT_UNSOLD_UNITS() - 1);
                }
                break;

            case "Central Govt, TNEB, Local Bodies":
                if(subCategory!=null && subCategory.equalsIgnoreCase("Physically Challenged")) {
                    scheme.setV_CENTRAL_GOVT_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(scheme.getV_CENTRAL_GOVT_PHYSICALLY_CHALLENGED_UNSOLD_UNITS() - 1);
                }else{
                    scheme.setV_CENTRAL_TNEB_LOCAL_BODIES_UNSOLD_UNITS(scheme.getV_CENTRAL_TNEB_LOCAL_BODIES_UNSOLD_UNITS() - 1);
                }
                break;

            case "Defence":
                if(subCategory!=null){
                    if (subCategory.equalsIgnoreCase("Awardees")) {
                        scheme.setV_AWARDEES_UNSOLD_UNITS(scheme.getV_AWARDEES_UNSOLD_UNITS() - 1);
                    }else if (subCategory.equalsIgnoreCase("Physically Challenged")) {
                        scheme.setV_DEFENCE_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(scheme.getV_DEFENCE_PHYSICALLY_CHALLENGED_UNSOLD_UNITS() - 1);
                    }
                }else{
                    scheme.setV_DEFENCE_UNSOLD_UNITS(scheme.getV_DEFENCE_UNSOLD_UNITS() - 1);
                }
                break;

            case "Dhobbies & Barbers":
                if(subCategory!=null){
                    if (subCategory.equalsIgnoreCase("Artists")) {
                        scheme.setV_DHOBBIES_ARTISTS_UNSOLD_UNITS(scheme.getV_DHOBBIES_ARTISTS_UNSOLD_UNITS() - 1);
                    }else if (subCategory.equalsIgnoreCase("Physically Challenged")) {
                        scheme.setV_DHOBBIES_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(scheme.getV_DHOBBIES_PHYSICALLY_CHALLENGED_UNSOLD_UNITS() - 1);
                    }
                }else{
                    scheme.setV_DHOBBIES_BARBERS_UNSOLD_UNITS(scheme.getV_DHOBBIES_BARBERS_UNSOLD_UNITS() - 1);
                }
                break;

            case "Working Journalist":
                if(subCategory!=null && subCategory.equalsIgnoreCase("Physically Challenged")) {
                    scheme.setV_JOURNALIST_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(scheme.getV_JOURNALIST_PHYSICALLY_CHALLENGED_UNSOLD_UNITS() - 1);
                }else{
                    scheme.setV_WORKING_JOURNALIST_UNSOLD_UNITS(scheme.getV_WORKING_JOURNALIST_UNSOLD_UNITS() - 1);
                }
                break;

            case "Language Crusaders":
                if(subCategory!=null && subCategory.equalsIgnoreCase("Physically Challenged")) {
                    scheme.setV_LANGUAGE_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(scheme.getV_LANGUAGE_PHYSICALLY_CHALLENGED_UNSOLD_UNITS() - 1);
                }else{
                    scheme.setV_LANGUAGE_CRUSADERS_UNSOLD_UNITS(scheme.getV_LANGUAGE_CRUSADERS_UNSOLD_UNITS() - 1);
                }
                break;

            case "TNHB Employees":
                if(subCategory!=null && subCategory.equalsIgnoreCase("Physically Challenged")) {
                    scheme.setV_TNHB_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(scheme.getV_TNHB_PHYSICALLY_CHALLENGED_UNSOLD_UNITS() - 1);
                }else{
                    scheme.setV_TNHB_EMPLOYEES_UNSOLD_UNITS(scheme.getV_TNHB_EMPLOYEES_UNSOLD_UNITS() - 1);
                }
                break;

            case "General Public":
                if(subCategory!=null){
                    if (subCategory.equalsIgnoreCase("Artists")) {
                        scheme.setV_GENERAL_PUBLIC_ARTISTS_UNSOLD_UNITS(scheme.getV_GENERAL_PUBLIC_ARTISTS_UNSOLD_UNITS() - 1);
                    }else if (subCategory.equalsIgnoreCase("Political Sufferers")) {
                        scheme.setV_GENERAL_PUBLIC_POLITICAL_SUFFERERS_UNSOLD_UNITS(scheme.getV_GENERAL_PUBLIC_POLITICAL_SUFFERERS_UNSOLD_UNITS() - 1);
                    }else if (subCategory.equalsIgnoreCase("Physically Challenged")) {
                        scheme.setV_GENERAL_PUBLIC_PHYSICALLY_CHALLENGED_UNSOLD_UNITS(scheme.getV_GENERAL_PUBLIC_PHYSICALLY_CHALLENGED_UNSOLD_UNITS() - 1);
                    }
                }else{
                    scheme.setV_GENERAL_PUBLIC_UNSOLD_UNITS(scheme.getV_GENERAL_PUBLIC_UNSOLD_UNITS() - 1);
                }
                break;
        }
    }

    // Unit Scheme Details for Unit ID CUSTOMER APPLICATION AUTOPOPULATE

    @Override
    public Map<String, Object> getUnitSchemeDetails(Long unitId) {
        List<Object[]> details = unitDataRepo.findUnitScheme(unitId);
        Map<String, Object> detail = new HashMap<>();
        if (!details.isEmpty() && details.get(0).length >= 15) {
            detail.put("Unit_Account_No", details.get(0)[0]);
            detail.put("Scheme_Type", details.get(0)[1]);
            detail.put("Mode_Of_Allotment", details.get(0)[2]);
            detail.put("City_Rural", details.get(0)[3]);
            detail.put("Circle", details.get(0)[4]);
            detail.put("Scheme", details.get(0)[5]);
            detail.put("Type", details.get(0)[6]);
            detail.put("Block_No", details.get(0)[7]);
            detail.put("Floor_No", details.get(0)[8]);
            detail.put("Flat_No", details.get(0)[9]);
            detail.put("Unit_No", details.get(0)[10]);
            detail.put("Plot_Area", details.get(0)[11]);
            detail.put("UDS_Area", details.get(0)[12]);
            detail.put("Plinth_Area", details.get(0)[13]);
            detail.put("Unit_Cost", details.get(0)[14]);
            detail.put("Reservation_Status", details.get(0)[15]);

            if (details.get(0)[14] != null) {
                String unitCostStr = (String) details.get(0)[14];
                double unitCost = Double.parseDouble(unitCostStr);
                double initialAmount = 0.10 * unitCost; // 10% of Unit_Cost
                Long roundedInitialAmount = Math.round(initialAmount);
                detail.put("Initial_Amount", roundedInitialAmount);
            }
        }
        return detail;
    }


    @Override
    public CAllottee CAllotteeSave(CAllottee cAllottee) {
        if (cAllottee.getALLOTMENT_ORDER()!= null) {
            String base64FileData = cAllottee.getALLOTMENT_ORDER();
            byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
            String generatedFileName = cAllottee.getAllotment_order_filename();
            String uniqueFileName = generatedFileName;
            File dest = new File(uploadDir, generatedFileName);

            try {
                Files.write(dest.toPath(), decodedFileData);
                cAllottee.setAllotment_order_filename (generatedFileName);
                cAllottee.setAllotment_order_filepath(dest.getAbsolutePath());
            } catch (IOException e) {

            }
        }

        return cAllotteeRepos.save(cAllottee);
    }
    @Override
    public List<CAllottee> getAllCAllottee() {
        return cAllotteeRepos.findAll();
    }



    @Override
    public CAllottee getCAllottee(Long id) {
        return cAllotteeRepos.findByUnitNId(id);

    }

    @Transactional
    @Override
    public CAllottee uploadCAllotteeData(CAllottee uploadfile) {
        Long id = uploadfile.getN_ID();
        Optional<CAllottee> existingCAllotteeOptional = cAllotteeRepos.findById(id);

        if (existingCAllotteeOptional.isPresent()) {
            CAllottee existingCAllottee = existingCAllotteeOptional.get();
            Field[] fields = CAllottee.class.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);

                try {
                    Object updatedValue = field.get(uploadfile);

                    if (updatedValue != null) {
                        String base64FileData = null;

                        if (field.getName().equals("LCS_Agreement")) {
                            base64FileData = uploadfile.getLCS_Agreement();
                        } else if (field.getName().equals("FIELD_MEASUREMENT_BOOK")) {
                            base64FileData = uploadfile.getFIELD_MEASUREMENT_BOOK();
                        } else if (field.getName().equals("HANDING_OVER_REPORT")) {
                            base64FileData = uploadfile.getHANDING_OVER_REPORT();
                        } else if (field.getName().equals("DRAFT_SALE_DEED")) {
                            base64FileData = uploadfile.getDRAFT_SALE_DEED();
                        } else if (field.getName().equals("A_B_LOAN")) {
                            base64FileData = uploadfile.getA_B_LOAN();
                        } else if (field.getName().equals("IN_PRINCIPAL_LETTER_FOR_LOAN")) {
                            base64FileData = uploadfile.getIN_PRINCIPAL_LETTER_FOR_LOAN();
                        } else if (field.getName().equals("NOC")) {
                            base64FileData = uploadfile.getNOC();
                        } else if (field.getName().equals("SALE_DEED_REQUISITION_LETTER")) {
                            base64FileData = uploadfile.getSALE_DEED_REQUISITION_LETTER();
                        }

                        if (base64FileData != null) {
                            byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
                            String generatedFileName = UUID.randomUUID().toString() + ".pdf";
                            File dest = new File(uploadDir, generatedFileName);

                            try {
                                Files.write(dest.toPath(), decodedFileData);

                                if (field.getName().equals("LCS_Agreement")) {
                                    existingCAllottee.setLcs_agreement_filename(generatedFileName);
                                    existingCAllottee.setLcs_agreement_filepath(dest.getAbsolutePath());
                                } else if (field.getName().equals("FIELD_MEASUREMENT_BOOK")) {
                                    existingCAllottee.setField_measurement_book_filename(generatedFileName);
                                    existingCAllottee.setField_measurement_book_filepath(dest.getAbsolutePath());
                                } else if (field.getName().equals("HANDING_OVER_REPORT")) {
                                    existingCAllottee.setHanding_over_report_filename(generatedFileName);
                                    existingCAllottee.setHanding_over_report_filepath(dest.getAbsolutePath());
                                } else if (field.getName().equals("DRAFT_SALE_DEED")) {
                                    existingCAllottee.setDraft_sale_deed_filename(generatedFileName);
                                    existingCAllottee.setDraft_sale_deed_filepath(dest.getAbsolutePath());
                                } else if (field.getName().equals("A_B_LOAN")) {
                                    existingCAllottee.setA_b_loan_filename(generatedFileName);
                                    existingCAllottee.setA_b_loan_filepath(dest.getAbsolutePath());
                                } else if (field.getName().equals("IN_PRINCIPAL_LETTER_FOR_LOAN")) {
                                    existingCAllottee.setInPrincipalLetterForLoanFilename(generatedFileName);
                                    existingCAllottee.setInPrincipalLetterForLoanFilepath(dest.getAbsolutePath());
                                } else if (field.getName().equals("NOC")) {
                                    existingCAllottee.setNocFilename(generatedFileName);
                                    existingCAllottee.setNocFilepath(dest.getAbsolutePath());
                                } else if (field.getName().equals("SALE_DEED_REQUISITION_LETTER")) {
                                    existingCAllottee.setSaleDeedRequisitionLetterFilename(generatedFileName);
                                    existingCAllottee.setSaleDeedRequisitionLetterFilepath(dest.getAbsolutePath());
                                }

                            } catch (IOException e) {
                                // Handle the exception here
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            cAllotteeRepos.save(existingCAllottee);
            return existingCAllottee;
         } else {
            return null;
        }
    }

    // ALLOTTEE DASHBOARD - CALLOTTEE

    // Getting Allottee Details And Scheme Details

    @Override
    public Map<String, Object> getAllotteeDetail(Long id) {
        List<Object> data = cAllotteeRepos.getCAllotteeDetail(id);
        Map<String, Object> map = new HashMap<>();
        if (data != null && !data.isEmpty()) {
            // Retrieve the first element of the outer list
            Object[] detail = (Object[]) data.get(0);

            map.put("Applicant_Name", detail[0]);
            map.put("Contact_No", detail[1]);
            map.put("Email_Id", detail[2]);
            map.put("Joint_Applicant_Name", detail[3]);
            map.put("Aadhaar_No", detail[4]);
            map.put("Pan_No", detail[5]);
            map.put("Address", detail[6]);
            map.put("Unit_Account_No", detail[7]);
            map.put("Unit_Type", detail[8]);
            map.put("Mode_Of_Allotment", detail[9]);
            map.put("Division", detail[10]);
            map.put("City_Rural", detail[11]);
            map.put("Circle", detail[12]);
            map.put("Scheme", detail[13]);
            map.put("Type", detail[14]);
            map.put("Block_No", detail[15]);
            map.put("Floor_No", detail[16]);
            map.put("Flat_No", detail[17]);
            map.put("Unit_No", detail[18]);
            map.put("Plot_UDS", detail[19]);
            map.put("Plinth_Area", detail[20]);
            map.put("Unit_Cost", detail[21]);
            map.put("Reservation_Category", detail[22]);
            map.put("n_id", detail[23]);

        }
        return map;
    }

     // Documents for Allottee Dashboard

    @Override
    public Map<String, Object> getDocumentForAllottee(Long id) {
        List<Object> data = cAllotteeRepos.getCAllotteeDocuments(id);
        Map<String, Object> map = new HashMap<>();
        if (data != null && !data.isEmpty()) {
            // Retrieve the first element of the outer list
            Object[] detail = (Object[]) data.get(0);

            map.put("Application", detail[0]);
            map.put("Allotment_Order", detail[1]);
            map.put("LCS_Agreement", detail[2]);
            map.put("A_B_Loan", detail[3]);
            map.put("Field_Measurement_Book", detail[4]);
            map.put("Handing_Over_Report", detail[5]);
            map.put("Draft_Sale_Deed", detail[6]);

        }
        return map;
    }

//    @Transactional
//    @Override
//    public CAllottee uploadCAllotteeData(CAllottee uploadfile) {
//        Long id = uploadfile.getN_ID();
//        Optional<CAllottee> existingCAllotteeOptional = cAllotteeRepos.findById(id);
//
//        if (existingCAllotteeOptional.isPresent()) {
//            CAllottee existingCAllottee = existingCAllotteeOptional.get();
//            Field[] fields = CAllottee.class.getDeclaredFields();
//
//            for (Field field : fields) {
//                field.setAccessible(true);
//
//                try {
//                    Object updatedValue = field.get(uploadfile);
//
//                    if (updatedValue != null) {
//                        String base64FileData = null;
//
//                        if (field.getName().equals("LCS_Agreement")) {
//                            base64FileData = uploadfile.getLCS_Agreement();
//                        } else if (field.getName().equals("FIELD_MEASUREMENT_BOOK")) {
//                            base64FileData = uploadfile.getFIELD_MEASUREMENT_BOOK();
//                        } else if (field.getName().equals("HANDING_OVER_REPORT")) {
//                            base64FileData = uploadfile.getHANDING_OVER_REPORT();
//                        } else if (field.getName().equals("DRAFT_SALE_DEED")) {
//                            base64FileData = uploadfile.getDRAFT_SALE_DEED();
//                        } else if (field.getName().equals("A_B_LOAN")) {
//                            base64FileData = uploadfile.getA_B_LOAN();
//                        } else if (field.getName().equals("IN_PRINCIPAL_LETTER_FOR_LOAN")) {
//                            base64FileData = uploadfile.getIN_PRINCIPAL_LETTER_FOR_LOAN();
//                        } else if (field.getName().equals("NOC")) {
//                            base64FileData = uploadfile.getNOC();
//                        } else if (field.getName().equals("SALE_DEED_REQUISITION_LETTER")) {
//                            base64FileData = uploadfile.getSALE_DEED_REQUISITION_LETTER();
//                        }
//
//                        if (base64FileData != null) {
//                            byte[] decodedFileData = Base64.getDecoder().decode(base64FileData);
//                            String generatedFileName = UUID.randomUUID().toString() + ".pdf";
//                            String bucketUrl = "http://tnhb-property-docs.s3-website.ap-south-1.amazonaws.com/";
//                            String filepath = bucketUrl + generatedFileName;
//                            byte[] fileBytes = decodedFileData;
//                            AwsBasicCredentials credentials = AwsBasicCredentials.create("AKIAR4WRUXSBYP5V7J42", "b8YXyxvNaQpNVr63UxCm51KckgMV8ZZ1WRLFwYl/");
//
//                            S3Client s3Client = S3Client.builder()
//                                    .region(Region.AP_SOUTH_1)
//                                    .credentialsProvider(() -> credentials)
//                                    .build();
//
//                            PutObjectRequest request = PutObjectRequest.builder()
//                                    .bucket("tnhb-property-docs")
//                                    .key(generatedFileName)
//                                    .contentType("application/pdf")
//                                    .build();
//
//                            PutObjectResponse response = s3Client.putObject(request, RequestBody.fromBytes(fileBytes));
//
//                            if (field.getName().equals("LCS_Agreement")) {
//                                existingCAllottee.setLcs_agreement_filename(generatedFileName);
//                                existingCAllottee.setLcs_agreement_filepath(filepath);
//                            } else if (field.getName().equals("FIELD_MEASUREMENT_BOOK")) {
//                                existingCAllottee.setField_measurement_book_filename(generatedFileName);
//                                existingCAllottee.setField_measurement_book_filepath(filepath);
//                            } else if (field.getName().equals("HANDING_OVER_REPORT")) {
//                                existingCAllottee.setHanding_over_report_filename(generatedFileName);
//                                existingCAllottee.setHanding_over_report_filepath(filepath);
//                            } else if (field.getName().equals("DRAFT_SALE_DEED")) {
//                                existingCAllottee.setDraft_sale_deed_filename(generatedFileName);
//                                existingCAllottee.setDraft_sale_deed_filepath(filepath);
//                            } else if (field.getName().equals("A_B_LOAN")) {
//                                existingCAllottee.setA_b_loan_filename(generatedFileName);
//                                existingCAllottee.setA_b_loan_filepath(filepath);
//                            } else if (field.getName().equals("IN_PRINCIPAL_LETTER_FOR_LOAN")) {
//                                existingCAllottee.setInPrincipalLetterForLoanFilename(generatedFileName);
//                                existingCAllottee.setInPrincipalLetterForLoanFilepath(filepath);
//                            } else if (field.getName().equals("NOC")) {
//                                existingCAllottee.setNocFilename(generatedFileName);
//                                existingCAllottee.setNocFilepath(filepath);
//                            } else if (field.getName().equals("SALE_DEED_REQUISITION_LETTER")) {
//                                existingCAllottee.setSaleDeedRequisitionLetterFilename(generatedFileName);
//                                existingCAllottee.setSaleDeedRequisitionLetterFilepath(filepath);
//                            }
//                        }
//                    }
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            cAllotteeRepos.save(existingCAllottee);
//            return existingCAllottee;
//        } else {
//            return null;
//        }
//    }


}











