package com.denisgl.manager.controller;

import com.denisgl.manager.dto.ManagerActionDto;
import com.denisgl.manager.service.SenderService;
import com.denisgl.manager.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SenderController {

    private final SenderService senderService;

    @PostMapping(path = "send", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Response sendInfo(@RequestBody ManagerActionDto managerActionDto) {
        senderService.sendManagerInfo(managerActionDto);
        return new Response("ok", "Successfully sent");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleException(Exception e) {
        return new ResponseEntity<>(new Response("false", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
