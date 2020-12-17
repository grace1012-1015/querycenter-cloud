package com.goldwater.querycenter.entity.ruku;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "ST_ZVARL_B")
public class SingleRZWRelation {
    @Column(name = "STCD")
    private String stcd;

    @Column(name = "MSTM")
    private String mstm;

    @Column(name = "PTNO")
    private String ptno;

    @Column(name = "RZ")
    private String rz;

    @Column(name = "W")
    private String w;

    @Column(name = "WSFA")
    private String wsfa;

    @Column(name = "MODITIME")
    private String modiTime;
}
