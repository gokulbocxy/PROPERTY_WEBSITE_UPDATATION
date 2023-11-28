package com.bocxy.Property.Repository;

import com.bocxy.Property.Entity.Allottee;
import com.bocxy.Property.Entity.SalesDeedData;
import com.bocxy.Property.Entity.UnitData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface SalesDeedDataRepo extends JpaRepository<SalesDeedData, Long> {

    List<SalesDeedData> findByNAllotteeId(Long id);

    @Query("SELECT u FROM SalesDeedData u WHERE u.nSchemeId = :nSchemeId")
    List<SalesDeedData> findByNSchemeId(@RequestParam("nSchemeId") Long nSchemeId);

}
