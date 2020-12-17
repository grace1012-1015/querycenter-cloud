package com.goldwater.querycenter.entity.ruku;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "EXT_REPORTCONFIG_METADATA")
public class ReportConfigMetaData {
    @Column(name = "FIELDNAME")
    private String fieldName;

    @Column(name = "MID")
    private String mid;
}
