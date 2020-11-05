package com.egt.demo.demo.controller.api.xml;

import com.egt.demo.demo.controller.api.xml.contract.GetHistoryExchangeRateRequest;
import com.egt.demo.demo.controller.api.xml.contract.GetCurrentExchangeRateRequest;
import com.egt.demo.demo.controller.exceptions.DublicateCustomerRequestFound;
import com.egt.demo.demo.dao.CurrencyDateDAO;
import com.egt.demo.demo.model.CurrencyDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("api/xml")
@RestController
public class CurrencyXML {

    @Autowired
    CurrencyDateDAO currencyPersistence;

    @PostMapping(value = "current", consumes = MediaType.APPLICATION_XML_VALUE)
    public Object getCurrentExchangeRate(@RequestBody GetCurrentExchangeRateRequest clientRequest) throws DublicateCustomerRequestFound {
        return currencyPersistence.findAllByOrderByDateAsc().stream().findFirst().orElse(new CurrencyDate());
    }

    @PostMapping("history")
    public Object getHistoryExchangeRate(@RequestBody GetHistoryExchangeRateRequest historyXmlRequest) throws DublicateCustomerRequestFound {
        int period = historyXmlRequest.getHistoryTag().getPeriod();
        LocalDateTime endPeriod = LocalDateTime.now();
        LocalDateTime startPeriod= LocalDateTime.now().minusHours(period);

        return currencyPersistence.findByDateBetween(startPeriod, endPeriod);
    }
}
