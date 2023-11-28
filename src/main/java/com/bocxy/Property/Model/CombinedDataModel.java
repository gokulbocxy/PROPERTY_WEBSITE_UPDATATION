package com.bocxy.Property.Model;

import lombok.Data;

@Data
public class CombinedDataModel {

    // AllotteeDetails columns
    private String allotteeDate;
    private String allotteeNo;

    // UnitData columns
    private String blockNo;
    private String carpetArea;
    private String floorNo;
    private String plotArea;
    private String typeName;
    private String udsArea;
    private String unitNo;
    private String assetCategory;
    private String assetSubCategory;
    private String assetType;
    private String plinthArea;

    // SchemeData columns
    private String finalLandCost;
    private String schemeName;

}
