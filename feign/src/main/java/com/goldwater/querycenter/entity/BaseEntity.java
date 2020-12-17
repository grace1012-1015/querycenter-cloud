package com.goldwater.querycenter.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.util.Date;

@Data
public class BaseEntity {
    /**
     * //创建时间
     */
    //@Column(name = "create_time")
    //@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    //private Date createTime;

    /**
     * //最后更新时间
     */
    //@Column(name = "update_time")
    //@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    // private Date updateTime;

    /**
     * 创建者
     */
    //@Column(name = "create_by")
    //private String createBy;

    /**
     * 最后修改人
     */
    //@Column(name = "update_by")
    //private String updateBy;

    @Transient
    private Integer page;
    @Transient
    private Integer limit;
}
