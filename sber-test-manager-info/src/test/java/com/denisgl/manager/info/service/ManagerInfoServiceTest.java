package com.denisgl.manager.info.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.denisgl.manager.info.dto.ManagerInfo;
import com.denisgl.manager.info.persistence.entity.ManagerAction;
import com.denisgl.manager.info.persistence.repository.ManagerActionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ManagerInfoServiceTest {

    @TestConfiguration
    static class ManagerInfoServiceTestContextConfiguration {

        @Bean
        public ManagerInfoService employeeService() {
            return new ManagerInfoService();
        }
    }

    @Autowired
    private ManagerInfoService managerInfoService;

    @MockBean
    private ManagerActionRepository managerActionRepository;

    @Test
    public void getManagerInfo_should_assembleManagerInfo() {
        ManagerAction action1 = createManagerAction("1", "service1", getDateByDay(5), getDateByDay(6));
        ManagerAction action2 = createManagerAction("1", "service2", getDateByDay(3), getDateByDay(4));
        ManagerAction action3 = createManagerAction("2", "service", getDateByDay(1), getDateByDay(2));

        when(managerActionRepository.findAll()).thenReturn(Arrays.asList(action1, action2, action3));

        ManagerInfo managerInfo = managerInfoService.getManagerInfo();

        List<ManagerInfo.Manager> managerList = managerInfo.getManagerList();
        assertThat(managerList, hasSize(2));

        ManagerInfo.Manager tester2 = managerList.get(0);
        assertThat(tester2.getLogin(), equalTo("2"));

        List<ManagerInfo.Manager.Service> serviceList2 = tester2.getServiceList();
        assertThat(serviceList2, hasSize(1));

        ManagerInfo.Manager.Service service = serviceList2.get(0);
        assertThat(service.getServiceName(), equalTo("service"));
        assertThat(service.getServiceTimeStart(), equalTo(getDateByDay(1)));
        assertThat(service.getServiceTimeEnd(), equalTo(getDateByDay(2)));

        ManagerInfo.Manager tester1 = managerList.get(1);
        assertThat(tester1.getLogin(), equalTo("1"));

        List<ManagerInfo.Manager.Service> serviceList1 = tester1.getServiceList();
        assertThat(serviceList1, hasSize(2));

        ManagerInfo.Manager.Service service2 = serviceList1.get(0);
        assertThat(service2.getServiceName(), equalTo("service2"));
        assertThat(service2.getServiceTimeStart(), equalTo(getDateByDay(3)));
        assertThat(service2.getServiceTimeEnd(), equalTo(getDateByDay(4)));

        ManagerInfo.Manager.Service service1 = serviceList1.get(1);
        assertThat(service1.getServiceName(), equalTo("service1"));
        assertThat(service1.getServiceTimeStart(), equalTo(getDateByDay(5)));
        assertThat(service1.getServiceTimeEnd(), equalTo(getDateByDay(6)));

    }

    private Date getDateByDay(int day) {
        return new GregorianCalendar(2019, Calendar.MARCH, day).getTime();
    }

    private ManagerAction createManagerAction(String login, String service, Date start, Date end) {
        ManagerAction managerAction = new ManagerAction();
        managerAction.setManagerLogin(login);
        managerAction.setServiceName(service);
        managerAction.setServiceTimeStart(start);
        managerAction.setServiceTimeEnd(end);

        return managerAction;
    }
}
