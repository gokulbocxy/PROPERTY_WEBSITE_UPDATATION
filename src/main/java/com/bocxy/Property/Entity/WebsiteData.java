package com.bocxy.Property.Entity;


import javax.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="WebsiteData")
public class WebsiteData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "N_ID")
    private Long N_ID;

    @Column(name="nSchemeId")
    private Long nSchemeId;

    @ElementCollection
    @CollectionTable(name = "FPhotoCollection", joinColumns = @JoinColumn(name = "N_ID"))
    @Column(name = "fPhoto")
    private List<String> fPhoto;

    @Column(name="fVideo")
    private String fVideo;

    @Column(name="fFloorPlanPicture")
    private String fFloorPlanPicture;

    @Column(name="fFloorPlanPdf")
    private String fFloorPlanPdf;

    @Column(name="vGeoTagLink")
    private String vGeoTagLink;

    @Column(name="vProjectDescription")
    private String vProjectDescription;

    @Column(name="vAmenities")
    private String vAmenities;

    @Column(name="fPocPicture")
    private String fPocPicture;

    @Column(name="vPocName")
    private String vPocName;

    @Column(name="vPocMobile")
    private String vPocMobile;

    @Column(name="vPocEmail")
    private String vPocEmail;


    public Object get(String vUnitAllottedStatus) {
        return null;
    }
}