package com.bocxy.Property.Model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BalanceRequestModel {

    private Long n_UNIT_ID;
    private LocalDate MONTH;
}
