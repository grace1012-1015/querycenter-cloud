package com.goldwater.querycenter.entity.management;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "ST_USER_PRIVILEGE")
public class UserPrivilige {
    @Column(name = "PRIVILEGE_ID")
    private String priviligeId;

    @Column(name = "UCODE")
    private String userCode;
}
