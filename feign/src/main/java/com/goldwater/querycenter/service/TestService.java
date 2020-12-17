package com.goldwater.querycenter.service;

import com.goldwater.querycenter.common.util.source.Enum;
import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.dao.rtuex.RtuexPublicDao;
import com.goldwater.querycenter.dao.rw.RwPublicDao;
import com.goldwater.querycenter.dao.rwdb.RwdbPublicDao;
import com.goldwater.querycenter.dao.ycdb.YcdbPublicDao;
import com.goldwater.querycenter.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.goldwater.querycenter.service.redis.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TestService {
    @Autowired
    private RwPublicDao rwPublicDao;

    @Autowired
    private YcdbPublicDao ycdbPublicDao;

    @Autowired
    private RwdbPublicDao rwdbPublicDao;

    @Autowired
    private RtuexPublicDao rtuexPublicDao;

    @Autowired
    private RedisService redisService;

    public Result datas(String addvcd, int pageIndex, int length){
        Result rs = new Result();

        PageHelper.startPage(pageIndex, length);

        Map map = new HashMap();
        map.put("addvcd", addvcd);

        List<Test> testList = rwPublicDao.datas(map);

        PageInfo p = new PageInfo<>(testList);
        rs.setData(p);
        rs.setTotal(Integer.parseInt(p.getTotal() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result updateAddvcdByCd(){
        Result rs = new Result();

        Test t = new Test();

        t.setAddvcd("000000");
        t.setAddvnm("springboot test modified");
        t.setComments("test comments modified");
        t.setModiTime(new Date());

        int i = rwPublicDao.updateAddvcdByCd(t);

        if (i >0) return Result.operating("数据修改成功", true, Result.SUCCESS);

        return Result.operating("数据修改失败", true, Result.SUCCESS);
    }

    public Result insertAddvcd(){
        Result rs = new Result();

        Test t = new Test();

        t.setAddvcd("000000");
        t.setAddvnm("springboot test");
        t.setComments("test comments");
        t.setModiTime(new Date());

        int i = rwPublicDao.insertAddvcd(t);

        if (i >0) return Result.operating("数据新增成功", true, Result.SUCCESS);

        return Result.operating("数据新增失败", true, Result.SUCCESS);
    }

    public Result deleteAddvcdByCd(){
        Result rs = new Result();

        Test t = new Test();

        t.setAddvcd("000000");

        int i = rwPublicDao.deleteAddvcdByCd(t);

        if (i >0) return Result.operating("数据删除成功", true, Result.SUCCESS);

        return Result.operating("数据删除失败", true, Result.SUCCESS);
    }

    public Result redisDatas(){
        redisService.set("testkey", "goldwater test");

        return Result.operating(Enum.QUERY_SUCCESS,true, Result.SUCCESS, redisService.get("testkey"));
    }

    public Result ycdbDatas(){
        Result rs = new Result();

        rs.setData(ycdbPublicDao.datas());

        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result mutiDatas(){
        Result rs = new Result();

        int ycdb = ycdbPublicDao.datas();
        int rw = rwPublicDao.testDatas();
        int rwdb = rwdbPublicDao.datas();
        int rtuex = rtuexPublicDao.datas();

        rs.setCode(Result.SUCCESS);

        rs.setData(ycdb + "|" + rw + "|" + rwdb + "|" + rtuex);

        return rs;
    }

    public String hello() {
        System.out.println("Hello Feign Service。");

        return "Hello Feign Service。";
    }
}
