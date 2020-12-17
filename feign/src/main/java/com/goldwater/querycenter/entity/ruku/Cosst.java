package com.goldwater.querycenter.entity.ruku;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "ST_COSST_B")
public class Cosst {
    @Column(name = "ID")
    private String id;

    @Column(name = "STCD")
    private String stcd;

    @Column(name = "STNM")
    private String stnm;

    @Column(name = "ORDR")
    private String ordr;

    @Column(name = "ARNM")
    private String arnm;
}
