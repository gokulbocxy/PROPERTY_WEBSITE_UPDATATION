package com.bocxy.Property.Controller;

import com.bocxy.Property.Entity.*;
import com.bocxy.Property.Model.*;
import com.bocxy.Property.Repository.AllotteeRepo;
import com.bocxy.Property.Repository.SchemeDataRepo;
import com.bocxy.Property.Repository.UnitDataRepo;
import com.bocxy.Property.Repository.WebsiteDataRepo;
import com.bocxy.Property.Service.PropertyService;
import com.bocxy.Property.common.ResponseDo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4300","http://localhost:62872","http://propertyadmin.tnhb-noc.com","http://website.tnhb-noc.com"}, allowedHeaders = "*")
public class PropertyController {

    private final PropertyService propertyService;
    private final AllotteeRepo allotteeRepo;
    private final SchemeDataRepo schemeDataRepo;
    private final UnitDataRepo UnitDataRepo;
    private final ResponseDo responseDo;
    private final WebsiteDataRepo websiteDataRepo;



    @Autowired
    public PropertyController(PropertyService propertyService,  AllotteeRepo allotteeRepo,
                              SchemeDataRepo schemeDataRepo, UnitDataRepo unitDataRepo,
                              ResponseDo responseDo,WebsiteDataRepo websiteDataRepo) {
        this.propertyService = propertyService;
        this.allotteeRepo = allotteeRepo;
        this.schemeDataRepo = schemeDataRepo;
        this.UnitDataRepo = unitDataRepo;
        this.responseDo = responseDo;
        this.websiteDataRepo = websiteDataRepo;
    }

    //Schemes

    //All Schemes for List

    @PostMapping("/getAllSchemes")
    public ResponseDo getAllScheme(@RequestBody JSONObject json,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        Long id = json.getAsNumber("id").longValue();
        try {
            List<SchemeData> allScheme = propertyService.getAllSchemeData();

            if (allScheme != null) {
                return responseDo.setSuccessResponse(allScheme);
            } else {
                return responseDo.setSuccessResponse("No Data Found", null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return responseDo.setFailureResponse("An error occurred"); // Return a failure response in case of exception
        }
    }

    //Create Single Scheme
    @PostMapping("/saveSchemeData")
    public ResponseDo saveSchemeData(@RequestBody List<SchemeData> schemeData) {
        try {
            List<SchemeData> savedSchemeData = propertyService.saveSchemeData(schemeData);

            if (savedSchemeData != null) {
                return responseDo.setSuccessResponse(savedSchemeData);
            } else {
                return responseDo.setFailureResponse("Failed to save SchemeData.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return responseDo.setFailureResponse("An error occurred while saving SchemeData.");
        }
    }

   //Edit Single Scheme
   @PostMapping("/editSchemeData")
   public ResponseDo editSchemeData(@RequestBody SchemeData updatedSchemeData) {
       try {
           SchemeData editedSchemeData = propertyService.editSchemeData(updatedSchemeData);

           if (editedSchemeData != null) {
               return responseDo.setSuccessResponse(editedSchemeData);
           } else {
               return responseDo.setFailureResponse("SchemeData with the given ID not found.");
           }
       } catch (Exception e) {
           e.printStackTrace();
           return responseDo.setFailureResponse("An error occurred while editing SchemeData.");
       }
   }

    //Get Single Scheme
    @PostMapping("/getSchemeData")
    public ResponseDo getSchemeData(@RequestBody JSONObject json,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        Long id = json.getAsNumber("id").longValue();

        try {
            SchemeData schemeData = propertyService.getSchemeData(id);

            if (schemeData != null) {
                return responseDo.setSuccessResponse(schemeData);
            } else {
                return responseDo.setFailureResponse("Scheme Details not found for the provided ID.");
            }

        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return responseDo.setFailureResponse("Invalid date format. Please provide the date in the format 'dd-MM-yyyy'");
        } catch (Exception e) {
            e.printStackTrace();
            return responseDo.setFailureResponse("An error occurred"); // Return a failure response in case of exception
        }
    }

    // Delete Single Scheme
    @DeleteMapping("/deleteScheme/{id}")
    public ResponseDo deleteScheme(@PathVariable("id") Long id){
        try{
            propertyService.deleteSchemeData(id);
            return responseDo.setSuccessResponse("Successfully deleted SchemeData :"+id);
        }catch (Exception e) {
            e.printStackTrace();
            return responseDo.setFailureResponse("Failed to delete SchemeData");
        }
    }

    //AutoCalculate Scheme Code
    @PostMapping("/getSchemeCode/{division}")
    public ResponseEntity<ResponseDo> getSchemeCode(@PathVariable("division") String division){
        try{
            String code = propertyService.getDivisionCode(division);
            int count = propertyService.getDivSchemeCount(division);
            String newCount = String.format("%02d",count);
            return ResponseEntity.ok(responseDo.setSuccessResponse("Successfully generated Scheme Code",code+newCount));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(responseDo.setFailureResponse("Failed to generate Scheme code."));
        }
    }

    //Get Division List
    @PostMapping("/getAllDivision")
    public ResponseEntity<ResponseDo> getAllDivision(){
        try{
            List<String> divisions = propertyService.getAllDivision();
            return ResponseEntity.ok(responseDo.setSuccessResponse(divisions));
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(responseDo.setFailureResponse("Failed to retrieve division list."));
        }
    }

    //Allottee

    @PostMapping("/getAllotteesBySchemeId")
    public ResponseEntity<ResponseDo> getAllotteesBySchemeId(@RequestBody JSONObject json) {
        Long schemeId = json.getAsNumber("nSchemeId").longValue();

        try {
            List<Allottee> allottees = propertyService.getAllotteesBySchemeId(schemeId);

            if (allottees != null && !allottees.isEmpty()) {
                return ResponseEntity.ok(responseDo.setSuccessResponse(allottees));
            } else {
                return ResponseEntity.ok(responseDo.setSuccessResponse("No Allottees Found for Scheme ID: " + schemeId, null));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(responseDo.setFailureResponse("An error occurred"));
        }
    }

    //Create/Edit Allottee
    @PostMapping("/saveAllottees")
    public ResponseEntity<ResponseDo> saveAllottees(@RequestBody List<Allottee> allottees) {
        try {
            List<Allottee> savedAllottees = propertyService.saveAllottees(allottees);

            if (!savedAllottees.isEmpty()) {
                ResponseDo response = new ResponseDo();
                response.setSuccessResponse(true);
                response.setData(savedAllottees);
                return ResponseEntity.ok(response);
            } else {
                ResponseDo response = new ResponseDo();
                response.setSuccessResponse(false);
                response.setFailureResponse("Failed to save Allottees.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDo response = new ResponseDo();
            response.setSuccessResponse(false);
            response.setFailureResponse("An error occurred while saving Allottees.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //Delete Single Allottee
    @DeleteMapping("/deleteAllottee/{id}")
    public ResponseDo deleteAllottee(@PathVariable("id") Long id){
        try{
            propertyService.deleteAllotteeData(id);
            return responseDo.setSuccessResponse("Successfully deleted Allottee :"+id);
        }catch (Exception e) {
            e.printStackTrace();
            return responseDo.setFailureResponse("Failed to delete Allottee Data");
        }
    }

    //UnitData

    //Create/Edit UnitData
    @PostMapping("/saveUnitData")
    public ResponseDo saveunitdata(@RequestBody List<UnitData> unitData) {
        try {
            List<UnitData> savedUnitData = propertyService.saveUnitData(unitData);

            if (!savedUnitData.isEmpty()) {
                return responseDo.setSuccessResponse(savedUnitData);
            } else {
                return responseDo.setFailureResponse("Failed to save Allottees.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return responseDo.setFailureResponse("An error occurred while saving Allottees.");
        }
    }

    //Get UnitData by Scheme id
    @PostMapping("/getUnitOfOneScheme")
    public ResponseDo getUnitOfScheme(@RequestBody JSONObject json,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        Long nSchemeId = json.getAsNumber("id").longValue();
        try {
            List<UnitData> unitData = propertyService.getUnits(nSchemeId);
            return responseDo.setSuccessResponse(unitData);
        } catch (Exception e) {
            e.printStackTrace();
            return responseDo.setFailureResponse("Failed to list Unit Data");
        }

    }

    //Delete Single UnitData
    @DeleteMapping("/deleteUnit/{id}")
    public ResponseDo deleteUnit(@PathVariable("id") Long id){
        try{
            propertyService.deleteUnitData(id);
            return responseDo.setSuccessResponse("Successfully deleted Unit Data :"+id);
        }catch (Exception e) {
            e.printStackTrace();
            return responseDo.setFailureResponse("Failed to delete Unit Data");
        }
    }


    //Get Single UnitData
    @PostMapping("/getOneUnitData")
    public ResponseDo getOneUnitData(@RequestBody JSONObject json,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {
        Long nId = json.getAsNumber("nId").longValue();
        try {
            UnitData unitData = propertyService.getOneUnit(nId);
            return responseDo.setSuccessResponse(unitData);
        } catch (Exception e) {
            e.printStackTrace();
            return responseDo.setFailureResponse("Failed to list Unit Data");
        }
    }

    //Save Single Unit

    @PostMapping("/saveOneUnitData")
    public ResponseDo saveOneunitdata(@RequestBody UnitData unitData) {
        try {
            UnitData savedUnitData = propertyService.saveOneUnitData(unitData);

            if (savedUnitData!=null) {
                return responseDo.setSuccessResponse(savedUnitData);
            } else {
                return responseDo.setFailureResponse("Failed to save Unit Data.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return responseDo.setFailureResponse("An error occurred while saving Unit Data.");
        }
    }



    //SaleDeed Document

    @PostMapping("/saveSalesDeed")
    public ResponseEntity<ResponseDo> saveSalesDeed(@RequestBody List<SalesDeedData> salesdeeddatas) {
        try {
            List<SalesDeedData> savedSalesDeedDatas = propertyService.saveSalesDeed(salesdeeddatas);

            if (!savedSalesDeedDatas.isEmpty()) {
                ResponseDo response = new ResponseDo();
                response.setSuccessResponse(true);
                response.setData(savedSalesDeedDatas);
                return ResponseEntity.ok(response);
            } else {
                ResponseDo response = new ResponseDo();
                response.setSuccessResponse(false);
                response.setFailureResponse("Failed to save SalesDeed Data.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDo response = new ResponseDo();
            response.setSuccessResponse(false);
            response.setFailureResponse("An error occurred while saving SalesDeed.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

   //Get Sales Deed Documents by Scheme ID

    @PostMapping("/getAllSalesDeed")
    public ResponseDo getAllSalesDeed(@RequestBody JSONObject json,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        Long nSchemeId = json.getAsNumber("id").longValue();
        try {
            List<SalesDeedData> allSalesDeeds = propertyService.getAllSalesDeeds(nSchemeId);

            if (allSalesDeeds != null) {
                return responseDo.setSuccessResponse(allSalesDeeds);
            } else {
                return responseDo.setSuccessResponse("No Data Found", null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return responseDo.setFailureResponse("An error occurred"); // Return a failure response in case of exception
        }
    }

    //ALLOTTEE DASHBOARD API

    //Allotte OTP for Login
    @PostMapping("/send-otp")
    public ResponseEntity<Map<String, String>> sendOtpToAllottee(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        if (propertyService.emailExists(email)) {
            propertyService.sendOtpToAllottee(email);

            Map<String, String> response = new HashMap<>();
            response.put("message", "OTP sent successfully.");

            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Invalid email.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    //Allottee OTP verification
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String otp = requestBody.get("otp");
        List<Allottee> response = propertyService.verifyOtpAndGetAllottee(email, otp);

        if (!response.isEmpty()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP.");
        }
    }

    //Get Single Allottee by ID
    @PostMapping("/getAllotteebyid")
    public ResponseDo getAllotteebyid(@RequestBody JSONObject json,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        Long id = json.getAsNumber("id").longValue();
        try {
            List<Allottee> allotteeData = propertyService.getAllotteebyId(id);
            return responseDo.setSuccessResponse(allotteeData);
        } catch (Exception e) {
            e.printStackTrace();
            return responseDo.setFailureResponse("Failed to list Allottee Data");
        }

    }

    //Combined Allotte Details

    @PostMapping("/getDetailsById")
    public ResponseDo getDetailsById(@RequestBody JSONObject json) {
        Long id = json.getAsNumber("id").longValue();
        try {
            CombinedDataModel details = propertyService.getDetailsById(id);
            return responseDo.setSuccessResponse(details); // Assuming ResponseDo constructor accepts status and data

        } catch (Exception e) {
            e.printStackTrace();
            return responseDo.setFailureResponse("Failed to list Combined Data");
        }
    }

    //Get Single Allotee Sales Deed Document by ID
    @PostMapping("/getDocumentsbyid")
    public ResponseDo getDocumentsbyid(@RequestBody JSONObject json,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        Long id = json.getAsNumber("id").longValue();
        try {
            List<SalesDeedData> salesdeedData = propertyService.getSalesDeedDatabyId(id);
            return responseDo.setSuccessResponse(salesdeedData);
        } catch (Exception e) {
            e.printStackTrace();
            return responseDo.setFailureResponse("Failed to list SalesDeed Data");
        }

    }

    //Website Module

    //Create/Edit Single WebsiteScheme
    @PostMapping("/saveWebsiteData")
    public ResponseEntity<ResponseDo> saveWebsiteData(@RequestBody List<WebsiteData> websiteDataList) {
        try {
            List<WebsiteData> savedWebsiteDataList = propertyService.saveWebsiteData(websiteDataList);
            if(!savedWebsiteDataList.isEmpty()){
                ResponseDo response = new ResponseDo();
                response.setSuccessResponse(true);
                response.setData(savedWebsiteDataList);
                return ResponseEntity.ok(response);
            } else {
                ResponseDo response = new ResponseDo();
                response.setSuccessResponse(false);
                response.setFailureResponse("Failed to save WebsiteData.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDo response = new ResponseDo();
            response.setSuccessResponse(false);
            response.setFailureResponse("An error occurred while saving WebsiteData.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }

    }

    //Get data for website
    @PostMapping("/getParticularData")
    public ResponseEntity<ResponseDo> getWebsiteData(@RequestBody JSONObject json) {
        try {
            Long nschemeId = json.getAsNumber("nSchemeId").longValue();
            List<WebsiteData> websiteDataList = propertyService.getWebsiteData(nschemeId);

            if (!websiteDataList.isEmpty()) {
                ResponseDo response = new ResponseDo();
                response.setSuccessResponse(true);
                response.setData(websiteDataList);
                return ResponseEntity.ok(response);
            } else {
                ResponseDo response = new ResponseDo();
                response.setSuccessResponse(false);
                response.setFailureResponse("No WebsiteData found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDo response = new ResponseDo();
            response.setSuccessResponse(false);
            response.setFailureResponse("An error occurred while retrieving WebsiteData.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    //Delete Website Data
    @DeleteMapping("/deleteWebsite")
    public ResponseDo deleteWebsiteData(@RequestBody Map<String, Long> requestBody) {
        try {
            Long id = requestBody.get("id");
            propertyService.deleteWebsiteData(id);
            return responseDo.setSuccessResponse("Successfully deleted Website Data: " + id);
        } catch (Exception e) {
            e.printStackTrace();
            return responseDo.setFailureResponse("Failed to delete Website Data");
        }
    }


    //get all Website Data for USER WEBSITE scheme List screen
    @PostMapping("/getAllWebsiteData")
    public ResponseDo getAllWebsiteData(@RequestBody JSONObject json,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {
        Long id = json.getAsNumber("id").longValue();
        try {
            List<WebsiteModel> allwebsiteData = propertyService.getAllWebsiteData();

            if (allwebsiteData != null) {
                return responseDo.setSuccessResponse(allwebsiteData);
            } else {
                return responseDo.setSuccessResponse("No Data Found", null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return responseDo.setFailureResponse("An error occurred"); // Return a failure response in case of exception
        }
    }


    // get single websiteData
    @PostMapping("/getwebsiteData")
    public ResponseEntity<Map<String, Object>> getSchemeDataBySchemeId(@RequestBody Map<String, Long> request) {
        Long schemeId = request.get("schemeId");

        Map<String, Object> schemeData = propertyService.getSchemeDataBySchemeId(schemeId);
        if (schemeData == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(schemeData);
    }

    //Delete Website Data
    @DeleteMapping("/deleteWebsite/{id}")
    public ResponseDo deleteWebsiteData(@PathVariable("id") Long id){
        try{
            propertyService.deleteWebsiteData(id);
            return responseDo.setSuccessResponse("Successfully deleted Website Data :"+id);
        }catch (Exception e) {
            e.printStackTrace();
            return responseDo.setFailureResponse("Failed to delete Website Data");
        }
    }

    // Get Block for Booking
    @PostMapping("/getBlocksForWebsite")
    public ResponseEntity<List<Map<String, Object>>> getBlocksForWebsite(@RequestBody Map<String, Long> request) {
        Long schemeId = request.get("schemeId");

        List<Map<String, Object>> schemeDataList = propertyService.getBlocksForWebsite(schemeId);
        if (schemeDataList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(schemeDataList);
    }

    //Get Floor for Booking
    @PostMapping("/getFloorForWebsite")
    public ResponseEntity<List<Map<String, Object>>> getFloorForWebsite(@RequestBody Map<String, Long> request) {
        Long schemeId = request.get("schemeId");

        List<Map<String, Object>> schemeDataList = propertyService.getFloorForWebsite(schemeId);
        if (schemeDataList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(schemeDataList);
    }

    //Get Apartment for Booking
    @PostMapping("/getBlockFloorApartmentList")
    public ResponseEntity<List<Map<String, Object>>> getBlockFloorApartmentList(@RequestBody Map<String, Long> request) {
        Long schemeId = request.get("schemeId");

        List<Map<String, Object>> schemeDataList = propertyService.findUnitDetailsBySchemeId(schemeId);
        if (schemeDataList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(schemeDataList);
    }

    //Booking API
    @PostMapping("/bookNow")
    public ResponseEntity<String> bookNow(@RequestBody Map<String, Long> request) {
        Long unitId = request.get("unitId");

        String unitDataList = propertyService.findById(unitId);
        if (unitDataList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(unitDataList);
    }


    // enquiry
    @PostMapping("/send-email")
    public String sendEmail(@RequestBody Enquiry enquiry,
                            @RequestParam String vPocEmail,
                            @RequestParam String vEmail) {
        String result = propertyService.sendMailToCustomer(enquiry, vPocEmail, vEmail);
        return result;
    }


    //FINANCIAL

    // Create EMI calculation
    @PostMapping("/emirows")
    public ResponseEntity<String> createEmiRows(@RequestBody CalculationRequestModel request) {
        propertyService.calculateEmiRows(request);
        return ResponseEntity.ok("EMI rows created successfully.");
    }

    //Calculate Demand

    @PostMapping("/demandrows")
    public ResponseEntity<String> calculateDemandRows(@RequestBody DemandRequestModel request) {
               propertyService.calculateDemandRows();
                return ResponseEntity.ok("Demand rows created successfully.");
    }


    //Create/Update COLLECTION
    @PostMapping("/updatecollection")
    public ResponseEntity<String> updateCollectionTable(@RequestBody CollectionRequestModel request) {
        try {
            System.out.println(request);
            Financial_Calc updatedData = propertyService.updateCollectionData(request);
            return ResponseEntity.ok("Record updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found for the provided N_UNIT_ID and MONTH");
        }
    }

    //GET Demand FOR WHAT?????
    @PostMapping("/getdemand")
    public ResponseEntity<String> getdemand(@RequestBody GetDemandModel request) {
        try {
            System.out.println(request);
            Financial_Calc updatedData = propertyService.GetDemandModel(request);
            return ResponseEntity.ok("Record updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found for the provided N_UNIT_ID and MONTH");
        }
    }


    // Website Customer Booking Application

    //Get Scheme Wise List for Booking
    @PostMapping("/getAllWebsite")
    public ResponseDo getAllWebsite(@RequestBody JSONObject json,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        Long id = json.getAsNumber("id").longValue();
        try {
            List<Map<String, Object>> allWebsite = propertyService.getAllSchemeForWebsite();

            if (allWebsite != null) {
                return responseDo.setSuccessResponse(allWebsite);
            } else {
                return responseDo.setSuccessResponse("No Data Found", null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return responseDo.setFailureResponse("An error occurred");
        }
    }

    //Save Application
   @PostMapping("/saveCustomerApplication")
    public ResponseDo saveCustomerApplication(@RequestBody CustomerApplication customerApplication) {
        try {
            CustomerApplication savedCustomerApplication = propertyService.saveCustomerApplication(customerApplication);

            if (savedCustomerApplication != null) {
                return responseDo.setSuccessResponse(savedCustomerApplication);
            } else {
                return responseDo.setFailureResponse("Failed to save CustomerApplication.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return responseDo.setFailureResponse("An error occurred while saving CustomerApplication.");
        }
    }


    //Edit Customer For Status of Documents by ADMIN
    @PostMapping("/editCustomerApplication/{id}")
    public ResponseEntity<ResponseDo> editCustomerApplication(@PathVariable Long id, @RequestBody CustomerApplication updatedCustomerApplication) {
        try {
            CustomerApplication customerApplication = propertyService.updateCustomerApplicationById(id, updatedCustomerApplication);

            if (customerApplication != null) {
                return ResponseEntity.ok(responseDo.setSuccessResponse(customerApplication));
            } else {
                return ResponseEntity.ok(responseDo.setFailureResponse("CustomerApplication not found for the provided N_ID."));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(responseDo.setFailureResponse("An error occurred while updating the CustomerApplication."));
        }
    }

    //Get all Application for Admin
    @PostMapping("/getAllCustomerApp")
    public ResponseEntity<ResponseDo> getAllCustomerApplications() {
        try {
            List<CustomerApplication> allCustomerApplications = propertyService.getAllCustomerApplications();

            if (!allCustomerApplications.isEmpty()) {
                return ResponseEntity.ok(responseDo.setSuccessResponse(allCustomerApplications));
            } else {
                return ResponseEntity.ok(responseDo.setSuccessResponse("No Data Found", null));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(responseDo.setFailureResponse("An error occurred while fetching CustomerApplications."));
        }
    }

    //Get Single Application for Customer
    @PostMapping("/getByIdCustomerApp")
    public ResponseEntity<ResponseDo> getCustomerApplicationById(@RequestBody JSONObject json) {
        Long id = json.getAsNumber("id").longValue();
        try {
            CustomerApplication customerApplication = propertyService.getCustomerApplication(id);

            if (customerApplication != null) {
                return ResponseEntity.ok(responseDo.setSuccessResponse(customerApplication));
            } else {
                return ResponseEntity.ok(responseDo.setFailureResponse("CustomerApplication not found for the provided ID."));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(responseDo.setFailureResponse("An error occurred while fetching CustomerApplication."));
        }
    }


    //Get Applications for Single Customer
    @PostMapping("/getCustomerApp")
    public ResponseDo getCustomerApplications(@RequestBody JSONObject json,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        Long id = json.getAsNumber("id").longValue();
        try {
            List<CustomerApplication> CustomerApplications = propertyService.getCustomerApplications(id);
            return responseDo.setSuccessResponse(CustomerApplications);
        } catch (Exception e) {
            e.printStackTrace();
            return responseDo.setFailureResponse("Failed to Customer Application");
        }

    }

    //Get all Schemes with Customer Application
    @PostMapping("/getAllSchemeWithApplication")
    public ResponseEntity<ResponseDo> getAllSchemeWithAppl() {
        try {
            List<Map<String, Object>> allSchemeWithApplications = propertyService.getAllSchemesWithAppl();

            if (!allSchemeWithApplications.isEmpty()) {
                return ResponseEntity.ok(responseDo.setSuccessResponse(allSchemeWithApplications));
            } else {
                return ResponseEntity.ok(responseDo.setSuccessResponse("No Data Found", null));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(responseDo.setFailureResponse("An error occurred while fetching Scheme Info."));
        }
    }
    //Get all Applications Of One Scheme
    @PostMapping("/getAllApplicationsOfOneScheme")
    public ResponseEntity<ResponseDo> getAllApplOfOneScheme(@RequestBody JSONObject json) {
        try {
            String scheme = json.getAsString("scheme");
            List<CustomerApplication> allApplicationsOfOnescheme = propertyService.getAllApplOfOneScheme(scheme);

            if (!allApplicationsOfOnescheme.isEmpty()) {
                return ResponseEntity.ok(responseDo.setSuccessResponse(allApplicationsOfOnescheme));
            } else {
                return ResponseEntity.ok(responseDo.setSuccessResponse("No Data Found", null));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(responseDo.setFailureResponse("An error occurred while fetching Application List."));
        }
    }

    //Get AllPending Applications


    @PostMapping("/getPendingCustomerApplications")
    public ResponseEntity<ResponseDo> getpendingcustomerapllication() {

        try {
            List <CustomerApplication> customerApplication = propertyService.getPendingCustomerApplications();

            if (customerApplication != null) {
                return ResponseEntity.ok(responseDo.setSuccessResponse(customerApplication));
            } else {
                return ResponseEntity.ok(responseDo.setFailureResponse("CustomerApplication not found for the Pending."));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(responseDo.setFailureResponse("An error occurred while fetching CustomerApplication Pending."));
        }
    }

    //Get Category wise count List

    @PostMapping("/getCategoryWiseReservation")
    public ResponseEntity<ResponseDo> getCategoryWiseReservation(@RequestBody JSONObject json) {
        Long n_id = json.getAsNumber("id").longValue();
        try {
            Optional<SchemeData> schemeData = propertyService.getbyidSchemedata(n_id);

            if (schemeData != null) {
                return ResponseEntity.ok(responseDo.setSuccessResponse(schemeData));
            } else {
                return ResponseEntity.ok(responseDo.setFailureResponse("CustomerApplication not found for the provided ID."));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(responseDo.setFailureResponse("An error occurred while fetching CustomerApplication."));
        }
    }

    //Get Block/Floor Details for Booking
    @PostMapping("/getBlockFloorUnitDetails")
    public ResponseEntity<ResponseDo> getBlockUnitFloorUnit(@RequestBody JSONObject json) {
        try {
            Long scheme = json.getAsNumber("schemeId").longValue();
            List<Map<String, Object>> allDetails = propertyService.getPropertyData(scheme);

            if (!allDetails.isEmpty()) {
                return ResponseEntity.ok(responseDo.setSuccessResponse(allDetails));
            } else {
                return ResponseEntity.ok(responseDo.setSuccessResponse("No Data Found", null));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(responseDo.setFailureResponse("An error occurred while fetching Details."));
        }
    }

    //Get Block/Floor Booking for All Schemes
    @PostMapping("/getBlockFloorUnitAllSchemes")
    public ResponseEntity<ResponseDo> getUnitBlockForAllScheme(@RequestBody JSONObject json) {
        try {
            Long id = json.getAsNumber("id").longValue();
            List<Map<String, Object>> allDetails = propertyService.getUnitAllScheme();

            if (!allDetails.isEmpty()) {
                return ResponseEntity.ok(responseDo.setSuccessResponse(allDetails));
            } else {
                return ResponseEntity.ok(responseDo.setSuccessResponse("No Data Found", null));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(responseDo.setFailureResponse("An error occurred while fetching Details."));
        }
    }

    //Get Scheme name by scheme id
    @PostMapping("/getSchemeName")
    public String getSchemeName(@RequestBody JSONObject json) {

        Long scheme = json.getAsNumber("schemeId").longValue();
        String schemeName = propertyService.getSchemeName(scheme);
        return schemeName;

    }


    //Category wise count Increment/Decrement
    @PostMapping("/getCategoryWiseUnsold")
    public ResponseEntity<ResponseDo> getCategoryWiseUnsold(@RequestBody JSONObject json) {
        Long schemeId = json.getAsNumber("schemeId").longValue();
        String category = json.getAsString("category");
        String subcategory = json.getAsString("subcategory");
        Long unitId = json.getAsNumber("unitId").longValue();
        String status = json.getAsString("status");
        String reservation = json.getAsString("reservation");

        try {
            ResponseEntity<String>  schemeData = propertyService.updateDetailsOnBooking(schemeId, unitId, reservation,status, category, subcategory);

            if (schemeData != null) {
                return ResponseEntity.ok(responseDo.setSuccessResponse(schemeData));
            } else {
                return ResponseEntity.ok(responseDo.setFailureResponse(" An error occurred while updating the data "));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(responseDo.setFailureResponse("An error occurred while updating data."));
        }
    }

    // Unit Scheme Details for Unit ID CUSTOMER APPLICATION AUTOPOPULATE
    @PostMapping("/getUnitSchemeDetails")
    public ResponseDo getUnitSchemeDetails(@RequestBody JSONObject json) {

        Long unitId = json.getAsNumber("unitId").longValue();
        try {
            Map<String, Object> details = propertyService.getUnitSchemeDetails(unitId);

            if (details != null) {
                return responseDo.setSuccessResponse(details);
            } else {
                return responseDo.setFailureResponse(" An error occurred while getting the details");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return responseDo.setFailureResponse("An error occurred while getting the details.");
        }
    }

   // Accepted Allotte Save
   @PostMapping("/saveCAllottee")
   public ResponseDo saveCAllottee (@RequestBody CAllottee cAllottee) {
       try {
           CAllottee savedCAllottee = propertyService.CAllotteeSave(cAllottee);

           if (savedCAllottee != null) {
               return responseDo.setSuccessResponse(savedCAllottee);
           } else {
               return responseDo.setFailureResponse("Failed to save Circle Office.");
           }
       } catch (Exception e) {
           e.printStackTrace();
           return responseDo.setFailureResponse("An error occurred while saving Circle Office.");
       }
   }

   // Accepted All Allotte GET

    @PostMapping("/getAllCAllottee")
    public ResponseDo getAllCAllottee() {
        try {
            List<CAllottee> allCAllottee = propertyService.getAllCAllottee();

            if (allCAllottee != null) {
                return responseDo.setSuccessResponse(allCAllottee);
            } else {
                return responseDo.setFailureResponse("No Data Found");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return responseDo.setFailureResponse("An error occurred"); // Return a failure response in case of exception
        }
    }

    // Accepted Allottee Get by ID
    @PostMapping("/getByIdCAllottee")
    public ResponseEntity<ResponseDo> getCAllotteeById(@RequestBody JSONObject json) {
        Long id = json.getAsNumber("UnitNId").longValue();
        try {
            CAllottee cAllottee = propertyService.getCAllottee(id);

            if (cAllottee != null) {
                return ResponseEntity.ok(responseDo.setSuccessResponse(cAllottee));
            } else {
                return ResponseEntity.ok(responseDo.setFailureResponse("CustomerApplication not found for the provided ID."));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(responseDo.setFailureResponse("An error occurred while fetching CustomerApplication."));
        }
    }

    // Accepted CAllottee Upload Files
    @PostMapping("/saveUpload")
    public ResponseDo saveCAllotteeuploadfile (@RequestBody CAllottee cAllottee) {
        try {
            CAllottee savedCAllottee = propertyService.uploadCAllotteeData(cAllottee);

            if (savedCAllottee != null) {
                return responseDo.setSuccessResponse(savedCAllottee);
            } else {
                return responseDo.setFailureResponse("Failed to save CAllottee.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return responseDo.setFailureResponse("An error occurred while saving CAllottee.");
        }

    }


    // ALLOTTEE DASHBOARD - CALLOTTEE

    // Getting Allottee Details And Scheme Details
    @PostMapping("/getCAlloteeDetail")
    public ResponseDo getCAlloteeDetail (@RequestBody JSONObject json) {
        try {
            Long id = json.getAsNumber("customerId").longValue();

            Map<String, Object> details = propertyService.getAllotteeDetail(id);

            if (details != null) {
                return responseDo.setSuccessResponse(details);
            } else {
                return responseDo.setFailureResponse("Failed to get Allottee Detail.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return responseDo.setFailureResponse("An error occurred while getting Allottee Detail.");
        }
    }

    // Documents for Allottee Dashboard
    @PostMapping("/getDocumentsForAllottee")
    public ResponseDo getDocumentsForAllottee (@RequestBody JSONObject json) {
        try {
            Long id = json.getAsNumber("customerId").longValue();

            Map<String, Object> details = propertyService.getDocumentForAllottee(id);

            if (details != null) {
                return responseDo.setSuccessResponse(details);
            } else {
                return responseDo.setFailureResponse("Failed to get Allottee Documents.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return responseDo.setFailureResponse("An error occurred while getting Allottee Documents.");
        }
    }
}





