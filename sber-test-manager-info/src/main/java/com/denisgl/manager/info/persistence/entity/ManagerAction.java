package com.denisgl.manager.info.persistence.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class ManagerAction {

    @Id
    @GeneratedValue
    private Long id;
    private String managerLogin;
    private String serviceName;
    private Date serviceTimeStart;
    private Date serviceTimeEnd;
}
