package com.goldwater.querycenter.entity.ruku;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "ST_STCONFIG")
public class StConfig {
    @Column(name = "STCD")
    private String stcd;

    @Column(name = "STNM")
    private String stnm;

    @Column(name = "STTP")
    private String sttp;

    @Column(name = "PMAX5M")
    private String pMax5m;

    @Column(name = "PMAX1H")
    private String pMax1h;

    @Column(name = "ZMIN")
    private String zMin;

    @Column(name = "ZMAX")
    private String aMax;

    @Column(name = "SRAGE")
    private String srage;

    @Column(name = "TIMECT")
    private String timect;

    @Column(name = "DYE")
    private String dye;

    @Column(name = "WTMP")
    private String wtmp;

    @Column(name = "GATE")
    private String gate;

    @Column(name = "VOLGATE")
    private String volGate;

    @Column(name = "SOIL")
    private String soil;

    @Column(name = "VELOCITY")
    private String veloCity;

    @Column(name = "OTT")
    private String ott;
}
