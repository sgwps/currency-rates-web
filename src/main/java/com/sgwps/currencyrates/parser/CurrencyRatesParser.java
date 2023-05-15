package com.sgwps.currencyrates.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.stream.JsonParser;

import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

import com.sgwps.currencyrates.currency.CurrencyPair;
import com.sgwps.currencyrates.datePeriod.DatePeriod;

import jakarta.servlet.http.Cookie;


public class CurrencyRatesParser{
    final static String root = "https://api.exchangerate.host/timeseries?";
    CurrencyPair pair;
    DatePeriod period;

    //private ;

    public CurrencyRatesParser(CurrencyPair pair, DatePeriod period) {
        this.pair = pair;
        this.period = period;
    }

    private URI getUri() throws MalformedURLException{
        UriBuilder builder = (new  DefaultUriBuilderFactory()).uriString(root);
        builder.queryParam("start_date", period.getStartDate().toString());
        builder.queryParam("end_date", period.getEndDate().toString());
        builder.queryParam("base", pair.getCurrencyFrom().toString());
        builder.queryParam("symbols", pair.getCurrencyTo().toString());
        return builder.build();
    }

    public JsonValue getData() throws MalformedURLException, IOException {
        URI uri = getUri();
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setRequestMethod("GET");
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            JsonReader reader = Json.createReader(connection.getInputStream()); 
            JsonObject result = reader.readObject();    
            return result.get("rates");
        }
        return null;
    }

    public ArrayList<Double> getRates() throws MalformedURLException, IOException {
        ArrayList<Double> result = new ArrayList<>(period.getLength());
        JsonValue json = getData();
        for (LocalDate date : period) {
            result.add(json.asJsonObject().getJsonObject(date.toString()).getJsonNumber(pair.getCurrencyTo().toString()).doubleValue());
        }
        return result;
    }




}
