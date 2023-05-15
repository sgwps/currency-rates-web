package com.sgwps.currencyrates.forms;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RateRequestForm {
    //public CurrencyCode currencyFrom;
    //public CurrencyCode currencyTo;
    @Setter
    @Getter
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate startDate;

    @Setter
    @Getter
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate endDate;


    @Getter
    @Setter
    public String currencyFrom;

    @Getter
    @Setter
    public String currencyTo;
}
