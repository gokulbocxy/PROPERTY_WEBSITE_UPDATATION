package com.bocxy.Property.Entity;


import javax.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="Enquiry")
public class Enquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "N_ID")
    private Long N_ID;

    @Column(name="nSchemeId")
    private Long nSchemeId;

    @Column(name="vName")
    private String vName;

    @Column(name="vEmail")
    private String vEmail;

    @Column(name = "nContactNo")
    private String nContactNo;

    @Column (name = "vMessage")
    private String vMessage;


}
