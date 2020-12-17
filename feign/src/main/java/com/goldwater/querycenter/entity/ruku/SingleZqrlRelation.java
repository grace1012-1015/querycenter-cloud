package com.goldwater.querycenter.entity.ruku;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "ST_ZQRL_B")
public class SingleZqrlRelation {
    @Column(name = "STCD")
    private String stcd;

    @Column(name = "LNNM")
    private String lnnm;

    @Column(name = "BGTM")
    private String bgtm;

    @Column(name = "PTNO")
    private String ptno;

    @Column(name = "Z")
    private String z;

    @Column(name = "Q")
    private String q;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "MODITIME")
    private String modiTime;
}
