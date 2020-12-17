package com.goldwater.querycenter.entity.ruku;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "RTU_STATION")
public class RtuStation {
    @Column(name = "STCD")
    private String stcd;

    @Column(name = "STCD8")
    private String stcd8;

    @Column(name = "STNM")
    private String stnm;

    @Column(name = "STTP")
    private String sttp;

    @Column(name = "RVNM")
    private String rvnm;

    @Column(name = "PROTOCOL")
    private String protocol;

    @Column(name = "RTUCD")
    private String rtucd;

    @Column(name = "CTCD")
    private String ctcd;

    @Column(name = "BSNM")
    private String bsnm;

    @Column(name = "HNNM")
    private String hnnm;

    @Column(name = "DTMEL")
    private Double dtmel;

    @Column(name = "ACCURATY")
    private String accuraty;

    @Column(name = "RIVER_WARN_LOW")
    private String river_warn_low;

    @Column(name = "RIVER_WARN_HIGH")
    private String river_warn_high;

    @Column(name = "RAIN_WARN_MINUTE")
    private String river_warn_minute;

    @Column(name = "TELPHONE")
    private String telephone;

    @Column(name = "FLAG_HD")
    private String flag_hd;

    @Column(name = "CENTER")
    private String center;

    @Column(name = "BORROW")
    private String borrow;

    @Column(name = "FLAG_RAIN")
    private String flag_rain;

    @Column(name = "FLAG_WATER")
    private String flag_water;
}
