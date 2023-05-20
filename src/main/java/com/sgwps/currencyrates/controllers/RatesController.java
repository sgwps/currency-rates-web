package com.sgwps.currencyrates.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.ZoneOffset;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.sgwps.currencyrates.currency.CurrencyCode;
import com.sgwps.currencyrates.currency.CurrencyPair;
import com.sgwps.currencyrates.datePeriod.DatePeriod;
import com.sgwps.currencyrates.forms.RateRequestForm;
import com.sgwps.currencyrates.models.RateDynamic;

@Controller
@RequestMapping("/rates")
@SessionAttributes("rateDymnamic")
public class RatesController {

    @ModelAttribute("rateDymnamic")
    public RateDynamic rateDymnamic() throws MalformedURLException, IOException {
        return new RateDynamic(new CurrencyPair(CurrencyCode.USD, CurrencyCode.EUR), new DatePeriod());
    }

    @ModelAttribute("currentUTCDate")
    public LocalDate currentUTCDate() {
        return LocalDate.now(ZoneOffset.UTC);
    }

    @ModelAttribute("currencies")
    public CurrencyCode[] currencyCodes() {
        return CurrencyCode.values();
    }

    @GetMapping
    public String getHTML(Model model, @ModelAttribute("rateDymnamic") RateDynamic rate) {
        model.addAttribute("form", rate.getForm());
        return "rates";
    }

    @PostMapping
    public String resolvePostRequst(Model model, @ModelAttribute RateRequestForm form,
            @ModelAttribute("rateDymnamic") RateDynamic rate) throws MalformedURLException, IOException {
        try {

            rate.procceedForm(form);
            model.addAttribute("form", rate.getForm());
            return "rates";
        } catch (MalformedURLException | IllegalArgumentException e) {
            return "invalid_request";

        }
    }

}
