package com.denisgl.manager.info.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.denisgl.manager.info.dto.ManagerInfo;
import com.denisgl.manager.info.persistence.entity.ManagerAction;
import com.denisgl.manager.info.persistence.repository.ManagerActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerInfoService {

    @Autowired
    private ManagerActionRepository managerActionRepository;

    public ManagerInfo getManagerInfo() {
        List<ManagerAction> managerActions = managerActionRepository.findAll();

        Map<String, List<ManagerAction>> groupByManager = managerActions.stream()
                .collect(Collectors.groupingBy(ManagerAction::getManagerLogin));

        List<ManagerInfo.Manager> managers = new ArrayList<>();
        groupByManager.forEach((login, services) ->
                managers.add(new ManagerInfo.Manager(login, getSortedServices(services)))
        );

        List<ManagerInfo.Manager> sortedManagers = managers.stream()
                .filter(manager -> manager.getServiceList() != null && !manager.getServiceList().isEmpty())
                .sorted(Comparator.comparing(manager -> manager.getServiceList().get(0).getServiceTimeStart()))
                .collect(Collectors.toList());
        return new ManagerInfo(sortedManagers);
    }

    private List<ManagerInfo.Manager.Service> getSortedServices(List<ManagerAction> actions) {
        return actions.stream()
                .map(action -> new ManagerInfo.Manager.Service(
                        action.getServiceName(), action.getServiceTimeStart(), action.getServiceTimeEnd())
                )
                .sorted(Comparator.comparing(ManagerInfo.Manager.Service::getServiceTimeStart))
                .collect(Collectors.toList());
    }
}
