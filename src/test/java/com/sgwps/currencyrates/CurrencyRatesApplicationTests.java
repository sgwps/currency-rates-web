package com.sgwps.currencyrates;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.sgwps.currencyrates.currency.CurrencyCode;
import com.sgwps.currencyrates.currency.CurrencyPair;
import com.sgwps.currencyrates.datePeriod.DatePeriod;
import com.sgwps.currencyrates.models.RateDynamic;

@SpringBootTest
class CurrencyRatesApplicationTests {

	@Test
	void contextLoads() throws MalformedURLException, IOException {
		RateDynamic dynamic = new RateDynamic(new CurrencyPair(CurrencyCode.AUD, CurrencyCode.RUB), new DatePeriod());
		return;
	}

}
