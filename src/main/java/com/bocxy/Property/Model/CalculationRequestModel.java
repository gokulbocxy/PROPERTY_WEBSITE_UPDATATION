package com.bocxy.Property.Model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CalculationRequestModel {
    private Long N_UNIT_ID;
    private int V_PAYMENT_PERIOD_IN_YEARS;
    private int V_RATE_OF_INTEREST;
    private int V_PRINCIPAL_AMOUNT;
    private int N_INITIAL_DEPOSIT;
    private int N_INITIAL_DEPOSIT_PAID;
    private int N_INITIAL_DEPOSIT_TO_BE_PAID;
    private LocalDate EMI_START_DATE;
}
