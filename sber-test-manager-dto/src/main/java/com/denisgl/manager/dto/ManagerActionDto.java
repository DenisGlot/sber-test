package com.denisgl.manager.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ManagerActionDto implements Serializable {

    private static final long serialVersionUID = 7526472295322776147L;

    private String managerLogin;
    private String serviceName;
    private Date serviceTimeStart;
    private Date serviceTimeEnd;
}
