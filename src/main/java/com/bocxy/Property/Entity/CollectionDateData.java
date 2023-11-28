package com.bocxy.Property.Entity;

import javax.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "CollectionDateData")
public class CollectionDateData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "N_ID")
    private Long N_ID;

    @Column(name = "NUNITID")
    private Long NUNITID;

    @Column(name = "DATE")
    private LocalDate DATE;

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


    public void setV_TRANSACTION_NO(String transno) {
    }
}
