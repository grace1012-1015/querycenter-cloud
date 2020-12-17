package com.goldwater.querycenter.entity.ruku.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class CosstVo implements Serializable {
    private String id;

    private String stcd;

    private String stnm;

    private String ordr;

    private String arnm;

    private String custom;

    private String stlc;

    List<PptnVo> listPptn = new ArrayList<>();
}
