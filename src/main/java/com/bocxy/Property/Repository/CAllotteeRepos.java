package com.bocxy.Property.Repository;

import com.bocxy.Property.Entity.CAllottee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CAllotteeRepos extends JpaRepository<CAllottee, Long> {
    CAllottee findByUnitNId(Long id);

    @Query(value = "SELECT applicant_name, mobile_number, email_id, \n" +
            "joint_applicant_name, aadhaar_number, pan_number, \n" +
            "correspondence_address, unit_account_no, unit_type,\n" +
            "mode_of_allotment, division, city_rural, circle, scheme,\n" +
            "type, block_no, floor_no, flat_no, unit_no, plot_uds_area,\n" +
            "plinth_area, cost_of_unit, reservation_category, n_id\n" +
            " FROM property_db.callotee where customer_id = :id",nativeQuery = true)
    List<Object> getCAllotteeDetail(@RequestParam("id") Long id);

    @Query(value = "SELECT application_upload_filepath, allotment_order_filepath, lcs_agreement_filepath, a_b_loan_filepath,\n" +
            "field_measurement_book_filepath, handing_over_report_filepath, draft_sale_deed_filepath\n" +
            " FROM property_db.callotee where customer_id = :id",nativeQuery = true)
    List<Object> getCAllotteeDocuments(@RequestParam("id") Long id);

}