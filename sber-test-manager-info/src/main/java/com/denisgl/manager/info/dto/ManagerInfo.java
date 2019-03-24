package com.denisgl.manager.info.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ManagerInfo {

    private List<Manager> managerList;

    @Data
    @AllArgsConstructor
    public static class Manager {
        private String login;
        private List<Service> serviceList;

        @Data
        @AllArgsConstructor
        public static class Service {
            private String serviceName;
            private Date serviceTimeStart;
            private Date serviceTimeEnd;
        }
    }
}
