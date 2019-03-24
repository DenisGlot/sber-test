package com.denisgl.manager.controller;

import com.denisgl.manager.dto.ManagerActionDto;
import com.denisgl.manager.service.SenderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SenderController.class)
public class SenderControllerTest {

    private static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SenderService senderService;

    @Test
    public void sendInfo_should_returnOkResponse() throws Exception {
        ManagerActionDto dto = new ManagerActionDto();
        dto.setManagerLogin("tester");
        this.mockMvc
                .perform(
                        post("/send")
                        .header("Content-Type", "application/json")
                        .content(mapper.writeValueAsBytes(dto))

                )
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"ok\",\"message\":\"Successfully sent\"}"));

        verify(senderService).sendManagerInfo(dto);
    }
    @Test
    public void sendInfo_should_returnFalseStatus_whenExceptionThrown() throws Exception {
        ManagerActionDto dto = new ManagerActionDto();
        dto.setManagerLogin("tester");

        doThrow(new RuntimeException("Test exception")).when(senderService).sendManagerInfo(dto);

        this.mockMvc
                .perform(
                        post("/send")
                                .header("Content-Type", "application/json")
                                .content(mapper.writeValueAsBytes(dto))

                )
                .andExpect(status().isInternalServerError())
                .andExpect(content().json("{\"status\":\"false\",\"message\":\"Test exception\"}"));
    }
}
