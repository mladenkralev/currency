package com.egt.demo.demo.controller.api.statistics;

import com.egt.demo.demo.dao.RequestHistoryDAO;
import com.egt.demo.demo.model.RequestHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Implementing statistics for the access to the api
 * TODO probably should be protected with admin rights
 */
@RestController
@RequestMapping("api")
public class StatisticsRest {

    @Autowired
    private RequestHistoryDAO requestHistoryDAO;

    @GetMapping(value = {"statistics", "statistics/{name}"})
    public List<RequestHistory> getHistoryExchangeRate(@PathVariable(required = false) String name) {
        if (name == null) {
            return requestHistoryDAO.findAll();
        }
        return requestHistoryDAO.findAllByServiceName(name);
    }
}
