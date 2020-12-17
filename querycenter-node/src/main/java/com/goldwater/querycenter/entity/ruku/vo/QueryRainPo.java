package com.goldwater.querycenter.entity.ruku.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryRainPo implements Serializable {
    private String columnName;

    private String tableName;

    private String tm;
}
