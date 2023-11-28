package com.bocxy.Property.Entity;

import javax.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "financial")
public class Financial {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "N_ID")
    private Long NID;

    @Column(name = "NUNITID")
    private Long NUNITID;

    @Column(name = "MONTH")
    private LocalDate MONTH;

    @Column(name = "V_SCHEME_ID")
    private String V_SCHEME_ID;

    @Column(name ="EMI_START_DATE")
    private LocalDate EMI_START_DATE;

    @Column(name ="V_RATE_OF_INTEREST")
    private double V_RATE_OF_INTEREST;

    @Column(name="V_PAYMENT_PERIOD_IN_YEARS")
    private  int  V_PAYMENT_PERIOD_IN_YEARS;

    @Column(name="V_PRINCIPAL_AMOUNT")
    private  double V_PRINCIPAL_AMOUNT;

    @Column(name = "N_INITIAL_DEPOSIT")
    private double N_INITIAL_DEPOSIT;

    @Column(name = "N_INITIAL_DEPOSIT_PAID")
    private double N_INITIAL_DEPOSIT_PAID;

    @Column(name = "N_INITIAL_DEPOSIT_TO_BE_PAID")
    private double N_INITIAL_DEPOSIT_TO_BE_PAID;



    // not touch

    @Column(name ="V_INTEREST_ON_PRINCIPAL_AMOUNT")
    private String V_INTEREST_ON_PRINCIPAL_AMOUNT;

    @Column(name = "V_CUSTOMER_UNIT_ID")
    private String V_CUSTOMER_UNIT_ID;

    @Column(name = "V_UNIT_SELLING_COST")
    private String V_UNIT_SELLING_COST;

    @Column(name = "V_TENTATIVE_LAND_COST")
    private String V_TENTATIVE_LAND_COST;

    @Column(name = "V_FINAL_LAND_COST")
    private String V_FINAL_LAND_COST;

    @Column(name = "V_ALLOTTMENT_DATE")
    private String V_ALLOTTMENT_DATE;

    @Column(name = "V_FIRM_COST")
    private String V_FIRM_COST;

    @Column(name = "N_FREEZED_RATE")
    private String N_FREEZED_RATE;

    @Column(name = "N_MONTHLY_INSTALLMENT")
    private Long N_MONTHLY_INSTALLMENT;

    @Column(name = "V_DIFFERENCE_IN_SELLING_COST_LEFT_OUT_IF_ANY")
    private String V_DIFFERENCE_IN_SELLING_COST_LEFT_OUT_IF_ANY;

    @Column(name = "V_INTEREST_ON_LEFT_OUT_FROM_THE_READY_FOR_OCCUPATION")
    private String V_INTEREST_ON_LEFT_OUT_FROM_THE_READY_FOR_OCCUPATION;

    @Column(name = "V_DIFFERENCE_IN_LAND_COST_AS_ON_ALLOTTMENT")
    private String V_DIFFERENCE_IN_LAND_COST_AS_ON_ALLOTTMENT;

    @Column(name = "V_INTEREST_ON_DIFFERENCE_IN_LANDCOST")
    private String V_INTEREST_ON_DIFFERENCE_IN_LANDCOST;


    @Column(name ="V_EMI_DUE_DATE")
    private String V_EMI_DUE_DATE;

    @Column(name="V_ID_DUE_DATE")
    private  String V_IDE_DUE_DATE;

    //Demand

    @Column(name ="V_INITIAL_DEPOSIT")
    private String V_INITIAL_DEPOSIT;

    @Column(name ="V_INITIAL_DEPOSIT_BELATED_INTEREST")
    private String V_INITIAL_DEPOSIT_BELATED_INTEREST;

    @Column(name="V_TOTAL_EMI_DEMAND")
    private  String V_TOTAL_EMI_DEMAND;

    @Column(name ="V_PENAL_INTEREST_DEMAND")
    private String V_PENAL_INTEREST_DEMAND;

    @Column(name="N_TOTAL_DEMAND")
    private  String N_TOTAL_DEMAND;

    //Collection

    @Column(name ="V_INITIAL_DEPOSIT_PAID")
    private String V_INITIAL_DEPOSIT_PAID;

    @Column(name ="V_INITIAL_DEPOSIT_BELATED_INTEREST_FOR_DEMAND")
    private String V_INITIAL_DEPOSIT_BELATED_INTEREST_FOR_DEMAND;

    @Column(name ="V_TOTAL_EMI_COLLECTION")
    private String V_TOTAL_EMI_COLLECTION;

    @Column(name ="V_PENAL_INTEREST_FOR_COLLECTION")
    private String V_PENAL_INTEREST_FOR_COLLECTION;

    @Column(name="N_TOTAL_COLLECTION")
    private  String N_TOTAL_COLLECTION;

    //Outstanding due date for past due

    @Column(name ="V_INITIAL_DEPOSIT_BALANCE")
    private String V_INITIAL_DEPOSIT_BALANCE;

    @Column(name ="V_INITIAL_DEPOSIT_BELATED_INTEREST_FOR_BALANCE")
    private String V_INITIAL_DEPOSIT_BELATED_INTEREST_FOR_BALANCE;

    @Column(name ="V_TOTAL_EMI_OUTSTANDING")
    private String V_TOTAL_EMI_OUTSTANDING;

    @Column(name ="V_PENAL_INTEREST_FOR_DUES")
    private String V_PENAL_INTEREST_FOR_DUES;

    @Column(name="N_TOTAL_OUTSTANDING_DUES")
    private  String N_TOTAL_OUTSTANDING_DUES;


    //Outstanding due date for Future due

    @Column(name ="V_INITIAL_DEPOSIT_BALANCE_FUTURE_DUE")
    private String V_INITIAL_DEPOSIT_BALANCE_FUTURE_DUE;

    @Column(name ="V_TOTAL_EMI_OUTSTANDING_FUTURE_DUE")
    private String V_TOTAL_EMI_OUTSTANDING_FUTURE_DUE;

    @Column(name="N_TOTAL_OUTSTANDING_DUES_FUTURE_DUE")
    private  String N_TOTAL_OUTSTANDING_DUES_FUTURE_DUE;

    @Transient
    private String mode;

    public static void setFinancial(Financial financial) {
    }

    public double getV_EMI_OPENING() {
        return 0;
    }
}
