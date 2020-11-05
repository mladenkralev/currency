package com.egt.demo.demo.controller.api.json;


import com.egt.demo.demo.controller.api.json.contract.ClientRequest;
import com.egt.demo.demo.controller.exceptions.DublicateCustomerRequestFound;
import com.egt.demo.demo.dao.CurrencyDateDAO;
import com.egt.demo.demo.model.CurrencyDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/json")
public class CurrencyRest {

    @Autowired
    CurrencyDateDAO currencyPersistence;

    @PostMapping("current")
    public Object getCurrentExchangeRate(@RequestBody ClientRequest clientRequest) throws DublicateCustomerRequestFound {
        return currencyPersistence.findAllByOrderByDateAsc().stream().findFirst().orElse(new CurrencyDate());
    }

    @PostMapping("history")
    public Object getHistoryExchangeRate(@RequestBody ClientRequest clientRequest) throws DublicateCustomerRequestFound {
        int period = clientRequest.getPeriod();
        LocalDateTime endPeriod = LocalDateTime.now();
        LocalDateTime startPeriod= LocalDateTime.now().minusHours(period);

        return currencyPersistence.findByDateBetween(startPeriod, endPeriod);
    }
}
