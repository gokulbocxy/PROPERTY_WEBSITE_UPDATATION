package com.bocxy.Property.Repository;


import com.bocxy.Property.Entity.SchemeData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Repository
public interface SchemeDataRepo extends JpaRepository<SchemeData, Long> {

    @Query(value = "select d.v_division_code from division_office d where d.v_division_name = ?1", nativeQuery = true)
    String getDivisionCode(@RequestParam("divName") String divName);

    @Query(value = "select count(s.v_division)+1 from scheme_data s where s.v_division = ?1", nativeQuery = true)
    int getDivSchemeCount(@RequestParam("divName") String divName);

    @Query(value = "SELECT v_division_name FROM division_office ORDER BY v_division_name", nativeQuery = true)
    List<String> getAllDivision();

    @Query(value = "SELECT v_scheme_name FROM property_db.scheme_data WHERE n_id = :scheme", nativeQuery = true)
    String findSchemeName(@RequestParam("scheme") Long scheme);


    @Query(value = "SELECT" +
            "    sd.N_ID,\n" +
            "    sd.V_FROM_DATE,\n" +
            "    sd.V_TO_DATE,\n" +
            "    sd.V_RESERVATION_STATUS,\n" +
            "    sd.V_PROJECT_STATUS,\n" +
            "    sd.V_CIRCLE,\n" +
            "    sd.V_DIVISION,\n" +
            "    sd.V_DISTRICT,\n" +
            "    sd.V_SCHEME_NAME,\n" +
            "    sd.V_UNIT_TYPE,\n" +
            "    sd.V_SCHEME_TYPE,\n" +
            "    sd.N_TOTAL_UNITS,\n" +
            "    sd.N_TOTAL_ALLOTTED_UNITS,\n" +
            "    sd.N_TOTAL_UNSOLD_UNITS,\n" +
            "    sd.V_SELLING_EXTENT,\n" +
            "    sd.V_START_FROM,\n" +
            "    (\n" +
            "        SELECT fpc.f_photo\n" +
            "        FROM property_db.website_data wd\n" +
            "        JOIN property_db.fphoto_collection fpc ON wd.n_id = fpc.n_id\n" +
            "        WHERE wd.n_scheme_id = sd.N_ID\n" +
            "        LIMIT 1\n" +
            "    ) AS f_photo\n" +
            "FROM scheme_data sd", nativeQuery = true)
    List<Object[]> findSchemeDetail();

}