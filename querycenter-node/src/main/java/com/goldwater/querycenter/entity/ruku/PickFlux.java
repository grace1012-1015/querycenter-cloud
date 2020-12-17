package com.goldwater.querycenter.entity.ruku;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "ST_PICK_FLUX")
public class PickFlux {
    @Column(name = "STCD")
    private String stcd;

    @Column(name = "STNM")
    private String stnm;

    @Column(name = "EnterFlow")
    private String enterFlow;
}
