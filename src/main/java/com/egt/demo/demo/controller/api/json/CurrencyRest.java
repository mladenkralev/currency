package com.egt.demo.demo.controller.api.json;

import com.egt.demo.demo.controller.api.json.contract.ClientRequest;
import com.egt.demo.demo.dao.CurrencyDAO;
import com.egt.demo.demo.dao.CurrencyDateDAO;
import com.egt.demo.demo.model.CurrencyDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/json")
public class CurrencyRest {

    @Autowired
    CurrencyDateDAO currencyPersistence;

    @Autowired
    CurrencyDAO currencyDAO;

    /**
     * Works with Post request with body
     * {
     * 	"requestId": "342162",
     * 	"timestamp": "123444",
     * 	"client": "1234",
     * 	"currency": "BGN"
     * }
     * @param clientRequest
     * @return
     */
    @PostMapping("current")
    public Object getCurrentExchangeRate(@RequestBody ClientRequest clientRequest) {
        CurrencyDate currencyDate = currencyPersistence.findAllByOrderByDateDesc().stream().findFirst().orElse(new CurrencyDate());
        return currencyDAO.findByNameAndCurrencyDate_Id(clientRequest.getCurrency(), currencyDate.getId());
    }

    /**
     * Works with Post request wit body
     *
     * {
     * 	"requestId": "34567812-3312223312121222",
     * 	"timestamp": "123444",
     * 	"client": "1234",
     * 	"currency": "BGN",
     * 	"period" : "10"
     * }
     * @param clientRequest
     * @return
     */
    @PostMapping("history")
    public Object getHistoryExchangeRate(@RequestBody ClientRequest clientRequest) {
        int period = clientRequest.getPeriod();
        LocalDateTime endPeriod = LocalDateTime.now();
        LocalDateTime startPeriod = LocalDateTime.now().minusHours(period);

        List<CurrencyDate> byDateBetween = currencyPersistence.findByDateBetween(startPeriod, endPeriod);
        return byDateBetween.stream()
                .map(date -> currencyDAO.findByNameAndCurrencyDate_Id(clientRequest.getCurrency(), date.getId()))
                .collect(Collectors.toList());
    }
}
