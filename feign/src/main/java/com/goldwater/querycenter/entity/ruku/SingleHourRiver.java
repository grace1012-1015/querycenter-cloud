package com.goldwater.querycenter.entity.ruku;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "ST_RIVER_R")
public class SingleHourRiver {
    @Column(name = "STCD")
    private String stcd;

    @Column(name = "TM")
    private String tm;

    @Column(name = "Z")
    private String z;

    @Column(name = "Q")
    private String q;

    @Column(name = "XSA")
    private String xsa;

    @Column(name = "XSAVV")
    private String xsavv;

    @Column(name = "XSMXV")
    private String xsmxv;

    @Column(name = "FLWCHRCD")
    private String flwchrcd;

    @Column(name = "WPTN")
    private String wptn;

    @Column(name = "MSQMT")
    private String msqmt;

    @Column(name = "MSAMT")
    private String msamt;

    @Column(name = "MSVMT")
    private String msvmt;
}
