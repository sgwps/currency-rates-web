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

public class RateDynamic implements Iterable<DateRate> {
    @Getter
    CurrencyPair pair;
    @Getter
    DatePeriod period;
    @Getter
    ArrayList<Double> rates;

    public int testValue = 1;


    private void setPair(CurrencyPair pair) throws MalformedURLException, IOException {
        this.pair = pair;
        RatesArrayCreator arrayCreator = new RatesArrayCreator(pair);
        rates = arrayCreator.getRatesOfRange(period);

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


    private void updatePeriod(DatePeriod period) throws MalformedURLException, IOException {
        LocalDate minDate = pair.getMinDate();
        if (period.getStartDate().isBefore(minDate)) {
            throw new IllegalArgumentException("Date is to weak");
        }

        RatesArrayCreator arrayCreator = new RatesArrayCreator(pair);
        rates = arrayCreator.update(this.period, period, rates);
        this.period = period;

    }

    private void setCurrencyAndPeriod(CurrencyPair pair, DatePeriod period) throws MalformedURLException, IOException {
        LocalDate minDate = pair.getMinDate();
        if (period.getStartDate().isBefore(minDate)) {
            throw new IllegalArgumentException("Date is to weak");
        }
        this.period = period;
        setPair(pair);
    }

    public RateDynamic(CurrencyPair pair, DatePeriod period) throws MalformedURLException, IOException {
        setCurrencyAndPeriod(pair, period);

    }

    public void ParseFullForm(RateRequestForm form) throws MalformedURLException, IOException {
        CurrencyPair pair = new CurrencyPair(CurrencyCode.valueOf(form.getCurrencyFrom()),
                CurrencyCode.valueOf(form.getCurrencyTo()));
        DatePeriod period = new DatePeriod(form.getStartDate(), form.getEndDate());
        setCurrencyAndPeriod(pair, period);
    }

    public RateDynamic() throws MalformedURLException, IOException {
        this(new CurrencyPair(CurrencyCode.USD, CurrencyCode.EUR), new DatePeriod());
    }

    public RateRequestForm getForm() {
        DatePeriod periodCopy = period.copy();
        return new RateRequestForm(periodCopy.getStartDate(), periodCopy.getEndDate(), pair.getCurrencyFrom().toString(),
                pair.getCurrencyTo().toString());
    }

    public void procceedForm(RateRequestForm form) throws MalformedURLException, IOException {
        CurrencyPair oldPair = this.pair.copy();
        DatePeriod oldPeriod = this.period.copy();
        ArrayList<Double> rates = (ArrayList<Double>) this.rates.clone();
        try {
            if (CurrencyCode.valueOf(form.getCurrencyFrom()) != this.pair.getCurrencyFrom()
                    || CurrencyCode.valueOf(form.getCurrencyTo()) != this.pair.getCurrencyTo()) {
                ParseFullForm(form);
            } else {
                DatePeriod newPeriod = new DatePeriod(form.getStartDate(), form.getEndDate());
                updatePeriod(newPeriod);
            }
        } catch (IllegalArgumentException e) {
            this.pair = oldPair;
            this.period = oldPeriod;
            this.rates = rates;
            throw new IllegalArgumentException();

        }
    }

    public ArrayList<DateRate> getRatesArray() {
        ArrayList<DateRate> result = new ArrayList<>();
        for (DateRate rate : this) {
            result.add(rate);
        }
        return result;
    }

    @Override
    public Iterator<DateRate> iterator() {
        return new Iterator<DateRate>() {

            Iterator<LocalDate> periodIterator;

            {
                periodIterator = period.iterator();
            }
            

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
