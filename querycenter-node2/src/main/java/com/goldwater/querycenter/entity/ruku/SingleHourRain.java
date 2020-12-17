package com.goldwater.querycenter.entity.ruku;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "ST_PPTN_R")
public class SingleHourRain {
    @Column(name = "STCD")
    private String stcd;

    @Column(name = "TM")
    private String tm;

    @Column(name = "DRP")
    private String drp;

    @Column(name = "INTV")
    private String intv;

    @Column(name = "PDR")
    private String pdr;

    @Column(name = "DYP")
    private String dyp;

    @Column(name = "WTH")
    private String wth;
}
