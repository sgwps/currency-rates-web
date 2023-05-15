package com.sgwps.currencyrates.datePeriod;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;

import lombok.Getter;

public class DatePeriod implements Iterable<LocalDate>{
    @Getter
    LocalDate startDate;
    @Getter
    LocalDate endDate;
    {
        startDate = LocalDate.now(ZoneOffset.UTC);
        endDate = LocalDate.now(ZoneOffset.UTC);
    }

    public void setStart(LocalDate value) {
        if (value.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");

        }
        startDate = value;

    }

    public void setEnd(LocalDate value) {
        if (value.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");

        }
        if (value.isAfter(LocalDate.now(ZoneOffset.UTC))) {
            throw new IllegalArgumentException("End date cannot be after current UTC date");

        }
        endDate = value;
    }

    @Override
    public Iterator<LocalDate> iterator() {
        return new PeriodIterator(this);
    }


    public int getLength() {
        return (int) startDate.until(endDate, ChronoUnit.DAYS) + 1;
    }

    public DatePeriod(){

    }

    public DatePeriod(LocalDate start, LocalDate end) {
        setStart(start);
        setEnd(end);
    }

    public DatePeriod Intersection(DatePeriod other) {
        LocalDate newStart = getStartDate().isBefore(other.getStartDate()) ? other.getStartDate() : getStartDate();
        LocalDate newEnd = getStartDate().isAfter(other.getStartDate()) ? other.getStartDate() : getStartDate();
        return new DatePeriod(newStart, newEnd);
    }


    public IntegerPair IntersectionIndexes(DatePeriod other) {
        try{
            DatePeriod intersection = this.Intersection(other);
            int firstVal = (int) getStartDate().until(intersection.getStartDate(), ChronoUnit.DAYS);
            int secondVal = (int) intersection.getEndDate().until(getEndDate(), ChronoUnit.DAYS);
            return new IntegerPair(firstVal, secondVal);

        } catch (IllegalArgumentException e) {
            return new IntegerPair(-1, -1);
        }
    }

    public int getIndex(LocalDate date) {
        return (int) getStartDate().until(date, ChronoUnit.DAYS);
    }
}
