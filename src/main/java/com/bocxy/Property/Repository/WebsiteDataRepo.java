package com.bocxy.Property.Repository;
import com.bocxy.Property.Entity.WebsiteData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;


public interface WebsiteDataRepo extends JpaRepository<WebsiteData, Long> {

    @Query(value = "SELECT DISTINCT  sd.V_SCHEME_NAME AS v_scheme_name, sd.V_DIVISION AS v_division, " +
            "sd.N_TOTAL_UNSOLD_UNITS AS n_total_unsold_units, " +
            "sd.N_NO_OF_HIG_UNITS AS n_no_of_hig_units, " +
            "sd.N_NO_OF_MIG_UNITS AS n_no_of_mig_units, " +
            "sd.N_NO_OF_LIG_UNITS AS n_no_of_lig_units, " +
            "sd.N_NO_OF_EWS_UNITS AS n_no_of_ews_units, " +
            "sd.V_UNIT_TYPE As v_unit_type, " +
            "ud.N_SCHEME_ID AS n_scheme_id, " +
            "ud.V_UNIT_ALLOTTED_STATUS AS v_unit_allotted_status ," +
            "wd.F_PHOTO AS f_photo "+
            "FROM scheme_data sd JOIN unit_data ud ON sd.N_ID = ud.N_SCHEME_ID JOIN website_data wd ON ud.N_SCHEME_ID = wd.N_SCHEME_ID " +
            "WHERE ud.V_UNIT_ALLOTTED_STATUS = 'no'  AND sd.N_TOTAL_UNSOLD_UNITS>0 ", nativeQuery = true)
    List<Map<String, Object>> findAllUnitData();

    @Query(nativeQuery = true, value =
            "SELECT DISTINCT sd.V_SCHEME_NAME AS v_scheme_name, " +
                    "sd.V_DIVISION AS v_division, " +
                    "sd.N_TOTAL_UNSOLD_UNITS AS n_total_unsold_units, " +
                    "sd.N_NO_OF_HIG_UNITS AS n_no_of_hig_units, " +
                    "sd.N_NO_OF_MIG_UNITS AS n_no_of_mig_units, " +
                    "sd.N_NO_OF_LIG_UNITS AS n_no_of_lig_units, " +
                    "sd.N_NO_OF_EWS_UNITS AS n_no_of_ews_units, " +
                    "sd.V_UNIT_TYPE AS v_unit_type, " +

                    "wd.f_poc_picture AS f_poc_picture, " +
                    "wd.v_poc_mobile AS v_poc_mobile, " +
                    "wd.v_poc_name AS v_poc_name, " +
                    "wd.v_poc_email AS v_poc_email, " +
                    "wd.v_geo_tag_link AS v_geo_tag_link, " +
                    "wd.v_project_description AS v_project_description, " +
                    "wd.v_amenities AS v_amenities, " +
                    "wd.f_video AS f_video, " +
                    "wd.f_floor_plan_picture AS f_floor_plan_picture, " +
                    "fpc.f_photo AS f_photo " +
                    "FROM scheme_data sd " +

                    "JOIN website_data wd ON sd.N_ID = wd.N_SCHEME_ID " +
                    "LEFT JOIN fphoto_collection fpc ON wd.N_ID = fpc.N_ID " +
                    "WHERE sd.N_ID = :schemeId")
    List<Map<String, Object>> findSchemeDataBySchemeId(@Param("schemeId") Long schemeId);



    @Query(value = "SELECT DISTINCT ud.N_ID AS n_apartment_no, " +
            "ud.N_SCHEME_ID AS scheme_id, " + // Added a comma here
            "ud.V_FLOOR_NO AS v_floor_no, " + // Added a comma here
            "ud.V_BLOCK_NO AS v_block_no " +  // Added a space at the end
            "FROM  unit_data ud",
//            "JOIN website_data wd ON ud.N_SCHEME_ID = wd.N_SCHEME_ID ", // Added a space at the beginning
             nativeQuery = true)

        List<Map<String, Object>> findUnitDetailsBySchemeId(@Param("schemeId") Long schemeId);


    @Query(value = "SELECT DISTINCT  ud.V_BLOCK_NO AS v_block_no, " +
            "ud.N_SCHEME_ID AS scheme_id " +// Added a comma here
            "FROM unit_data ud",
           nativeQuery = true)
    List<Map<String, Object>> findBlocksBySchemeId(@Param("schemeId")Long schemeId);

    @Query(value = "SELECT DISTINCT  ud.V_BLOCK_NO AS v_block_no, " +
            "ud.V_FLOOR_NO AS v_floor_no, " +
            "ud.N_SCHEME_ID AS scheme_id " +// Added a comma here
            "FROM unit_data ud",
            nativeQuery = true)
    List<Map<String, Object>> findFloorDetailsBySchemeId(@Param("schemeId")Long schemeId);

    @Query(value = "SELECT ud.V_UNIT_ID AS v_unit_id,"+
            "ud.V_UNIT_ALLOTTED_STATUS AS v_unit_allotted_status"+
            "FROM unit_data ud",   nativeQuery = true)
    Map<String, Object> findUnitDataWithStatus(Long unitId);

    List<WebsiteData> findBynSchemeId(Long nschemeId);


}
