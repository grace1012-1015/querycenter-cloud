package com.goldwater.querycenter.entity.ruku.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class StationMessageVo implements Serializable {
    private String stcd;

    private String stcd8;

    private String stnm;

    private String sttp;

    private String rvnm;

    private String protocol;

    private String sendTime;

    private String recvTime;

    private String funcCode;
}
