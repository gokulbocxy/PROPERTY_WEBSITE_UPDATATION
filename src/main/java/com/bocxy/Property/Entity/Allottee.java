package com.bocxy.Property.Entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "allottee_details")
public class Allottee{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "N_ID")
    private Long NID;

    @Column(name="nSchemeId")
    private Long nSchemeId;

    @Column(name="nUnitId")
    private Long nUnitId;

    @Column(name = "V_UNIT_CODE")
    private String V_UNIT_CODE;

    @Column(name = "V_TYPE_NAME")
    private String V_TYPE_NAME;

    @Column(name = "V_ALLOTTEE_NO")
    private String V_ALLOTTEE_NO;

    @Column(name = "D_ALLOTTEE_DATE")
    private String D_ALLOTTEE_DATE;

    @Column(name = "V_ALLOTTEE_NAME")
    private String V_ALLOTTEE_NAME;

    @Column(name = "V_FATHER_SPOUSE_NAME")
    private String V_FATHER_SPOUSE_NAME;

    @Column(name = "N_PHONE_NUMBER")
    private Long N_PHONE_NUMBER;

    @Column(name = "V_EMAIL_ID")
    private String EMAILID;

    @Column(name = "N_AADHAAR_NO")
    private String N_AADHAAR_NO;

    @Column(name = "V_OTHER_DOC_NAME")
    private String V_OTHER_DOC_NAME;

    @Column(name = "V_OTHER_DOC_NUMBER")
    private String V_OTHER_DOC_NUMBER;

    @Column(name ="V_OTHER_FILE_NAME")
    private String V_OTHER_FILE_NAME;

    @Column(name="V_OTHER_FILE_PATH")
    private  String V_OTHER_FILE_PATH;

    @Column(name ="V_AADHAAR_FILE_NAME")
    private String V_AADHAAR_FILE_NAME;

    @Column(name="V_AADHAAR_FILE_PATH")
    private  String V_AADHAAR_FILE_PATH;

    @Column(name ="V_ALLOTTE_FILE_NAME")
    private String V_ALLOTTE_FILE_NAME;

    @Column(name="V_ALLOTTE_FILE_PATH")
    private  String V_ALLOTTE_FILE_PATH;

    @Column(name = "V_OTP")
    private String V_OTP;


    @Transient
    private String mode;

    @Transient
    private String V_AADHAAR_FILE;

    @Transient
    private String V_ALLOTTE_FILE;

    @Transient
    private String V_OTHER_FILE;
}
