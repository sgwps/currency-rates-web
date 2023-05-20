package com.sgwps.currencyrates.parser;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;

import com.sgwps.currencyrates.currency.CurrencyPair;
import com.sgwps.currencyrates.datePeriod.DatePeriod;
import com.sgwps.currencyrates.datePeriod.IntegerPair;

import lombok.Getter;

public class RatesArrayCreator {
    @Getter
    CurrencyPair pair;

    public ArrayList<Double> getRatesOfRange(DatePeriod period) throws MalformedURLException, IOException {
        CurrencyRatesParser request = new CurrencyRatesParser(pair, period);
        return request.getRates();
    }

    public ArrayList<Double> getSubPeriod(LocalDate start, LocalDate end) throws MalformedURLException, IOException {
        try {
            DatePeriod prefix = new DatePeriod(start, end);
            return getRatesOfRange(prefix);
        }
        catch (IllegalArgumentException e) {
            return new ArrayList<>();
        }
    }

    public ArrayList<Double> update(DatePeriod oldPeriod, DatePeriod newPeriod, ArrayList<Double> currentArray) throws MalformedURLException, IOException {
        ArrayList<ArrayList<Double>> preResult = new ArrayList<>();
        preResult.add(getSubPeriod(newPeriod.getStartDate(), oldPeriod.getStartDate().minusDays(1)));
        IntegerPair intersection = oldPeriod.IntersectionIndexes(newPeriod);
        if (intersection.getFirstVal() != -1) {
            ArrayList<Double> intersectionArray = new ArrayList<>();
            for (int index : intersection) {
                intersectionArray.add(currentArray.get(index));
            }
            preResult.add(intersectionArray);
        }
        preResult.add(getSubPeriod(oldPeriod.getEndDate().plusDays(1), newPeriod.getEndDate()));
        ArrayList<Double> result = new ArrayList<>();
        for (ArrayList<Double> i : preResult) {
            for (double j : i) {
                result.add(j);
            }
        }
        return result;


    }


    public RatesArrayCreator(CurrencyPair pair) {
        this.pair = pair;
    }
}
