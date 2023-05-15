package com.sgwps.currencyrates.datePeriod;

import java.time.LocalDate;
import java.util.Iterator;

public class PeriodIterator implements Iterator<LocalDate> {

    LocalDate current;
    LocalDate end;

    public PeriodIterator(DatePeriod period) {
        this.current = period.getStartDate().minusDays(1);
        this.end = period.getEndDate();
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
