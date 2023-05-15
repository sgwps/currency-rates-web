package com.sgwps.currencyrates.datePeriod;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
public class DateRate {
    LocalDate date;
    double value;

}
