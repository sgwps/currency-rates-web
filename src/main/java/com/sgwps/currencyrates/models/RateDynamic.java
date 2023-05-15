package com.sgwps.currencyrates.models;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

import com.sgwps.currencyrates.currency.CurrencyCode;
import com.sgwps.currencyrates.currency.CurrencyPair;
import com.sgwps.currencyrates.datePeriod.DatePeriod;
import com.sgwps.currencyrates.datePeriod.DateRate;
import com.sgwps.currencyrates.datePeriod.PeriodIterator;
import com.sgwps.currencyrates.forms.RateRequestForm;
import com.sgwps.currencyrates.parser.RatesArrayCreator;

import ch.qos.logback.classic.pattern.DateConverter;
import lombok.Getter;

public class RateDynamic implements Iterable<DateRate>{
    @Getter
    CurrencyPair pair;
    @Getter
    DatePeriod period;
    @Getter
    ArrayList<Double> rates;

    public int testValue = 1;

    


    public void setStartDate(LocalDate date) throws MalformedURLException, IOException {
        LocalDate minDate = pair.getMinDate();
        if (date.isBefore(minDate)) {
            throw new IllegalArgumentException("Date is to weak");
        }
        RatesArrayCreator arrayCreator = new RatesArrayCreator(pair);
        DatePeriod newPeriod = new DatePeriod(date, period.getEndDate());
        rates = arrayCreator.update(period, newPeriod, rates);
        period = newPeriod;
        //  period.setStart(minDate);
    }


    public void setEndDate(LocalDate date) throws MalformedURLException, IOException {
        LocalDate minDate = pair.getMinDate();
        if (date.isBefore(minDate)) {
            throw new IllegalArgumentException("Date is to weak");
        }
        RatesArrayCreator arrayCreator = new RatesArrayCreator(pair);
        DatePeriod newPeriod = new DatePeriod(period.getStartDate(), date);
        rates = arrayCreator.update(period, newPeriod, rates);
        period = newPeriod;
    }


    private void setPeriod(DatePeriod period) throws MalformedURLException, IOException {
        LocalDate minDate = pair.getMinDate();
        if (period.getStartDate().isBefore(minDate)) {
            throw new IllegalArgumentException("Date is to weak");
        }
        this.period = period;

        RatesArrayCreator arrayCreator = new RatesArrayCreator(pair);
        rates = arrayCreator.getRatesOfRange(period);
    }
    
    public RateDynamic(CurrencyPair pair, DatePeriod period) throws MalformedURLException, IOException {
        this.pair = pair;
        setPeriod(period);
        
    }


    public void ParseFullForm(RateRequestForm form) throws MalformedURLException, IOException {
        CurrencyPair pair = new CurrencyPair(CurrencyCode.valueOf(form.getCurrencyFrom()), CurrencyCode.valueOf(form.getCurrencyTo()));
        DatePeriod period = new DatePeriod(form.getStartDate(), form.getEndDate());
        this.pair = pair;
        setPeriod(period);
    }

    public RateDynamic() throws MalformedURLException, IOException{
        this(new CurrencyPair(CurrencyCode.USD, CurrencyCode.EUR), new DatePeriod());
    }

    public RateRequestForm getForm(){
        return new RateRequestForm(period.getStartDate(), period.getEndDate(), pair.getCurrencyFrom().toString(), pair.getCurrencyTo().toString());
    }

    public void procceedForm(RateRequestForm form) throws MalformedURLException, IOException{
        if (CurrencyCode.valueOf(form.getCurrencyFrom()) != this.pair.getCurrencyFrom() || CurrencyCode.valueOf(form.getCurrencyTo()) != this.pair.getCurrencyTo()) {
            ParseFullForm(form);
        }
        else {
            DatePeriod period = new DatePeriod(form.getStartDate(), form.getEndDate());
            setPeriod(period);
        }
    }


    @Override
    public Iterator<DateRate> iterator() {
        return new Iterator<DateRate>() {
            Iterator<LocalDate> periodIterator = period.iterator();

            @Override
            public boolean hasNext() {
                return periodIterator.hasNext();
            }

            @Override
            public DateRate next() {
                LocalDate date = periodIterator.next();
                int index = period.getIndex(date);
                return new DateRate(date, rates.get(index));
            }


        };
    }
   
    
}
