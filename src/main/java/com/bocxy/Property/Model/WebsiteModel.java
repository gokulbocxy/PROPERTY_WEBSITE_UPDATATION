package com.bocxy.Property.Model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
public class WebsiteModel {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long N_ID;
    private String V_SCHEME_NAME;
    private String V_DIVISION;

    private Long N_NO_OF_HIG_UNITS;

    private Long N_NO_OF_MIG_UNITS;

    private Long N_NO_OF_LIG_UNITS;

    private Long N_NO_OF_EWS_UNITS;

    private Long N_TOTAL_UNSOLD_UNITS;

    private Long N_SCHEME_ID;

    private String V_UNIT_ALLOTTED_STATUS;

    private String F_PHOTO;

    private String V_UNIT_TYPE;

    private String vPocMobile;

    private String vPocName;

    private String fPocPicture;

    private String vPocEmail;

    private String fVideo;

    private String fFloorPlanPicture;

    private String vGeoTagLink;

    private String vEmail;








}
