package com.bocxy.Property.Entity;

import javax.persistence.*;
import lombok.Data;
import org.springframework.scheduling.support.SimpleTriggerContext;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "Financial_Calc")
public class Financial_Calc {
//    private static final double N_ARREAR_BALANCE = ;


//EMI Working

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "N_ID")
    private Long N_ID;

    @Column(name = "NUNITID")
    private Long NUNITID;

    @Column(name = "DATE")
    private LocalDate DATE;

    @Column(name = "MONTHNYEAR")
    private String MONTHNYEAR;

    @Column(name = "V_EMI_OPENING")
    private Double V_EMI_OPENING;

    @Column(name = "N_INTEREST_FOR_EMI")
    private Double N_INTEREST_FOR_EMI;

    @Column(name = "V_PRINCIPAL_FOR_EMI")
    private Double V_PRINCIPAL_FOR_EMI;

    @Column(name = "N_BALANCE_FOR_EMI")
    private Double N_BALANCE_FOR_EMI;

    // Demand Sheet Working

    //Current Demand

    @Column(name = "N_ARREAR_CD")
    private Double N_ARREAR_CD;

    @Column(name = "N_EMI_CD")
    private Double N_EMI_CD;

    @Column(name = "N_INITIAL_DEPOSIT_CD")
    private Double N_INITIAL_DEPOSIT_CD;

    @Column(name = "N_PENAL_INTEREST_CD")
    private Double N_PENAL_INTEREST_CD;

    @Column(name = "N_INITIAL_DEPOSIT_PI_CD")
    private Double N_INITIAL_DEPOSIT_PI_CD;

    @Column(name = "TOTAL_CD")
    private Double TOTAL_CD;

    //Collection

    @Column(name = "N_ARREAR_COL")
    private Double N_ARREAR_COL;

    @Column(name = "N_EMI_COL")
    private Double N_EMI_COL;

    @Column(name = "N_INITIAL_DEPOSIT_COL")
    private Double N_INITIAL_DEPOSIT_COL;

    @Column(name = "N_PENAL_INTEREST_COL")
    private Double N_PENAL_INTEREST_COL;

    @Column(name = "N_INITIAL_DEPOSIT_PI_COL")
    private Double N_INITIAL_DEPOSIT_PI_COL;

    @Column(name = "TOTAL_COL")
    private Double TOTAL_COL;

    //Balance

    @Column(name = "N_ARREAR_BALANCE")
    private Double N_ARREAR_BALANCE;

    @Column(name = "N_EMI_BALANCE")
    private Double N_EMI_BALANCE;

    @Column(name = "N_INITIAL_DEPOSIT_BALANCE")
    private Double N_INITIAL_DEPOSIT_BALANCE;

    @Column(name = "N_PENAL_INTEREST_BALANCE")
    private Double N_PENAL_INTEREST_BALANCE;

    @Column(name = "N_INITIAL_DEPOSIT_PI_BALANCE")
    private Double N_INITIAL_DEPOSIT_PI_BALANCE;

    @Column(name = "TOTAL_BALANCE")
    private Double TOTAL_BALANCE;

    //Balance Auto Calculation
    public void updateBalanceColumns() {

        if (N_ARREAR_CD != null && N_ARREAR_COL != null) {
            N_ARREAR_BALANCE = N_ARREAR_CD - N_ARREAR_COL;
        }
        if (N_EMI_CD != null && N_EMI_COL != null) {
            N_EMI_BALANCE = N_EMI_CD - N_EMI_COL;
        }
        if (N_INITIAL_DEPOSIT_CD != null && N_INITIAL_DEPOSIT_COL != null) {
            N_INITIAL_DEPOSIT_BALANCE = N_INITIAL_DEPOSIT_CD - N_INITIAL_DEPOSIT_COL;
        }
        if (N_PENAL_INTEREST_CD != null && N_PENAL_INTEREST_COL != null) {
            N_PENAL_INTEREST_BALANCE = N_PENAL_INTEREST_CD - N_PENAL_INTEREST_COL;
        }
        if (N_INITIAL_DEPOSIT_PI_CD != null && N_INITIAL_DEPOSIT_PI_COL != null) {
            N_INITIAL_DEPOSIT_PI_BALANCE = N_INITIAL_DEPOSIT_PI_CD - N_INITIAL_DEPOSIT_PI_COL;
        }
        if (TOTAL_CD != null && TOTAL_COL != null) {
            TOTAL_BALANCE = TOTAL_CD - TOTAL_COL;
        }
    }


    public Financial_Calc(String cronExpression) {
    }

    public Financial_Calc() {

    }

    public static void setFinancialCalc(Financial_Calc financialCalc) {
    }

    public static Financial_Calc getFinancialCalc() {
        return new Financial_Calc();
    }


    public double getN_INITIAL_DEPOSIT_TO_BE_PAID() {
        return 0;
    }

    public boolean isN_EMI_COL() {
        return false;
    }

    public Date next(SimpleTriggerContext date) {
//        return date;
        return null;
    }

    public java.sql.Date nextExecutionTime(SimpleTriggerContext simpleTriggerContext) {
        return null;
    }

    public double getV_EMI_OPENING(LocalDate fixedMonth) {
        return  getV_EMI_OPENING();
    }



}


