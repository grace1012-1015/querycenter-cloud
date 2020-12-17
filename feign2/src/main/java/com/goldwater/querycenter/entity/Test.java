package com.goldwater.querycenter.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "st_addvcd_d")
public class Test extends BaseEntity{
    @Column(name = "addvcd")
    private String addvcd;

    @Column(name = "addvnm")
    private String addvnm;

    @Column(name = "comments")
    private String comments;

    @Column(name = "moditime")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modiTime;
}
