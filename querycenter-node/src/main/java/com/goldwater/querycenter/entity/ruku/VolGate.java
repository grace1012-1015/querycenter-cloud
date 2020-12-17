package com.goldwater.querycenter.entity.ruku;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "ST_VOLGATE_R")
public class VolGate {
    @Column(name = "TM")
    private String tm;

    @Column(name = "VOLGATE")
    private String volGate;
}
