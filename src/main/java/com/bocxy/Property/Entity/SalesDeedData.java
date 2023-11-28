package com.bocxy.Property.Entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "SalesDeedData")
public class SalesDeedData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "N_ID")
    private Long NID;

    @Column(name="nUnitId")
    private Long nUnitId;

    @Column(name="nSchemeId")
    private Long nSchemeId;

    @Column(name="nAllotteeId")
    private Long NAllotteeId;

    @Column(name = "Allotment_Order_FileName")
    private String AllotmentOrderFileName;

    @Column(name = "Allotment_Order_FilePath")
    private String AllotmentOrderFilePath;

    @Column(name = "LCS_FileName")
    private String LCSFileName;

    @Column(name = "LCS_FilePath")
    private String LCSFilePath;

    @Column(name = "Handing_Report_FileName")
    private String HandingReportFileName;

    @Column(name = "Handing_Report_FilePath")
    private String HandingReportFilePath;

    @Column(name = "Field_Book_FileName")
    private String FieldBookFileName;

    @Column(name = "Field_Book_FilePath")
    private String FieldBookFilePath;

    @Column(name = "Loan_FileName")
    private String LoanFileName;

    @Column(name = "Loan_FilePath")
    private String LoanFilePath;

    @Column(name ="Sale_Deed_FileName")
    private String SaleDeedFileName;

    @Column(name="Sale_Deed_FilePath")
    private  String SaleDeedFilePath;

    @Transient
    private String AllotmentOrderFile;

    @Transient
    private String LCSFile;

    @Transient
    private String HandingReportFile;

    @Transient
    private String FieldBookFile;

    @Transient
    private String LoanFile;

    @Transient
    private String SaleDeedFile;

}
