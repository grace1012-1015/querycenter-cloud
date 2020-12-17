package com.goldwater.querycenter.entity.management;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "ST_USER")
public class User {
    @Id
    @Column(name = "UCODE")
    private String userCode;

    @Column(name = "UPWD")
    private String userPwd;

    @Column(name = "UCOMPANY")
    private String userCompany;

    @Column(name = "UCBRIEF")
    private String userCbrief;

    @Column(name = "UCTEL")
    private String userCtel;

    @Column(name = "UNAME")
    private String userName;

    @Column(name = "UEMAIL")
    private String userEmail;

    @Column(name = "UPHONE")
    private String userPhone;

    @Column(name = "UKEY")
    private String userKey;

    @Column(name = "USTATE")
    private String userState;

    @Column(name = "UROLE")
    private String userRole;
}
