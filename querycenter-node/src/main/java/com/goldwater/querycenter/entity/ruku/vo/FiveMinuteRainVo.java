package com.goldwater.querycenter.entity.ruku.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FiveMinuteRainVo implements Serializable {
    private String stcd;

    private String stnm;

    private String drp;

    private String tm;

    private String intv;

    private String pdr;

    private String dyp;

    private String wth;
}
