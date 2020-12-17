package com.goldwater.querycenter.entity.management;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Data
@Table(name = "ST_MENU")
public class Menu {
    @Column(name = "MENU_ID")
    private String menuId;

    @Column(name = "MENU_NM")
    private String menuNum;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "ORDR")
    private String ordr;

    @Column(name = "PRT_ID")
    private String prtId;

    @Transient
    private List<Menu> children;

    @Transient
    private int id;

    @Transient
    private String title;

    @Transient
    private String icon;

    @Transient
    private String openType;

    @Transient
    private int type;

    @Transient
    private String href;
}
