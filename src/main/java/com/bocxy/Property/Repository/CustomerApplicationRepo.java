package com.bocxy.Property.Repository;

import com.bocxy.Property.Entity.CustomerApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerApplicationRepo extends JpaRepository<CustomerApplication, Long> {
    List<CustomerApplication> findByCustomerid(Long id);

    @Query(value = "select distinct c.scheme, s.v_cr_code, s.v_cc_code, s.v_d_code, s.v_scheme_code, s.v_splace, s.v_district, s.v_unit_type, s.v_scheme_type from property_db.scheme_data s join property_db.customer c on s.v_scheme_name = c.scheme", nativeQuery = true)
    List<Object[]> getSchemeDetail();
    List<CustomerApplication> findByScheme(String scheme);

    @Query(value = "SELECT * FROM customer WHERE status = 'Pending'", nativeQuery = true)
    List<CustomerApplication> getPendingCustomerApplication();

}