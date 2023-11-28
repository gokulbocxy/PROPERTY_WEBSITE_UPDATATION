package com.bocxy.Property.Entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "unitData")
public class UnitData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "N_ID")
    private Long N_ID;

    @Column(name = "N_SCHEME_ID")
    private Long N_SCHEME_ID;

    //New and confirmed Entries

    @Column(name = "V_CATEGORY", length = 45)
    private String V_CATEGORY;

    @Column(name = "V_ROAD_FACING", length = 45)
    private String V_ROAD_FACING;

    @Column(name = "V_CORNER_PLOT_STATUS", length = 45)
    private String V_CORNER_PLOT_STATUS;

    @Column(name = "V_UNIT_ALLOTTED_STATUS", length = 45)
    private String V_UNIT_ALLOTTED_STATUS;

    @Column(name = "V_UNIT_NO", length = 45)
    private String V_UNIT_NO;

    @Column(name = "V_BLOCK_NO", length = 45)
    private String V_BLOCK_NO;

    @Column(name = "V_FLOOR_NO", length = 45)
    private String V_FLOOR_NO;

    @Column(name = "V_UNIT_COST", length = 45)
    private String V_UNIT_COST;

    @Column(name = "V_UNIT_AC_NO", length = 45)
    private String V_UNIT_AC_NO;

    @Column(name = "V_RESERVATION_CATEGORY", length = 45)
    private String V_RESERVATION_CATEGORY;

    @Column(name = "V_PRIORITY", length = 45)
    private String V_PRIORITY;

    @Column(name = "V_INITIAL_DEPOSIT", length = 45)
    private String V_INITIAL_DEPOSIT;

    @Column(name = "V_EMI", length = 45)
    private String V_EMI;

    @Column(name = "V_EMI_START_DATE", length = 45)
    private String V_EMI_START_DATE;

    @Column(name = "GDQ", length = 45)
    private String GDQ;

    @Column(name = "V_MODE_OF_ALLOTMENT", length = 35)
    private String V_MODE_OF_ALLOTMENT;

    @Column(name = "V_DATE_OF_ALLOTMENT", length = 45)
    private String V_DATE_OF_ALLOTMENT;

    @Column(name = "V_FREEZED_DATE", length = 45)
    private String V_FREEZED_DATE;

    @Column(name = "V_UNIT_READY_DATE", length = 45)
    private String V_UNIT_READY_DATE;

    @Column(name = "V_ID_DUE_DATE_HP", length = 45)
    private String V_ID_DUE_DATE_HP;

    @Column(name = "V_SF_LOAN_SG", length = 45)
    private String V_SF_LOAN_SG;

    @Column(name = "V_AB_ISSUED_DATE", length = 45)
    private String V_AB_ISSUED_DATE;

    @Column(name = "V_LOAN_SANC_DATE", length = 45)
    private String V_LOAN_SANC_DATE;

    @Column(name = "V_UNIT_HANDING_OVER", length = 45)
    private String V_UNIT_HANDING_OVER;

    @Column(name = "V_PLOT_HANDING_OVER", length = 45)
    private String V_PLOT_HANDING_OVER;

    @Column(name = "V_ACTUAL_EXTENT", length = 45)
    private String V_ACTUAL_EXTENT;

    @Column(name = "V_DOOR_FACING", length = 45)
    private String V_DOOR_FACING;

    @Column(name = "V_LIVE_STATUS", length = 45)
    private String V_LIVE_STATUS;

    @Column(name = "V_RIPED_STATUS", length = 45)
    private String V_RIPED_STATUS;

    @Column(name = "V_FC_PAID_STATUS", length = 45)
    private String V_FC_PAID_STATUS;

    @Column(name = "V_DRAFT_DEED_ISSUED_ON", length = 45)
    private String V_DRAFT_DEED_ISSUED_ON;

    @Column(name = "V_FC_PAID_BUT_SALE_DEED_NOT_ISSUED")
    private String V_FC_PAID_BUT_SALE_DEED_NOT_ISSUED;

    @Column(name = "V_RIPED_BUT_COST_NOT_FULLY_PAID")
    private String V_RIPED_BUT_COST_NOT_FULLY_PAID;

    @Column(name = "V_SALE_DEED_STATUS", length = 45)
    private String V_SALE_DEED_STATUS;

    @Column(name = "V_SALE_DEED_DATE", length = 45)
    private String V_SALE_DEED_DATE;

    @Column(name = "V_MC_DEMAND", length = 45)
    private String V_MC_DEMAND;

    @Column(name = "MAINTENANCE_DATE", length = 45)
    private String MAINTENANCE_DATE;

    @Column(name = "V_CAR_DEMAND", length = 45)
    private String V_CAR_DEMAND;

    @Column(name = "V_CAR_SLOT_1", length = 45)
    private String V_CAR_SLOT_1;

    @Column(name = "V_CAR_SLOT_2", length = 45)
    private String V_CAR_SLOT_2;

    @Column(name = "V_PLINTH_AREA", length = 10)
    private String V_PLINTH_AREA;

    @Column(name = "V_GST_DEMAND", length = 45)
    private String V_GST_DEMAND;

    @Column(name = "V_DIFFERENCE_IN_COST_DUE_FROM", length = 45)
    private String V_DIFFERENCE_IN_COST_DUE_FROM;

    @Column(name = "V_DIFFERENCE_IN_COST", length = 10)
    private String V_DIFFERENCE_IN_COST;

    //SCHEDULE OF PAYMENT
    @Column(name = "V_1ST_AMOUNT_DUE", length = 45)
    private String V_1ST_AMOUNT_DUE;

    @Column(name = "V_1ST_AMOUNT_DUE_DATE", length = 45)
    private String V_1ST_AMOUNT_DUE_DATE;

    @Column(name = "V_2ST_AMOUNT_DUE", length = 45)
    private String V_2ST_AMOUNT_DUE;

    @Column(name = "V_2ND_AMOUNT_DUE_DATE", length = 45)
    private String V_2ND_AMOUNT_DUE_DATE;

    @Column(name = "V_3RD_AMOUNT_DUE", length = 45)
    private String V_3RD_AMOUNT_DUE;

    @Column(name = "V_3RD_AMOUNT_DUE_DATE", length = 45)
    private String V_3RD_AMOUNT_DUE_DATE;

    @Column(name = "V_4TH_AMOUNT_DUE", length = 45)
    private String V_4TH_AMOUNT_DUE;

    @Column(name = "V_4TH_AMOUNT_DUE_DATE", length = 45)
    private String V_4TH_AMOUNT_DUE_DATE;

    @Column(name = "V_5TH_AMOUNT_DUE", length = 45)
    private String V_5TH_AMOUNT_DUE;

    @Column(name = "V_5TH_AMOUNT_DUE_DATE", length = 45)
    private String V_5TH_AMOUNT_DUE_DATE;

    @Column(name = "V_6TH_AMOUNT_DUE", length = 45)
    private String V_6TH_AMOUNT_DUE;

    @Column(name = "V_6TH_AMOUNT_DUE_DATE", length = 45)
    private String V_6TH_AMOUNT_DUE_DATE;

    @Column(name = "V_7TH_AMOUNT_DUE", length = 45)
    private String V_7TH_AMOUNT_DUE;

    @Column(name = "V_7TH_AMOUNT_DUE_DATE", length = 45)
    private String V_7TH_AMOUNT_DUE_DATE;

    @Column(name = "V_8TH_AMOUNT_DUE", length = 45)
    private String V_8TH_AMOUNT_DUE;

    @Column(name = "V_8TH_AMOUNT_DUE_DATE", length = 45)
    private String V_8TH_AMOUNT_DUE_DATE;

    @Column(name = "V_9TH_AMOUNT_DUE", length = 45)
    private String V_9TH_AMOUNT_DUE;

    @Column(name = "V_9TH_AMOUNT_DUE_DATE", length = 45)
    private String V_9TH_AMOUNT_DUE_DATE;

    @Column(name = "V_10TH_AMOUNT_DUE", length = 45)
    private String V_10TH_AMOUNT_DUE;

    @Column(name = "V_10TH_AMOUNT_DUE_DATE", length = 45)
    private String V_10TH_AMOUNT_DUE_DATE;

    @Column(name = "V_11TH_AMOUNT_DUE", length = 45)
    private String V_11TH_AMOUNT_DUE;

    @Column(name = "V_11TH_AMOUNT_DUE_DATE", length = 45)
    private String V_11TH_AMOUNT_DUE_DATE;

    @Column(name = "V_12TH_AMOUNT_DUE", length = 45)
    private String V_12TH_AMOUNT_DUE;

    @Column(name = "V_12TH_AMOUNT_DUE_DATE", length = 45)
    private String V_12TH_AMOUNT_DUE_DATE;

    @Column(name = "V_13TH_AMOUNT_DUE", length = 45)
    private String V_13TH_AMOUNT_DUE;

    @Column(name = "V_13TH_AMOUNT_DUE_DATE", length = 45)
    private String V_13TH_AMOUNT_DUE_DATE;

    //SCHEDULE OF PROPERTY
    @Column(name = "V_ALLOTMENT_REF", length = 45)
    private String V_ALLOTMENT_REF;

    @Column(name = "V_CURRENT_FILE_REF", length = 45)
    private String V_CURRENT_FILE_REF;

    @Column(name = "V_TYPE_DESC", length = 45)
    private String V_TYPE_DESC;

    @Column(name = "V_SCHEME_PLACE", length = 45)
    private String V_SCHEME_PLACE;

    @Column(name = "TOTAL_COST", length = 45)
    private String TOTAL_COST;

    @Column(name = "TOTAL_COST_WORDS", length = 45)
    private String TOTAL_COST_WORDS;

    @Column(name = "LAND_COST", length = 45)
    private String LAND_COST;

    @Column(name = "LAND_COST_WORDS", length = 45)
    private String LAND_COST_WORDS;

    @Column(name = "BUILDING_COST", length = 45)
    private String BUILDING_COST;

    @Column(name = "BUILDING_COST_WORDS", length = 45)
    private String BUILDING_COST_WORDS;

    @Column(name = "EMD_RECEIPT_NO", length = 45)
    private String EMD_RECEIPT_NO;

    @Column(name = "EMD_PAID_DATE", length = 45)
    private String EMD_PAID_DATE;

    @Column(name = "EMD_PAID_AMOUNT", length = 45)
    private String EMD_PAID_AMOUNT;

    @Column(name = "DATE_OF_HANDLING", length = 45)
    private String DATE_OF_HANDLING;

    @Column(name = "BOARD_RESOLU_NO", length = 45)
    private String BOARD_RESOLU_NO;

    @Column(name = "BOARD_RESOLU_DATE", length = 45)
    private String BOARD_RESOLU_DATE;

    @Column(name = "GO_NO", length = 45)
    private String GO_NO;

    @Column(name = "GO_DATE", length = 45)
    private String GO_DATE;

    @Column(name = "SURVEY_NO", length = 60)
    private String SURVEY_NO;

    @Column(name = "NORTH_BOUNDARY", length = 45)
    private String NORTH_BOUNDARY;

    @Column(name = "EAST_BOUNDARY", length = 45)
    private String EAST_BOUNDARY;

    @Column(name = "WEST_BOUNDARY", length = 45)
    private String WEST_BOUNDARY;

    @Column(name = "SOUTH_BOUNDARY", length = 45)
    private String SOUTH_BOUNDARY;

    @Column(name = "NORTH_SCALING", length = 45)
    private String NORTH_SCALING;

    @Column(name = "EAST_SCALING", length = 45)
    private String EAST_SCALING;

    @Column(name = "WEST_SCALING", length = 45)
    private String WEST_SCALING;

    @Column(name = "SOUTH_SCALING", length = 45)
    private String SOUTH_SCALING;

    @Column(name = "SPLAY", length = 45)
    private String SPLAY;

    @Column(name = "VILLAGE", length = 45)
    private String VILLAGE;

    @Column(name = "TALUK", length = 45)
    private String TALUK;

    @Column(name = "DISTRICT", length = 45)
    private String DISTRICT;

    @Column(name = "SUB_REG_DESIG", length = 45)
    private String SUB_REG_DESIG;

    @Column(name = "SUB_REG_PLACE", length = 45)
    private String SUB_REG_PLACE;

    @Column(name = "LC_AGREE_DATE", length = 45)
    private String LC_AGREE_DATE;

    @Column(name = "PURPOSE_OF_DOC", length = 45)
    private String PURPOSE_OF_DOC;


    //old Entity
    @Column(name = "V_TYPE_NAME", length = 10)
    private String V_TYPE_NAME;

    @Column(name = "V_UNIT_ID", length = 10)
    private String V_UNIT_ID;

    @Column(name = "V_ASSET_CATEGORY", length = 10)
    private String V_ASSET_CATEGORY;

    @Column(name = "VAssetSubCategory", length = 10)
    private String VAssetSubCategory;

    @Column(name = "V_ASSET_TYPE", length = 10)
    private String V_ASSET_TYPE;

    @Column(name = "V_UDS_AREA", length = 10)
    private String V_UDS_AREA;

    @Column(name = "V_PLOT_AREA", length = 10)
    private String V_PLOT_AREA;

    @Column(name = "V_CARPET_AREA", length = 10)
    private String V_CARPET_AREA;

    @Column(name = "V_GOVT_DISCRETION_QUOTA", length = 10)
    private String V_GOVT_DISCRETION_QUOTA;

    @Column(name = "V_ALLOTMENT_TYPE", length = 10)
    private String V_ALLOTMENT_TYPE;

    @Column(name = "V_FLAT_NO", length = 10)
    private String V_FLAT_NO;





}