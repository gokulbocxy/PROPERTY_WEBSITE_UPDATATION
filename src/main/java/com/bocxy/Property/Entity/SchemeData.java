package com.bocxy.Property.Entity;

import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Formula;
import java.util.List;

@Entity
@Data
@Table(name="scheme_data")
public class SchemeData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "N_ID")
    private Long N_ID;

    //  Final Confirmed

    // ------------------ Type Information --------------------

    @Column(name="V_SCHEME_CODE")
    private String V_SCHEME_CODE;

    @Column(name="V_DIVISION")
    private String V_DIVISION;

    @Column(name="V_CIRCLE")
    private String V_CIRCLE;

    @Column(name="V_CITY_RURAL")
    private String V_CITY_RURAL;

    @Column(name="V_SCHEME_NAME")
    private String V_SCHEME_NAME;

    @Column(name="V_SCHEME_TYPE")
    private String V_SCHEME_TYPE;

    @Column(name="V_UNIT_TYPE")
    private String V_UNIT_TYPE;

    @Column(name="N_TOTAL_UNITS")
    private Long N_TOTAL_UNITS;

    @Column(name="V_SPLACE")
    private String V_SPLACE;

    @Column(name="V_DISTRICT")
    private String V_DISTRICT;

    @Column(name="V_RESERVATION_STATUS")
    private String V_RESERVATION_STATUS;

    @Column(name="V_FROM_DATE")
    private String V_FROM_DATE;

    @Column(name="V_TO_DATE")
    private String V_TO_DATE;

    @Column(name="V_PROJECT_STATUS")
    private String V_PROJECT_STATUS;

//    @Column(name="V_MODE_OF_ALLOCATION")
//    private String V_MODE_OF_ALLOCATION;

    @Column(name="V_CUT_OFF_DATE")
    private String V_CUT_OFF_DATE;

    @Column(name="N_SELLING_PRICE")
    private Long N_SELLING_PRICE;

    @Column(name="N_RATE_OF_SCHEME_INTEREST")
    private Long N_RATE_OF_SCHEME_INTEREST;

    @Column(name="V_REPAYMENT_PERIOD")
    private String V_REPAYMENT_PERIOD;

    @Column(name="V_SELLING_EXTENT")
    private String V_SELLING_EXTENT;

    @Column(name="V_PLINTH_AREA")
    private String V_PLINTH_AREA;

    @Column(name="V_UDS_AREA")
    private String V_UDS_AREA;

    @Column(name="V_TENTATIVE_LAND")
    private String V_TENTATIVE_LAND;

    @Column(name="V_FINAL_LAND")
    private String V_FINAL_LAND;

    @Column(name="N_TENTATIVE_LAND_COST")
    private Long N_TENTATIVE_LAND_COST;

    @Column(name="N_FINAL_LAND_COST")
    private Long N_FINAL_LAND_COST;

    @Column(name="V_FINAL_CUTOFF_DATE")
    private String V_FINAL_CUTOFF_DATE;

    @Column(name="N_PROFIT_ON_LAND")
    private Long N_PROFIT_ON_LAND;

    @Column(name="V_REMARKS")
    private String V_REMARKS;

    @Column(name="V_START_FROM")
    private String V_START_FROM;

    // --------------------- TYPE UPDATED VALUE -----------------------

    @Column(name="N_TOTAL_ALLOTTED_UNITS")
    private Long N_TOTAL_ALLOTTED_UNITS;

    @Column(name="N_TOTAL_UNSOLD_UNITS")
    private Long N_TOTAL_UNSOLD_UNITS;

    @Column(name="N_TOTAL_ALLOTTED_UNITS_FOR_OUTRIGHT")
    private Long N_TOTAL_ALLOTTED_UNITS_FOR_OUTRIGHT;

    @Column(name="N_TOTAL_ALLOTTED_UNITS_FOR_HIRE_PURCHASE")
    private Long N_TOTAL_ALLOTTED_UNITS_FOR_HIRE_PURCHASE;

    @Column(name="N_TOTAL_ALLOTTED_UNITS_FOR_SFS")
    private Long N_TOTAL_ALLOTTED_UNITS_FOR_SFS;

    @Column(name="N_TOTAL_ARREARS_EMI")
    private Long N_TOTAL_ARREARS_EMI;

    @Column(name="N_TOTAL_CURRENT_EMI")
    private Long N_TOTAL_CURRENT_EMI;

    @Column(name="N_TOTAL_BALANCE_EMI")
    private Long N_TOTAL_BALANCE_EMI;

    @Column(name="N_TOTAL_LIVE_CASES_FOR_HIRE")
    private Long N_TOTAL_LIVE_CASES_FOR_HIRE;

    @Column(name="N_FULL_COST_PAID")
    private Long N_FULL_COST_PAID;

    @Column(name="N_TOTAL_NO_OF_SALE_DEED_ISSUED")
    private Long N_TOTAL_NO_OF_SALE_DEED_ISSUED;

    @Column(name="N_TOTAL_RIPPED_UNIT")
    private Long N_TOTAL_RIPPED_UNIT;


    // --------------------------- EXTRA -----------------------------


    @Column(name="N_NO_OF_HIG_UNITS")
    private Long N_NO_OF_HIG_UNITS;

    @Column(name="N_NO_OF_MIG_UNITS")
    private Long N_NO_OF_MIG_UNITS;

    @Column(name="N_NO_OF_LIG_UNITS")
    private Long N_NO_OF_LIG_UNITS;

    @Column(name="N_NO_OF_EWS_UNITS")
    private Long N_NO_OF_EWS_UNITS;

    @Column(name="N_TOTAL_NO_OF_RESIDENTIAL_UNITS")
    private Long N_TOTAL_NO_OF_RESIDENTIAL_UNITS;

    @Column(name="N_TOTAL_NO_OF_COMMERCIAL_UNITS")
    private Long N_TOTAL_NO_OF_COMMERCIAL_UNITS;

    @Column(name="N_NO_OF_OUTRIGHT_UNITS")
    private Long N_NO_OF_OUTRIGHT_UNITS;

    @Column(name="N_NO_OF_HIREPURCHASE_UNITS")
    private Long N_NO_OF_HIREPURCHASE_UNITS;

    @Column(name="N_NO_OF_SFS_UNITS")
    private Long N_NO_OF_SFS_UNITS;

    @Column(name="N_RATE_ADOPTED")
    private Long N_RATE_ADOPTED;

    @Formula("(N_NO_OF_HIG_UNITS+N_NO_OF_MIG_UNITS+N_NO_OF_LIG_UNITS+N_NO_OF_EWS_UNITS)")
    @Column(name="N_TOTAL_DEVELOPED_UNITS")
    private Long N_TOTAL_DEVELOPED_UNITS;

    @Column(name="N_TOTAL_ALLOTTED_UNITS_FOR_RESIDENTIAL")
    private Long N_TOTAL_ALLOTTED_UNITS_FOR_RESIDENTIAL;

    @Column(name="N_TOTAL_ALLOTTED_UNITS_FOR_COMMERCIAL")
    private Long N_TOTAL_ALLOTTED_UNITS_FOR_COMMERCIAL;

    @Column(name="N_TOTAL_NO_OF_PAID_CASES")
    private Long N_TOTAL_NO_OF_PAID_CASES;

    @Column(name="N_TOTAL_ALLOTTED_UNITS_FOR_HIG")
    private Long N_TOTAL_ALLOTTED_UNITS_FOR_HIG;

    @Column(name="N_TOTAL_ALLOTTED_UNITS_FOR_MIG")
    private Long N_TOTAL_ALLOTTED_UNITS_FOR_MIG;

    @Column(name="N_TOTAL_ALLOTTED_UNITS_FOR_LIG")
    private Long N_TOTAL_ALLOTTED_UNITS_FOR_LIG;

    @Column(name="N_TOTAL_ALLOTTED_UNITS_FOR_EWS")
    private Long N_TOTAL_ALLOTTED_UNITS_FOR_EWS;

    public void updateTotalDevelopedUnits(){
        this.N_TOTAL_DEVELOPED_UNITS = (N_NO_OF_HIG_UNITS + N_NO_OF_MIG_UNITS + N_NO_OF_LIG_UNITS + N_NO_OF_EWS_UNITS);
    }

    @Transient
    private String mode;

    @Transient
    private List<Allottee> AllotteeList;

    @Transient
    private List<UnitData> unitData;

    //New field Added

    @Column(name="V_CR_CODE")
    private String V_CR_CODE;

    @Column(name="V_CC_CODE")
    private String V_CC_CODE;

    @Column(name="V_D_CODE")
    private String V_D_CODE;

    @Column(name="V_FLAT_TYPE")
    private String V_FLAT_TYPE;

    @Column(name="V_FLAT_NO")
    private String V_FLAT_NO;

    @Column(name="V_SIZE_OF_FLAT")
    private String V_SIZE_OF_FLAT;

    @Column(name="V_COST_OF_FLAT")
    private String V_COST_OF_FLAT;

    //Category wise reservation



    @Column(name="V_RESERVATION_PERIOD")
    private String V_RESERVATION_PERIOD;

    @Column(name="V_SCHEDULED_CASTE")
    private Long V_SCHEDULED_CASTE;

    @Column(name="V_ARTISTS")
    private Long V_ARTISTS;

    @Column(name="V_POLITICAL_SUFFERERS")
    private Long V_POLITICAL_SUFFERERS;

    @Column(name="V_PHYSICALLY_CHALLENGED")
    private Long V_PHYSICALLY_CHALLENGED;

    @Column(name="V_SCHEDULED_TRIBES")
    private Long V_SCHEDULED_TRIBES;

    @Column(name="V_ST_PHYSICALLY_CHALLENGED")
    private Long V_ST_PHYSICALLY_CHALLENGED;

    @Column(name="V_STATE_GOVERNMENT")
    private Long V_STATE_GOVERNMENT;

    @Column(name="V_STATE_GOVT_PHYSICALLY_CHALLENGED")
    private Long V_STATE_GOVT_PHYSICALLY_CHALLENGED;

    @Column(name="V_STATE_GOVT_JUDICIAL_OFFICERS")
    private Long V_STATE_GOVT_JUDICIAL_OFFICERS;

    @Column(name="V_CENTRAL_TNEB_LOCAL_BODIES")
    private Long V_CENTRAL_TNEB_LOCAL_BODIES;

    @Column(name="V_CENTRAL_GOVT_PHYSICALLY_CHALLENGED")
    private Long V_CENTRAL_GOVT_PHYSICALLY_CHALLENGED;

    @Column(name="V_DEFENCE")
    private Long V_DEFENCE;

    @Column(name="V_AWARDEES")
    private Long V_AWARDEES;

    @Column(name="V_DEFENCE_PHYSICALLY_CHALLENGED")
    private Long V_DEFENCE_PHYSICALLY_CHALLENGED;

    @Column(name="V_DHOBBIES_BARBERS")
    private Long V_DHOBBIES_BARBERS;

    @Column(name="V_DHOBBIES_ARTISTS")
    private Long V_DHOBBIES_ARTISTS;

    @Column(name="V_DHOBBIES_PHYSICALLY_CHALLENGED")
    private Long V_DHOBBIES_PHYSICALLY_CHALLENGED;

    @Column(name="V_WORKING_JOURNALIST")
    private Long V_WORKING_JOURNALIST;

    @Column(name="V_JOURNALIST_PHYSICALLY_CHALLENGED")
    private Long V_JOURNALIST_PHYSICALLY_CHALLENGED;

    @Column(name="V_LANGUAGE_CRUSADERS")
    private Long V_LANGUAGE_CRUSADERS;

    @Column(name="V_LANGUAGE_PHYSICALLY_CHALLENGED")
    private Long V_LANGUAGE_PHYSICALLY_CHALLENGED;

    @Column(name="V_TNHB_EMPLOYEES")
    private Long V_TNHB_EMPLOYEES;

    @Column(name="V_TNHB_PHYSICALLY_CHALLENGED")
    private Long V_TNHB_PHYSICALLY_CHALLENGED;

    @Column(name="V_GENERAL_PUBLIC")
    private Long V_GENERAL_PUBLIC;

    @Column(name="V_GENERAL_PUBLIC_ARTISTS")
    private Long V_GENERAL_PUBLIC_ARTISTS;

    @Column(name="V_GENERAL_PUBLIC_POLITICAL_SUFFERERS")
    private Long V_GENERAL_PUBLIC_POLITICAL_SUFFERERS;

    @Column(name="V_GENERAL_PUBLIC_PHYSICALLY_CHALLENGED")
    private Long V_GENERAL_PUBLIC_PHYSICALLY_CHALLENGED;

    //Category wise Unsold Units

    @Column(name="V_SCHEDULED_CASTE_UNSOLD_UNITS")
    private Long V_SCHEDULED_CASTE_UNSOLD_UNITS;

    @Column(name="V_ARTISTS_UNSOLD_UNITS")
    private Long V_ARTISTS_UNSOLD_UNITS;

    @Column(name="V_POLITICAL_SUFFERERS_UNSOLD_UNITS")
    private Long V_POLITICAL_SUFFERERS_UNSOLD_UNITS;

    @Column(name="V_PHYSICALLY_CHALLENGED_UNSOLD_UNITS")
    private Long V_PHYSICALLY_CHALLENGED_UNSOLD_UNITS;

    @Column(name="V_SCHEDULED_TRIBES_UNSOLD_UNITS")
    private Long V_SCHEDULED_TRIBES_UNSOLD_UNITS;

    @Column(name="V_ST_PHYSICALLY_CHALLENGED_UNSOLD_UNITS")
    private Long V_ST_PHYSICALLY_CHALLENGED_UNSOLD_UNITS;

    @Column(name="V_STATE_GOVERNMENT_UNSOLD_UNITS")
    private Long V_STATE_GOVERNMENT_UNSOLD_UNITS;

    @Column(name="V_STATE_GOVT_PHYSICALLY_CHALLENGED_UNSOLD_UNITS")
    private Long V_STATE_GOVT_PHYSICALLY_CHALLENGED_UNSOLD_UNITS;

    @Column(name="V_STATE_GOVT_JUDICIAL_OFFICERS_UNSOLD_UNITS")
    private Long V_STATE_GOVT_JUDICIAL_OFFICERS_UNSOLD_UNITS;

    @Column(name="V_CENTRAL_TNEB_LOCAL_BODIES_UNSOLD_UNITS")
    private Long V_CENTRAL_TNEB_LOCAL_BODIES_UNSOLD_UNITS;

    @Column(name="V_CENTRAL_GOVT_PHYSICALLY_CHALLENGED_UNSOLD_UNITS")
    private Long V_CENTRAL_GOVT_PHYSICALLY_CHALLENGED_UNSOLD_UNITS;

    @Column(name="V_DEFENCE_UNSOLD_UNITS")
    private Long V_DEFENCE_UNSOLD_UNITS;

    @Column(name="V_AWARDEES_UNSOLD_UNITS")
    private Long V_AWARDEES_UNSOLD_UNITS;

    @Column(name="V_DEFENCE_PHYSICALLY_CHALLENGED_UNSOLD_UNITS")
    private Long V_DEFENCE_PHYSICALLY_CHALLENGED_UNSOLD_UNITS;

    @Column(name="V_DHOBBIES_BARBERS_UNSOLD_UNITS")
    private Long V_DHOBBIES_BARBERS_UNSOLD_UNITS;

    @Column(name="V_DHOBBIES_ARTISTS_UNSOLD_UNITS")
    private Long V_DHOBBIES_ARTISTS_UNSOLD_UNITS;

    @Column(name="V_DHOBBIES_PHYSICALLY_CHALLENGED_UNSOLD_UNITS")
    private Long V_DHOBBIES_PHYSICALLY_CHALLENGED_UNSOLD_UNITS;

    @Column(name="V_WORKING_JOURNALIST_UNSOLD_UNITS")
    private Long V_WORKING_JOURNALIST_UNSOLD_UNITS;

    @Column(name="V_JOURNALIST_PHYSICALLY_CHALLENGED_UNSOLD_UNITS")
    private Long V_JOURNALIST_PHYSICALLY_CHALLENGED_UNSOLD_UNITS;

    @Column(name="V_LANGUAGE_CRUSADERS_UNSOLD_UNITS")
    private Long V_LANGUAGE_CRUSADERS_UNSOLD_UNITS;

    @Column(name="V_LANGUAGE_PHYSICALLY_CHALLENGED_UNSOLD_UNITS")
    private Long V_LANGUAGE_PHYSICALLY_CHALLENGED_UNSOLD_UNITS;

    @Column(name="V_TNHB_EMPLOYEES_UNSOLD_UNITS")
    private Long V_TNHB_EMPLOYEES_UNSOLD_UNITS;

    @Column(name="V_TNHB_PHYSICALLY_CHALLENGED_UNSOLD_UNITS")
    private Long V_TNHB_PHYSICALLY_CHALLENGED_UNSOLD_UNITS;

    @Column(name="V_GENERAL_PUBLIC_UNSOLD_UNITS")
    private Long V_GENERAL_PUBLIC_UNSOLD_UNITS;

    @Column(name="V_GENERAL_PUBLIC_ARTISTS_UNSOLD_UNITS")
    private Long V_GENERAL_PUBLIC_ARTISTS_UNSOLD_UNITS;

    @Column(name="V_GENERAL_PUBLIC_POLITICAL_SUFFERERS_UNSOLD_UNITS")
    private Long V_GENERAL_PUBLIC_POLITICAL_SUFFERERS_UNSOLD_UNITS;

    @Column(name="V_GENERAL_PUBLIC_PHYSICALLY_CHALLENGED_UNSOLD_UNITS")
    private Long V_GENERAL_PUBLIC_PHYSICALLY_CHALLENGED_UNSOLD_UNITS;

}