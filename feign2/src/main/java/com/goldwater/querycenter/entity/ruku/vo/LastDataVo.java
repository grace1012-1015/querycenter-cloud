package com.goldwater.querycenter.entity.ruku.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LastDataVo implements Serializable {
    private String stcd;

    private String deval;

    private String valtpy;

    private String ymdhm;
}
