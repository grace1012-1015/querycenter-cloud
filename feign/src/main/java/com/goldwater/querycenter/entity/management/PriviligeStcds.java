package com.goldwater.querycenter.entity.management;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "ST_PRIVILEGE_STCDS")
public class PriviligeStcds {
    @Column(name = "PRIVILEGE_ID")
    private String priviligeId;

    @Column(name = "STCD")
    private String stcd;
}
