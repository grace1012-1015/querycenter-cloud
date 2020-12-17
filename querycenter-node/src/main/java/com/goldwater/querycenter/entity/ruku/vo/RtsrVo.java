package com.goldwater.querycenter.entity.ruku.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class RtsrVo implements Serializable {
    private String stcd;

    private String stnm;

    private String devNo;

    private String ymdhm;

    private String deval;

    private String valtyp;

    private String channel;

    private String flag;

    private String insertTime;
}
