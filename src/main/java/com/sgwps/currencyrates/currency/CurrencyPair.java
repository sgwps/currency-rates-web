package com.sgwps.currencyrates.currency;

import java.io.FileNotFoundException;
import java.time.LocalDate;

import com.sgwps.currencyrates.datePeriod.DatePeriod;

import lombok.Getter;

public class CurrencyPair {
    @Getter
    CurrencyCode currencyFrom;
    @Getter
    CurrencyCode currencyTo;

    private void set(CurrencyCode currencyFrom, CurrencyCode currencyTo) {
        if (currencyFrom == currencyTo) {
            throw new IllegalArgumentException("Currencies must be different");
        } 
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
    }


    public void setTo(CurrencyCode currencyTo) {
        set(currencyFrom, currencyTo);
    }

    public void setFrom(CurrencyCode currencyFrom) {
        set(currencyFrom, currencyTo);
    }

    public void swap() {
        set(currencyTo, currencyFrom);
    }


    public CurrencyPair(CurrencyCode currencyFrom, CurrencyCode currencyTo) {
        set(currencyFrom, currencyTo);
    }


    public LocalDate getMinDate() throws FileNotFoundException {
        LocalDate minFrom = (new CurrencyInfo(currencyFrom)).getMinRequestDate();
        LocalDate minTo = (new CurrencyInfo(currencyTo)).getMinRequestDate();
        if (minFrom.isBefore(minTo)) {
            return minTo;
        }
            return minFrom;
    }

}
