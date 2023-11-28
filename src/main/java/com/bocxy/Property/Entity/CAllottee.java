package com.bocxy.Property.Entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "CAllotee")
public class CAllottee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "N_ID")
    private Long N_ID;

    @Column(name = "CUSTOMER_ID")
    private Long customerid;

    @Column(name = "APPLICATION_NO", length = 50)
    private String applicationNo;

    @Column(name = "DATE", length = 50)
    private String date;

    @Column(name = "UNIT_ACCOUNT_NO", length = 50)
    private String unitAccountNo;

    @Column(name = "UNIT_TYPE", length = 50)
    private String unitType;

    @Column(name = "MODE_OF_ALLOTMENT", length = 50)
    private String modeOfAllotment;

    @Column(name = "CITY_RURAL", length = 50)
    private String cityRural;

    @Column(name = "CIRCLE", length = 50)
    private String circle;

    @Column(name = "SCHEME", length = 50)
    private String scheme;

    @Column(name = "TYPE", length = 50)
    private String type;

    @Column(name = "BLOCK_NO", length = 50)
    private String blockNo;

    @Column(name = "FLOOR_NO", length = 50)
    private String floorNo;

    @Column(name = "FLAT_NO", length = 50)
    private String flatNo;

    @Column(name = "UNIT_NO", length = 50)
    private String unitNo;

    @Column(name = "PLOT_UDS_AREA", length = 50)
    private String plotUdsArea;

    @Column(name = "PLINTH_AREA", length = 50)
    private String plinthArea;

    @Column(name = "RESERVATION_CATEGORY", length = 50)
    private String reservationCategory;

    @Column(name = "PRIORITY_BASIS", length = 50)
    private String priorityBasis;

    @Column(name = "COST_OF_UNIT", length = 50)
    private double costOfUnit;

    @Column(name = "DIVISION", length = 50)
    private String division;

    @Column(name = "RESERVATION_OR_NON_RESERVATION", length = 50)
    private String reservationOrNonReservation;

    @Column(name = "STATUS", length = 50)
    private String status;

    @Column(name = "APPLICANT_NAME", length = 50)
    private String applicantName;

    @Column(name = "DATE_OF_BIRTH", length = 50)
    private String dateOfBirth;

    @Column(name = "AGE")
    private int age;

    @Column(name = "APPLICANT_SPOUSE_NAME", length = 50)
    private String applicantSpouseName;

    @Column(name = "APPLICANT_FATHERS_NAME", length = 50)
    private String applicantFathersName;

    @Column(name = "JOINT_APPLICANT_NAME", length = 50)
    private String jointApplicantName;

    @Column(name = "JOINT_APPLICANT_DOB", length = 50)
    private String jointApplicantDOB;

    @Column(name = "JOINT_APPLICANT_AGE")
    private int jointApplicantAge;

    @Column(name = "JOINT_APPLICANT_SPOUSE_NAME", length = 50)
    private String jointApplicantSpouseName;

    @Column(name = "JOINT_APPLICANT_FATHERname", length = 50)
    private String jointApplicantFathername;

    @Column(name = "MOBILE_NUMBER", length = 50)
    private String mobileNumber;

    @Column(name = "EMAIL_ID", length = 50)
    private String emailId;

    @Column(name = "AADHAAR_NUMBER", length = 50)
    private String aadhaarNumber;

    @Column(name = "PAN_NUMBER", length = 50)
    private String panNumber;

    @Column(name = "CORRESPONDENCE_ADDRESS")
    private String correspondenceAddress;

    @Column(name = "PERMANENT_ADDRESS", length = 50)
    private String permanentAddress;

    @Column(name = "APPLICANT_MONTHLY_INCOME", length = 50)
    private double applicantMonthlyIncome;

    @Column(name = "SPOUSE_MONTHLY_INCOME", length = 50)
    private double spouseMonthlyIncome;

    @Column(name = "TOTAL_MONTHLY_INCOME", length = 50)
    private double totalMonthlyIncome;

    @Column(name = "BANK_NAME", length = 50)
    private String bankName;

    @Column(name = "ACCOUNT_NUMBER")
    private Long accountNumber;

    @Column(name = "IFSC_CODE", length = 50)
    private String ifscCode;

    @Column(name = "ACCOUNT_HOLDER_NAME", length = 50)
    private String accountHolderName;

    @Column(name = "APPLICATION_FEE", length = 50)
    private double applicationFee;

    @Column(name = "REGISTRATION_FEE", length = 50)
    private double registrationFee;

    @Column(name = "Amount_Paid", length = 50)
    private double amountPaid;

    @Column(name = "UNIT_N_ID")
    private Long unitNId;

    @Column(name = "SCHEME_N_ID")
    private Long schemeNId;

    @Column(name = "CATEGORY_CODE", length = 50)
    private Long categoryCode;

    @Column(name = "SUB_CATEGORY_CODE", length = 50)
    private Long subCategoryCode;

    @Transient
    private String nativeOfTamilnadu;

    @Transient
    private String birthCertificate;

    @Transient
    private String aadhaarProof;

    @Transient
    private String panProof;

    @Transient
    private String incomeCertificate;

    @Transient
    private String reservationCategoryProof;

    @Transient
    private String reservationSubCategoryProof;

    @Transient
    private String signature;

    @Transient
    private String jointApplSign;

    @Transient
    private String photo;

    @Column(name = "NATIVE_OF_TAMILNADU_FILEPATH", length = 255)
    private String nativeOfTamilnadu_filepath;

    @Column(name = "BIRTH_CERTIFICATE_FILEPATH", length = 255)
    private String birthCertificate_filepath;

    @Column(name = "AADHAAR_PROOF_FILEPATH", length = 255)
    private String aadhaarProof_filepath;

    @Column(name = "PAN_PROOF_FILEPATH", length = 255)
    private String panProof_filepath;

    @Column(name = "INCOME_CERTIFICATE_FILEPATH", length = 255)
    private String incomeCertificate_filepath;

    @Column(name = "RESERVATION_CATEGORY_PROOF_FILEPATH", length = 255)
    private String reservationCategoryProof_filepath;

    @Column(name = "RESERVATION_SUB_CATEGORY_PROOF_FILEPATH", length = 255)
    private String reservationSubCategoryProof_filepath;

    @Column(name = "SIGNATURE_FILEPATH", length = 255)
    private String signature_filepath;

    @Column(name = "JOINT_APPL_SIGN_FILEPATH", length = 255)
    private String jointApplSignFilePath;

    @Column(name = "PHOTO_FILEPATH", length = 255)
    private String photo_filepath;

    @Column(name = "APPLICATION_UPLOAD_FILEPATH", length = 255)
    private String application_upload_filepath;

    @Transient
    private String ALLOTMENT_ORDER;

    @Column(name = "ALLOTMENT_ORDER_FILENAME", length = 50)
    private String allotment_order_filename;

    @Column(name = "ALLOTMENT_ORDER_FILEPATH", length = 255)
    private String allotment_order_filepath;

    @Transient
    private String LCS_Agreement;

    @Column(name = "LCS_AGREEMENT_FILENAME", length = 50)
    private String lcs_agreement_filename;

    @Column(name = "LCS_AGREEMENT_FILEPATH", length = 255)
    private String lcs_agreement_filepath;

    @Transient
    private String FIELD_MEASUREMENT_BOOK;

    @Column(name = "FIELD_MEASUREMENT_BOOK_FILENAME", length = 50)
    private String field_measurement_book_filename;

    @Column(name = "FIELD_MEASUREMENT_BOOK_FILEPATH", length = 255)
    private String field_measurement_book_filepath;

    @Transient
    private String HANDING_OVER_REPORT;

    @Column(name = "HANDING_OVER_REPORT_FILENAME", length = 50)
    private String handing_over_report_filename;

    @Column(name = "HANDING_OVER_REPORT_FILEPATH", length = 255)
    private String handing_over_report_filepath;

    @Transient
    private String DRAFT_SALE_DEED;

    @Column(name = "DRAFT_SALE_DEED_FILENAME", length = 50)
    private String draft_sale_deed_filename;

    @Column(name = "DRAFT_SALE_DEED_FILEPATH", length = 255)
    private String draft_sale_deed_filepath;

    @Transient
    private String A_B_LOAN;

    @Column(name = "A_B_Loan_FILENAME", length = 50)
    private String a_b_loan_filename;

    @Column(name = "A_B_Loan_FILEPATH", length = 255)
    private String a_b_loan_filepath;

    @Transient
    private String IN_PRINCIPAL_LETTER_FOR_LOAN;

    @Column(name = "IN_PRINCIPAL_LETTER_FOR_LOAN_FILENAME", length = 50)
    private String inPrincipalLetterForLoanFilename;

    @Column(name = "IN_PRINCIPAL_LETTER_FOR_LOAN_FILEPATH", length = 255)
    private String inPrincipalLetterForLoanFilepath;

    @Transient
    private String NOC;

    @Column(name = "NOC_FILENAME", length = 50)
    private String nocFilename;

    @Column(name = "NOC_FILEPATH", length = 255)
    private String nocFilepath;

    @Transient
    private String SALE_DEED_REQUISITION_LETTER;

    @Column(name = "SALE_DEED_REQUISITION_LETTER_FILENAME", length = 50)
    private String saleDeedRequisitionLetterFilename;

    @Column(name = "SALE_DEED_REQUISITION_LETTER_FILEPATH", length = 200)
    private String saleDeedRequisitionLetterFilepath;}