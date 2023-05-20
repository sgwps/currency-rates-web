package com.sgwps.currencyrates.datePeriod;

import java.time.LocalDate;
import java.util.Iterator;

public class PeriodIterator implements Iterator<LocalDate> {

    LocalDate current;
    LocalDate end;

    public PeriodIterator(DatePeriod period) {
        DatePeriod copy = period.copy();
        this.current = copy.getStartDate().minusDays(1);
        this.end = copy.getEndDate();
    }

    @Override
    public boolean hasNext() {
        current = current.plusDays(1);
        return !current.isAfter(end);
    }

    @Override
    public LocalDate next() {

        return current;
    }
    
}
