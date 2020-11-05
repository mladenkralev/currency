package com.egt.demo.demo.scheduled.tasks;

import com.egt.demo.demo.configuration.ApplicationConfiguration;
import com.egt.demo.demo.model.RequestHistory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Just to validate that something is passed and then it can be recieved
 */
@Service
public class RequestHistoryMessageListener {

    private final Logger logger = LoggerFactory.getLogger(StatisticsCollector.class);

    @RabbitListener(queues = ApplicationConfiguration.QUEUE_SPECIFIC_NAME)
    public void receiveMessage(final List<RequestHistory> requestHistory) {
        logger.info("Received message as specific class: {}", requestHistory);
    }
}
