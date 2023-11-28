package com.bocxy.Property.Repository;


import com.bocxy.Property.Entity.Allottee;
import com.bocxy.Property.Model.CombinedDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface AllotteeRepo extends JpaRepository<Allottee, Long> {


    List<Allottee> findAllByEMAILID(String email);

    List<Allottee> findByNID(Long id);
    @Query(value = "SELECT a.d_allottee_date, a.v_allottee_no, u.v_block_no, u.v_carpet_area, u.v_floor_no," +
            " u.v_plot_area, u.v_type_name, u.v_uds_area, u.v_unit_no, u.v_asset_category,u.vasset_sub_category, u.v_asset_type," +
            " u.v_plinth_area, s.n_final_land_cost, s.v_scheme_name FROM allottee_details a " +
            "JOIN unit_data u ON a.n_unit_id = u.n_id " +
            "JOIN scheme_data s ON a.n_scheme_id = s.n_id WHERE a.n_id = ?1", nativeQuery = true)
    List<Object[]> findDetailsById(Long id);


    @Query( "SELECT a FROM Allottee a WHERE nSchemeId = :schemeId")
    List<Allottee> findAllByNSchemeId(@RequestParam("schemeId") Long schemeId);
}