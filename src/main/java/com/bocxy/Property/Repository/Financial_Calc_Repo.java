package com.bocxy.Property.Repository;

import com.bocxy.Property.Entity.Financial_Calc;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;

@Repository
public interface Financial_Calc_Repo extends JpaRepository<Financial_Calc, Long> {

    Financial_Calc findByNUNITIDAndMONTHNYEAR(Long nUnitId, String monthyear);

//    Financial_Calc findByNUNITID(Long nUnitId);

    Financial_Calc findByDATE(LocalDate month);

    Financial_Calc findByMONTHNYEAR(String monthyear);

//    Financial_Calc findByMonth(Date nextExecutionTime);
}
