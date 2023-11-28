package com.bocxy.Property.Repository;
import com.bocxy.Property.Entity.UnitData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Repository
public interface UnitDataRepo extends JpaRepository<UnitData, Long> {
    @Query("SELECT u FROM UnitData u WHERE u.N_SCHEME_ID = :nSchemeId")
    List<UnitData> findByNSchemeId(@RequestParam("nSchemeId") Long nSchemeId);
    @Query("SELECT COUNT(u) FROM UnitData u JOIN SchemeData s ON u.N_SCHEME_ID = s.N_ID WHERE u.N_SCHEME_ID = :schemeId AND u.V_UNIT_ALLOTTED_STATUS = 'yes'")
    Long updateTotalAllottedUnits(@RequestParam("schemeId") Long schemeId);

    @Query("SELECT COUNT(u) FROM UnitData u JOIN SchemeData s ON u.N_SCHEME_ID = s.N_ID WHERE u.N_SCHEME_ID = :schemeId AND u.V_ASSET_TYPE ='Residential' AND u.V_UNIT_ALLOTTED_STATUS = 'yes'")
    Long allottedResidential(@RequestParam("schemeId") Long schemeId);
    @Query("SELECT COUNT(u) FROM UnitData u JOIN SchemeData s ON u.N_SCHEME_ID = s.N_ID WHERE u.N_SCHEME_ID = :schemeId AND u.V_ASSET_TYPE = 'Commercial' AND u.V_UNIT_ALLOTTED_STATUS = 'yes'")
    Long allottedCommercial(@RequestParam("schemeId") Long schemeId);

    @Query("SELECT COUNT(u) FROM UnitData u JOIN SchemeData s ON u.N_SCHEME_ID = s.N_ID WHERE u.N_SCHEME_ID = :schemeId AND u.V_TYPE_NAME = 'Hire Purchase' AND u.V_UNIT_ALLOTTED_STATUS = 'yes'")
    Long allotedHirePurchase(@RequestParam("schemeId") Long schemeId);

    @Query("SELECT COUNT(u) FROM UnitData u JOIN SchemeData s ON u.N_SCHEME_ID = s.N_ID WHERE u.N_SCHEME_ID = :schemeId AND u.V_TYPE_NAME ='Self Finance' AND u.V_UNIT_ALLOTTED_STATUS = 'yes'")
    Long allottedSelfFinance(@RequestParam("schemeId") Long schemeId);
    @Query("SELECT COUNT(u) FROM UnitData u JOIN SchemeData s ON u.N_SCHEME_ID = s.N_ID WHERE u.N_SCHEME_ID = :schemeId AND u.V_TYPE_NAME ='Outright' AND u.V_UNIT_ALLOTTED_STATUS = 'yes'")
    Long allottedOutright(@RequestParam("schemeId") Long schemeId);
    @Query("SELECT COUNT(u) FROM UnitData u JOIN SchemeData s ON u.N_SCHEME_ID = s.N_ID WHERE u.N_SCHEME_ID = :schemeId AND u.VAssetSubCategory = 'HIG' AND u.V_UNIT_ALLOTTED_STATUS = 'yes'")
    Long allottedHIG(@RequestParam("schemeId") Long schemeId);
    @Query("SELECT COUNT(u) FROM UnitData u JOIN SchemeData s ON u.N_SCHEME_ID = s.N_ID WHERE u.N_SCHEME_ID = :schemeId AND u.VAssetSubCategory ='MIG' AND u.V_UNIT_ALLOTTED_STATUS = 'yes'")
    Long allottedMIG(@RequestParam("schemeId") Long schemeId);
    @Query("SELECT COUNT(u) FROM UnitData u JOIN SchemeData s ON u.N_SCHEME_ID = s.N_ID WHERE u.N_SCHEME_ID = :schemeId AND u.VAssetSubCategory ='LIG' AND u.V_UNIT_ALLOTTED_STATUS = 'yes'")
    Long allottedLIG(@RequestParam("schemeId") Long schemeId);
    @Query("SELECT COUNT(u) FROM UnitData u JOIN SchemeData s ON u.N_SCHEME_ID = s.N_ID WHERE u.N_SCHEME_ID = :schemeId AND u.VAssetSubCategory ='EWS' AND u.V_UNIT_ALLOTTED_STATUS = 'yes'")
    Long allottedEWS(@RequestParam("schemeId") Long schemeId);

    @Query("SELECT u.V_UNIT_AC_NO,s.V_SCHEME_TYPE, u.V_MODE_OF_ALLOTMENT, s.V_CITY_RURAL, s.V_CIRCLE, s.V_SCHEME_NAME,\n" +
            "s.V_UNIT_TYPE, u.V_BLOCK_NO, u.V_FLOOR_NO, u.V_FLAT_NO, u.V_UNIT_NO,\n" +
            "u.V_PLOT_AREA, s.V_UDS_AREA, u.V_PLINTH_AREA, u.V_UNIT_COST , s.V_RESERVATION_STATUS\n" +
            "FROM UnitData u \n" +
            "JOIN SchemeData s \n" +
            "ON u.N_SCHEME_ID = s.N_ID\n" +
            "WHERE u.N_ID = :unitId")
    List<Object[]> findUnitScheme(@RequestParam("unitId") Long unitId);

    @Query(value = "SELECT s.V_SCHEME_CODE, s.V_SCHEME_NAME, s.V_SCHEME_TYPE, s.V_UNIT_TYPE, s.N_TOTAL_UNITS,\n" +
            "    u.V_UNIT_NO, u.V_BLOCK_NO, u.V_FLOOR_NO, u.V_UNIT_ALLOTTED_STATUS, u.N_ID,\n" +
            "    u.V_UNIT_COST\n" +
            "FROM unit_data u JOIN scheme_data s ON u.n_scheme_id = s.N_ID\n" +
            "WHERE u.N_SCHEME_ID = :scheme AND u.v_unit_allotted_status REGEXP '^(?i)(pending|no)$'", nativeQuery = true)
    List<Object[]> findPropertyData(@RequestParam("scheme") Long scheme);

    @Query(value = "SELECT s.V_SCHEME_CODE, s.V_SCHEME_NAME, s.V_SCHEME_TYPE, s.V_UNIT_TYPE, s.N_TOTAL_UNITS,\n" +
            "    u.V_UNIT_NO, u.V_BLOCK_NO, u.V_FLOOR_NO, u.V_UNIT_ALLOTTED_STATUS, u.N_ID,\n" +
            "    u.V_UNIT_COST, s.V_CIRCLE, s.V_DIVISION, s.V_DISTRICT\n" +
            "FROM unit_data u JOIN scheme_data s ON u.n_scheme_id = s.N_ID\n" +
            "WHERE u.v_unit_allotted_status REGEXP '^(?i)(pending|no)$'", nativeQuery = true)
    List<Object[]> findAllSchemeUnit();

    @Modifying
    @Query(value = "UPDATE scheme_data " +
            "SET N_TOTAL_ALLOTTED_UNITS = (SELECT COUNT(*) FROM unit_data WHERE N_SCHEME_ID = :nId AND V_UNIT_ALLOTTED_STATUS = 'Yes'), " +
            "N_TOTAL_UNSOLD_UNITS = (N_TOTAL_UNITS - N_TOTAL_ALLOTTED_UNITS) " +
            "WHERE N_ID = :nId", nativeQuery = true)
    void updateSchemeData(@Param("nId") Long nId);

    @Modifying
    @Query(value = "UPDATE scheme_data " +
            "SET n_full_cost_paid = (SELECT COUNT(*) FROM unit_data WHERE N_SCHEME_ID = :nId AND v_fc_paid_status = 'Yes'), " +
            "N_TOTAL_LIVE_CASES_FOR_HIRE = (SELECT COUNT(*) FROM unit_data WHERE N_SCHEME_ID = :nId AND v_live_status = 'Yes'), " +
            "N_TOTAL_RIPPED_UNIT = (SELECT COUNT(*) FROM unit_data WHERE N_SCHEME_ID = :nId AND v_riped_status = 'Yes'), " +
            "N_TOTAL_NO_OF_SALE_DEED_ISSUED = (SELECT COUNT(*) FROM unit_data WHERE N_SCHEME_ID = :nId AND v_sale_deed_status = 'Yes') " +
            "WHERE N_ID = :nId", nativeQuery = true)
    void schemeDataUpdated(@Param("nId") Long nId);
}