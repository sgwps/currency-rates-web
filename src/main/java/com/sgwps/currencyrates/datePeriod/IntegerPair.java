package com.sgwps.currencyrates.datePeriod;

import java.util.Iterator;
import java.util.stream.IntStream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class IntegerPair implements Iterable<Integer>{
    int firstVal;
    int secondVal;
    @Override
    public Iterator<Integer> iterator() {
        return IntStream.range(firstVal, secondVal + 1).iterator();
    }
}
