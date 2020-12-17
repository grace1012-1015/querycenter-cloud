package com.goldwater.querycenter.entity.rtu;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "RTU_CALLER")
public class RtuCaller {
    @Column(name = "ID")
    private String id;

    @Column(name = "RTUCD")
    private String rtucd;

    @Column(name = "FUNC")
    private String func;

    @Column(name = "PARAMS")
    private String params;

    @Column(name = "CMDTIME")
    private String cmdTime;
}
