
package com.bocxy.Property.Entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "Customer")
public class CustomerApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "N_ID")
    private Long N_ID;

    @Column(name = "CUSTOMER_ID")
    private Long customerid;

    @Column(name = "APPLICATION_NO")
    private String applicationNo;

    @Column(name = "DATE",length = 45)
    private String date;

    @Column(name = "UNIT_ACCOUNT_NO")
    private String unitAccountNo;

    @Column(name = "UNIT_TYPE",length = 45)
    private String unitType;

    @Column(name = "MODE_OF_ALLOTMENT",length = 45)
    private String modeOfAllotment;

    @Column(name = "CITY_RURAL",length = 45)
    private String cityRural;

    @Column(name = "CIRCLE",length = 45)
    private String circle;

    @Column(name = "SCHEME")
    private String scheme;

    @Column(name = "TYPE",length = 45)
    private String type;

    @Column(name = "BLOCK_NO",length = 45)
    private String blockNo;

    @Column(name = "FLOOR_NO",length = 45)
    private String floorNo;

    @Column(name = "FLAT_NO",length = 45)
    private String flatNo;

    @Column(name = "UNIT_NO")
    private String unitNo;

    @Column(name = "PLOT_UDS_AREA",length = 45)
    private String plotUdsArea;

    @Column(name = "PLINTH_AREA",length = 45)
    private String plinthArea;

    @Column(name = "RESERVATION_CATEGORY",length = 45)
    private String reservationCategory;

    @Column(name = "PRIORITY_BASIS",length = 45)
    private String priorityBasis;

    @Column(name = "COST_OF_UNIT")
    private double costOfUnit;

    @Column(name = "DIVISION")
    private String division;

    @Column(name = "RESERVATION_OR_NON_RESERVATION",length = 45)
    private String reservationOrNonReservation;

    @Column(name = "STATUS",length = 45)
    private String status;

    @Column(name = "APPLICANT_NAME")
    private String applicantName;

    @Column(name = "DATE_OF_BIRTH",length = 45)
    private String dateOfBirth;

    @Column(name = "AGE",length = 45)
    private int age;

    @Column(name = "APPLICANT_SPOUSE_NAME")
    private String applicantSpouseName;

    @Column(name = "APPLICANT_FATHERS_NAME")
    private String applicantFathersName;

    @Column(name = "JOINT_APPLICANT_NAME")
    private String jointApplicantName;

    @Column(name = "JOINT_APPLICANT_DOB",length = 45)
    private String jointApplicantDOB;

    @Column(name = "JOINT_APPLICANT_AGE",length = 45)
    private int jointApplicantAge;

    @Column(name = "JOINT_APPLICANT_SPOUSE_NAME")
    private String jointApplicantSpouseName;

    @Column(name = "JOINT_APPLICANT_FATHERNAME")
    private String jointApplicantFathername;

    @Column(name = "MOBILE_NUMBER",length = 45)
    private String mobileNumber;

    @Column(name = "EMAIL_ID")
    private String emailId;

    @Column(name = "AADHAAR_NUMBER",length = 45)
    private String aadhaarNumber;

    @Column(name = "PAN_NUMBER")
    private String panNumber;

    @Column(name = "CORRESPONDENCE_ADDRESS")
    private String correspondenceAddress;

    @Column(name = "PERMANENT_ADDRESS")
    private String permanentAddress;

    @Column(name = "APPLICANT_MONTHLY_INCOME")
    private double applicantMonthlyIncome;

    @Column(name = "SPOUSE_MONTHLY_INCOME")
    private double spouseMonthlyIncome;

    @Column(name = "TOTAL_MONTHLY_INCOME")
    private double totalMonthlyIncome;

    @Column(name = "BANK_NAME")
    private String bankName;

    @Column(name = "ACCOUNT_NUMBER")
    private Long accountNumber;

    @Column(name = "IFSC_CODE")
    private String ifscCode;

    @Column(name = "ACCOUNT_HOLDER_NAME")
    private String accountHolderName;

    @Column(name = "APPLICATION_FEE",length = 45)
    private double applicationFee;

    @Column(name = "REGISTRATION_FEE",length = 45)
    private double registrationFee;

    @Column(name = "Amount_Paid")
    private double amountPaid;

    @Column(name = "UNIT_N_ID",length = 45)
    private Long unitNId;

    @Column(name = "SCHEME_N_ID",length = 45)
    private Long schemeNId;

    @Column(name = "CATEGORY_CODE",length = 45)             // CC
    private Long categoryCode;

    @Column(name = "SUB_CATEGORY_CODE",length = 45)         // SC
    private Long subCategoryCode;

    @Column(name = "NATIVEOFTAMILNADU_STATUS",length = 45)
    private String nativeOfTamilnadu_status;

    @Column(name = "BIRTHCERTIFICATE_STATUS",length = 45)
    private String birthCertificate_status;

    @Column(name = "AADHAARPROOF_STATUS",length = 45)
    private String aadhaarProof_status;

    @Column(name = "PANPROOF_STATUS",length = 45)
    private String panProof_status;

    @Column(name = "INCOMECERTIFICATE_STATUS",length = 45)
    private String incomeCertificate_status;

    @Column(name = "RESERVATIONCATEGORYPROOF_STATUS",length = 45)
    private String reservationCategoryProof_status;

    @Column(name = "RESERVATIONSUBCATEGORYPROOF_STATUS",length = 45)
    private String reservationSubCategoryProof_status;

    @Column(name = "SIGNATURE_STATUS",length = 45)
    private String signature_status;

    @Column(name = "JOINTAPPLSIGN_STATUS",length = 45)
    private String jointApplSign_status;

    @Column(name = "PHOTO_STATUS",length = 45)
    private String photo_status;

    @Transient
    private String nativeOfTamilnadu;

    @Transient
    private String birthCertificate;

    @Transient
    private String aadhaarProof;

    @Transient
    private String panProof;

    @Transient
    private String incomeCertificate;

    @Transient
    private String reservationCategoryProof;

    @Transient
    private String reservationSubCategoryProof;

    @Transient
    private String signature;

    @Transient
    private String jointApplSign;

    @Transient
    private String photo;

    @Transient
    private String APPLICATION_UPLOAD;

    @Column(name = "NATIVE_OF_TAMILNADU_FILENAME",length = 70)
    private String nativeOfTamilnadu_filename;

    private String nativeOfTamilnadu_filepath;

    @Column(name = "BIRTH_CERTIFICATE_FILENAME",length = 70)
    private String birthCertificate_filename;

    @Column(name = "BIRTH_CERTIFICATE_FILEPATH")
    private String birthCertificate_filepath;

    @Column(name = "AADHAAR_PROOF_FILENAME",length = 70)
    private String aadhaarProof_filename;

    @Column(name = "AADHAAR_PROOF_FILEPATH")
    private String aadhaarProof_filepath;

    @Column(name = "PAN_PROOF_FILENAME",length = 70)
    private String panProof_filename;

    @Column(name = "PAN_PROOF_FILEPATH")
    private String panProof_filepath;

    @Column(name = "INCOME_CERTIFICATE_FILENAME",length = 70)
    private String incomeCertificate_filename;

    @Column(name = "INCOME_CERTIFICATE_FILEPATH")
    private String incomeCertificate_filepath;

    @Column(name = "RESERVATION_CATEGORY_PROOF_FILENAME",length = 70)
    private String reservationCategoryProof_filename;

    @Column(name = "RESERVATION_CATEGORY_PROOF_FILEPATH")
    private String reservationCategoryProof_filepath;

    @Column(name = "RESERVATION_SUB_CATEGORY_PROOF_FILENAME",length = 70)
    private String reservationSubCategoryProof_filename;

    @Column(name = "RESERVATION_SUB_CATEGORY_PROOF_FILEPATH")
    private String reservationSubCategoryProof_filepath;

    @Column(name = "SIGNATURE_FILENAME",length = 70)
    private String signature_filename;

    @Column(name = "SIGNATURE_FILEPATH")
    private String signature_filepath;

    @Column(name = "JOINT_APPL_SIGN_FILENAME",length = 70)
    private String jointApplSignFilename;

    @Column(name = "JOINT_APPL_SIGN_FILEPATH")
    private String jointApplSignFilePath;

    @Column(name = "PHOTO_FILENAME",length = 70)
    private String photo_filename;

    @Column(name = "PHOTO_FILEPATH")
    private String photo_filepath;

    @Column(name = "APPLICATION_UPLOAD_FILENAME",length = 70)
    private String application_upload_filename;

    @Column(name = "APPLICATION_UPLOAD_FILEPATH")
    private String application_upload_filepath;
}