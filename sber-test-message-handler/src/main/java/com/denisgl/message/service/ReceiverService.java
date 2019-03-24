package com.denisgl.message.service;

import com.denisgl.manager.dto.ManagerActionDto;
import com.denisgl.message.persistence.entity.ManagerAction;
import com.denisgl.message.persistence.repository.ManagerActionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReceiverService {

    @Autowired
    private ManagerActionRepository managerActionRepository;

    @JmsListener(destination = "${activemq.queue}")
    public void receive(ManagerActionDto managerActionDto) {
        log.info("received message='{}'", managerActionDto);

        ManagerAction entity = convertManagerAction(managerActionDto);
        managerActionRepository.save(entity);
    }

    private ManagerAction convertManagerAction(ManagerActionDto dto) {
        ManagerAction entity= new ManagerAction();

        entity.setManagerLogin(dto.getManagerLogin());
        entity.setServiceName(dto.getServiceName());
        entity.setServiceTimeStart(dto.getServiceTimeStart());
        entity.setServiceTimeEnd(dto.getServiceTimeEnd());

        return entity;
    }
}
