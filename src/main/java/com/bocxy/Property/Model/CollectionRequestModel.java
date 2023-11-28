package com.bocxy.Property.Model;

import lombok.Data;
import java.time.LocalDate;
@Data
public class CollectionRequestModel {

    private Long n_UNIT_ID;
    private LocalDate MONTH;
    private String MONTHNYEAR;
    private String v_TRANSACTION_NO;
    private Double n_EMI_COL;
    private Double n_INITIAL_DEPOSIT_COL;
    private Double n_ARREAR_COL;
    private Double n_PENAL_INTEREST_COL;
    private Double n_INITIAL_DEPOSIT_PI_COL;
}
