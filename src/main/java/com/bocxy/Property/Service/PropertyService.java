package com.bocxy.Property.Service;
import com.bocxy.Property.Entity.*;
import com.bocxy.Property.Model.*;
import org.springframework.http.ResponseEntity;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PropertyService {

    List<Allottee> saveAllottees(List<Allottee> allottees);

    List<Allottee> getAllotteesBySchemeId(Long schemeId);

    List<SchemeData>getAllSchemeData();

    List<SchemeData> saveSchemeData(List<SchemeData> schemeData);

    SchemeData getSchemeData(Long id);

    String getDivisionCode(String divisionName);

    int getDivSchemeCount(String divName);

    List<String> getAllDivision();

    List<UnitData> saveUnitData(List<UnitData> unitData);

    List<UnitData> getUnits(Long nSchemeId);

    void deleteSchemeData(Long id);

    void deleteAllotteeData(Long id);

    void deleteUnitData(Long id);

    List<SalesDeedData> saveSalesDeed(List<SalesDeedData> salesdeed);

    List<SalesDeedData> getAllSalesDeeds(Long nSchemeId);


    // ALLOTTEE DASHBOARD
    boolean emailExists(String email);

    void sendOtpToAllottee(String email);

    List<Allottee> verifyOtpAndGetAllottee(String email, String otp);

    List<Allottee> getAllotteebyId(Long id);

    CombinedDataModel getDetailsById(Long id);

    List<SalesDeedData> getSalesDeedDatabyId(Long id);


    //WEBSITE MODULE
    List<WebsiteData> saveWebsiteData(List<WebsiteData> websiteDataList);

//    List<WebsiteData>getWebsiteData();

    List<WebsiteModel>getAllWebsiteData();

    List<Map<String, Object>> getAllSchemeForWebsite();

    Map<String, Object> getSchemeDataBySchemeId(Long schemeId);

    void deleteWebsiteData(Long id);

    List<WebsiteData>getWebsiteData(Long nschemeId);


    List<Map<String, Object>> findUnitDetailsBySchemeId(Long schemeId);

    // Get One Unit Data
    UnitData getOneUnit(Long nId);

    // Save One Unit Data
    UnitData saveOneUnitData(UnitData unitData);

    List <Map<String, Object>> getBlocksForWebsite(Long schemeId);

    ResponseEntity<Object> bookNow(Long unitId);

    List<Map<String, Object>> getFloorForWebsite(Long schemeId);

    String findById(Long unitId);

    //Enquiry
    String sendMailToCustomer(Enquiry enquiry, String vPocEmail, String vEmail);

    //Financial

    //CREATE EMI CALCULATION
    void calculateEmiRows(CalculationRequestModel request);

    //CREATE DEMAND CALCULATION

    void calculateDemandRows();

    //CREATE/UPDATE COLLECTION
     Financial_Calc updateCollectionData(CollectionRequestModel request);

    Financial_Calc GetDemandModel(GetDemandModel request);


    // Website Customer Booking Application

    List<SchemeData>getAllWebsite();

    @Transactional
    CustomerApplication saveCustomerApplication(CustomerApplication customerApplication);

    List<CustomerApplication> getAllCustomerApplications();

    CustomerApplication getCustomerApplication(Long id);

    //Get Applications for Single Customer
    List<CustomerApplication> getCustomerApplications(Long id);

    //PROPERTY Application Related
    List<Map<String, Object>> getAllSchemesWithAppl();

    List<CustomerApplication> getAllApplOfOneScheme(String scheme);

    //Website Category Wise Count List
    Optional<SchemeData> getbyidSchemedata(Long n_id);

    //Get Block/Floor Details for Booking
    List<Map<String, Object>> getPropertyData(Long scheme);

    //Get Block/Floor Booking for All Schemes
    List<Map<String, Object>> getUnitAllScheme();

    //Get Scheme name by scheme id
    String getSchemeName(Long scheme);

    //Category Wise List Count Increment/Decrement
    ResponseEntity<String> updateDetailsOnBooking(Long schemeId, Long unitId, String reservation, String status, String category, String subCategory);

    // Unit Scheme Details for Unit ID CUSTOMER APPLICATION AUTOPOPULATE
    Map<String, Object> getUnitSchemeDetails(Long unitId);

    SchemeData editSchemeData(SchemeData updatedSchemeData);

    CAllottee CAllotteeSave(CAllottee cAllottee);

    List<CAllottee> getAllCAllottee();

    CAllottee getCAllottee(Long id);

    @Transactional
    CAllottee uploadCAllotteeData(CAllottee uploadfile);

    List<CustomerApplication> getPendingCustomerApplications();

    CustomerApplication updateCustomerApplicationById(Long id, CustomerApplication updatedCustomerApplication);

    Map<String, Object> getAllotteeDetail(Long id);

    Map<String, Object> getDocumentForAllottee(Long id);
}
