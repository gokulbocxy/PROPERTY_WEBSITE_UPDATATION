package com.bocxy.Property.Repository;

import com.bocxy.Property.Entity.Financial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FinancialRepo extends JpaRepository <Financial, Long> {


    Financial findByNUNITID(Long nUnitId);

    Financial findByMONTH(LocalDate month);

    @Query("SELECT f FROM Financial f WHERE f.EMI_START_DATE = :emiStartDate")
    List<Financial> findByEMI_START_DATE(@Param("emiStartDate") LocalDate emiStartDate);

//    Financial findByEMI_START_DATE(LocalDate month);

//    Financial findByDATE(LocalDate month);
}
