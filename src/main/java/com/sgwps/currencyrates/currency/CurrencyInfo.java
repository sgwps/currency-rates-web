package com.sgwps.currencyrates.currency;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;


public class CurrencyInfo {
    CurrencyCode currencyCode;
    static final String path = "currs.json";

    private JsonObject getJson() {
        try {
            JsonReader jsonReader = Json.createReader(new FileInputStream(path));
            JsonObject object = jsonReader.readObject();
            return object.getJsonObject(currencyCode.toString());
        } catch (FileNotFoundException e) {
            return null;
        }

    }

    public ArrayList<String> getCountriesList() {
        ArrayList<String> countries;
        countries = new ArrayList<>();
        JsonObject json = getJson();
        json.getJsonArray("countries").forEach(country -> countries.add(((JsonString) country).getString()));
        return countries;
    }

    public LocalDate getMinRequestDate() throws FileNotFoundException {
        JsonValue json = getJson().get("ratesHistoryStart");
        return LocalDate.parse(((JsonString) json).getString());
    }

    public CurrencyInfo(CurrencyCode code) {
        this.currencyCode = code;
    }

    public String getFullName() {
        return getCurrencyInstance().getDisplayName();
    }

    public Currency getCurrencyInstance() {
        return Currency.getInstance(currencyCode.toString());
    }

    public String getSymbol() {
        return getCurrencyInstance().getSymbol();
    }

}
