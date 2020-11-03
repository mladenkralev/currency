package com.egt.demo.demo.controller;

import com.egt.demo.demo.dao.CurrencyDateDAO;
import com.egt.demo.demo.model.CurrencyDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
public class CurrencyRest {

    @Autowired
    CurrencyDateDAO currencyPersistence;

    @GetMapping("current")
    public CurrencyDate getCurrentExchangeRate() {
        return currencyPersistence.findAllByOrderByDateAsc().stream().findFirst().orElse(new CurrencyDate());
    }

}
