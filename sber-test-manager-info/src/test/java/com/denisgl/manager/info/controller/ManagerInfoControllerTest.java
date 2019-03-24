package com.denisgl.manager.info.controller;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import com.denisgl.manager.info.dto.ManagerInfo;
import com.denisgl.manager.info.service.ManagerInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ManagerInfoController.class)
public class ManagerInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ManagerInfoService managerInfoService;

    @Test
    public void getInfo_should_returnManagerInfo() throws Exception {
        Date date = new GregorianCalendar(2019, Calendar.MARCH, 24).getTime();
        ManagerInfo managerInfo = new ManagerInfo(
                Collections.singletonList(
                        new ManagerInfo.Manager("tester", Arrays.asList(
                                new ManagerInfo.Manager.Service("service1", date, date),
                                new ManagerInfo.Manager.Service("service2", date, date)
                        ))));
        when(managerInfoService.getManagerInfo()).thenReturn(managerInfo);

        this.mockMvc
                .perform(get("/info"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"managerList\": [\n" +
                        "    {\n" +
                        "      \"login\": \"tester\",\n" +
                        "      \"serviceList\": [\n" +
                        "        {\n" +
                        "          \"serviceName\": \"service1\",\n" +
                        "          \"serviceTimeStart\": \"2019-03-23T21:00:00.000+0000\",\n" +
                        "          \"serviceTimeEnd\": \"2019-03-23T21:00:00.000+0000\"\n" +
                        "        },\n" +
                        "        {\n" +
                        "          \"serviceName\": \"service2\",\n" +
                        "          \"serviceTimeStart\": \"2019-03-23T21:00:00.000+0000\",\n" +
                        "          \"serviceTimeEnd\": \"2019-03-23T21:00:00.000+0000\"\n" +
                        "        }\n" +
                        "      ]\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}\n"));
    }
}