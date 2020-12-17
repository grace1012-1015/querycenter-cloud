package com.goldwater.querycenter.common.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
    public static final int SUCCESS = 1;
    public static final int FAILURE = -1;

    private static final long serialVersionUID = 5576237395711742681L;

    private boolean success = true;

    private String msg = "";

    private Object data = null;

    private Object obj = null;

    private Integer code = 1;

    private int total;

    private int page;

    public static Result success(String msg) {
        Result rs = new Result();
        rs.setSuccess(true);
        rs.setMsg(msg);
        return rs;
    }

    public static Result error(String msg) {
        Result rs = new Result();
        rs.setSuccess(false);
        rs.setMsg(msg);
        rs.setCode(1);
        return rs;
    }

    public static Result operating(String msg, Boolean f) {
        Result rs = new Result();
        rs.setSuccess(f);
        rs.setMsg(msg);
        rs.setCode(1);
        return rs;
    }

    public static Result operating(String msg, Boolean f, Integer code) {
        Result rs = new Result();
        rs.setSuccess(f);
        rs.setMsg(msg);
        rs.setCode(code);
        return rs;
    }

    public static Result operating(String msg, Boolean f, Integer code, Object data) {
        Result rs = new Result();
        rs.setSuccess(f);
        rs.setMsg(msg);
        rs.setCode(code);
        rs.setData(data);
        return rs;
    }

    public static Result operating(String msg, Boolean f, Integer code, Object data, Object obj) {
        Result rs = new Result();
        rs.setSuccess(f);
        rs.setMsg(msg);
        rs.setCode(code);
        rs.setData(data);
        rs.setObj(obj);
        return rs;
    }

    public static Result operating(String msg, Boolean f, Integer code, Object data, Integer total, Integer page) {
        Result rs = new Result();
        rs.setSuccess(f);
        rs.setMsg(msg);
        rs.setCode(code);
        rs.setData(data);
        rs.setTotal(total);
        rs.setPage(page);
        return rs;
    }
}
