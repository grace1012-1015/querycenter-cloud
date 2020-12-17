package com.goldwater.querycenter.entity.management;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(name = "ST_PRIVILEGE_MENU")
public class PriviligeMenu {
    @Column(name = "PRIVILEGE_ID")
    private String priviligeId;

    @Column(name = "MENU_ID")
    private String menuId;

}
