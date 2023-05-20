package com.sgwps.currencyrates;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sgwps.currencyrates.currency.CurrencyCode;
import com.sgwps.currencyrates.currency.CurrencyInfo;
import com.sgwps.currencyrates.currency.CurrencyPair;
import com.sgwps.currencyrates.datePeriod.DatePeriod;
import com.sgwps.currencyrates.datePeriod.DateRate;
import com.sgwps.currencyrates.models.RateDynamic;
import com.sgwps.currencyrates.parser.CurrencyRatesParser;

@SpringBootApplication
public class CurrencyRatesApplication {

	public static void main(String[] args) throws IOException {
		/*DatePeriod p1 = new DatePeriod();
		DatePeriod p2 = new DatePeriod();
		p2.setStart(LocalDate.of(2023, 5, 11));
		DatePeriod i = p2.Intersection(p1);
		DatePeriod i2 = p1.Intersection(p2);*/

		SpringApplication.run(CurrencyRatesApplication.class, args);
		
	}

}
