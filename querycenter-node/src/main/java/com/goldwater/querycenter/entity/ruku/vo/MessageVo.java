package com.goldwater.querycenter.entity.ruku.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageVo implements Serializable {
    private String rowNumber;

    private String stcd;

    private String stcd8;

    private String stnm;

    private String sendTime;

    private String recvTime;

    private String channel;

    private String flag;

    private String message;
}
