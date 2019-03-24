package com.denisgl.manager.info.controller;

import com.denisgl.manager.info.service.ManagerInfoService;
import com.denisgl.manager.info.dto.ManagerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManagerInfoController {

    @Autowired
    private ManagerInfoService managerInfoService;

    @GetMapping("info")
    public ManagerInfo getInfo() {
        return managerInfoService.getManagerInfo();
    }
}
