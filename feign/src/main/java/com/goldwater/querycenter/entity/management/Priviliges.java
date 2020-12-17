package com.goldwater.querycenter.entity.management;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "ST_PRIVILIGES")
public class Priviliges {
    @Id
    @Column(name = "PRIVILEGE_ID")
    private String priviligeId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LEVEL")
    private String level;

    @Column(name = "ORDR")
    private String order;
}
