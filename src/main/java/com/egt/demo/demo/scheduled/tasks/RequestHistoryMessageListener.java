package com.egt.demo.demo.scheduled.tasks;

import com.egt.demo.demo.model.RequestHistory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestHistoryMessageListener {

    @RabbitListener(queues = MessagingApplication.QUEUE_SPECIFIC_NAME)
    public void receiveMessage(final List<RequestHistory> requestHistory) {
        System.out.printf("Received message as specific class: %s", requestHistory);
    }
}
