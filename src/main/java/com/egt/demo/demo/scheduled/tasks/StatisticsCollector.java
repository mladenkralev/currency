package com.egt.demo.demo.scheduled.tasks;

import com.egt.demo.demo.dao.RequestHistoryDAO;
import com.egt.demo.demo.model.RequestHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Used to report periodically statistics to cloudamqp
 */
@Component
public class StatisticsCollector {

    Logger logger = LoggerFactory.getLogger(StatisticsCollector.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RequestHistoryDAO requestHistoryDAO;

    @Scheduled(fixedRateString = "${scheduler.statistics.miliseconds}")
    public void sendStatistics() {
        List<RequestHistory> requestHistoryList = requestHistoryDAO.findAll();
        logger.info("Sending statistics to Rabbit cloud instance");
        rabbitTemplate.convertAndSend(MessagingApplication.EXCHANGE_NAME, MessagingApplication.ROUTING_KEY, requestHistoryList);
    }
}
