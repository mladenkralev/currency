package com.egt.demo.demo.scheduled.tasks.pull;

import com.egt.demo.demo.dao.CurrencyDateDAO;
import com.egt.demo.demo.model.Currency;
import com.egt.demo.demo.model.CurrencyDate;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Performs a sync with fixer api and persists it inside Database
 */
@Component
public class ExchangeRateScheduler {

    private static final Logger log = LoggerFactory.getLogger(ExchangeRateScheduler.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final String fixerUrl = "http://data.fixer.io/api/latest?access_key={access_key}";

    @Value("${fixer.key}")
    private String fixerUserKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CurrencyDateDAO currencyPesistence;

    @Scheduled(fixedRateString = "${pull.concurency.scheduler.occurrences.miliseconds}")
    public void reportCurrentTime() throws ParseException {
        log.info("Getting currency rates {}", dateFormat.format(new Date()));

        ResponseEntity<JsonNode> response
                = restTemplate.getForEntity(fixerUrl, JsonNode.class, fixerUserKey);
        JsonNode complexResponse = response.getBody();
        CurrencyDate currencyDate = new CurrencyDate();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Double> result = mapper.convertValue(
                Objects.requireNonNull(complexResponse, "Expecting response to have rates key").get("rates"),
                new TypeReference<Map<String, Double>>() {
                });

        List<Currency> currencies = result
                .entrySet()
                .stream()
                .map(e -> new Currency(e.getKey(), e.getValue(), currencyDate)).
                        collect(Collectors.toList());

        currencyDate.setRates(currencies);
        long date = complexResponse.get("timestamp").asLong();
        currencyDate.setDate(date);

        log.info("Persisting the data {} at {}", currencyDate, dateFormat.format(new Date()));
        currencyPesistence.save(currencyDate);
    }
}
