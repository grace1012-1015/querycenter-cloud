package com.goldwater.querycenter.entity.ruku.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AnomalyVoltageVo implements Serializable {
    private String stcd;

    private String stnm;

    private String tm;

    private String volGate;
}
