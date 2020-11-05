package com.egt.demo.demo.controller.api.xml;

import com.egt.demo.demo.controller.api.xml.contract.GetCurrentExchangeRateRequest;
import com.egt.demo.demo.controller.api.xml.contract.GetHistoryExchangeRateRequest;
import com.egt.demo.demo.dao.CurrencyDAO;
import com.egt.demo.demo.dao.CurrencyDateDAO;
import com.egt.demo.demo.model.CurrencyDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("api/xml")
@RestController
public class CurrencyXML {

    @Autowired
    CurrencyDateDAO currencyPersistence;

    @Autowired
    CurrencyDAO currencyDAO;

    /**
     * Works with Post request with body:
     * <command id="1234">
     * 	  <get consumer="122233333333">
     * 		  <currency>EUR</currency>
     * 	  </get>
     * </command>
     */
    @PostMapping(value = "current", consumes = MediaType.APPLICATION_XML_VALUE)
    public Object getCurrentExchangeRate(@RequestBody GetCurrentExchangeRateRequest clientRequest) {
        CurrencyDate currencyDate = currencyPersistence.findAllByOrderByDateDesc().stream().findFirst()
                .orElse(new CurrencyDate());
        return currencyDAO.findByNameAndCurrencyDate_Id(clientRequest.getCommand().getCurrency().getCurrency(),
                currencyDate.getId());
    }

    /**
     * Works with Post request with body:
     * <command id="1234">
     * 	<history consumer="122233333" currency="EUR" period="24"/>
     * </command>
     */
    @PostMapping("history")
    public Object getHistoryExchangeRate(@RequestBody GetHistoryExchangeRateRequest historyXmlRequest) {
        int period = historyXmlRequest.getHistoryTag().getPeriod();
        LocalDateTime endPeriod = LocalDateTime.now();
        LocalDateTime startPeriod = LocalDateTime.now().minusHours(period);

        List<CurrencyDate> byDateBetween = currencyPersistence.findByDateBetween(startPeriod, endPeriod);
        return byDateBetween.stream()
                .map(date -> currencyDAO.findByNameAndCurrencyDate_Id(historyXmlRequest.getHistoryTag().getCurrency(),
                        date.getId()))
                .collect(Collectors.toList());
    }
}
