package com.goldwater.querycenter.entity.management.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserVo  implements Serializable {
    private String userCode;

    private String userName;

    private String userEmail;

    private String userPwd;

    private String userCtel;

    private String userState;

    private String userCompany;

    private String userPhone;

    private String userCbrief;

    private String priviligeName;

    private String priviligeId;
}
