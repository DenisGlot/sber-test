package com.denisgl.manager.service;

import com.denisgl.manager.dto.ManagerActionDto;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class SenderService {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${activemq.queue}")
    private String queueName;

    public void sendManagerInfo(ManagerActionDto managerActionDto) {
        jmsTemplate.convertAndSend(new ActiveMQQueue(queueName), managerActionDto);
    }
}
