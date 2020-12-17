package com.goldwater.querycenter.service.ruku;

import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.dao.rw.ruku.ChartDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChartService {
    @Autowired
    private ChartDao chartDao;

    public Result getSingleHourRain(String stcd, String sttm, String entm){
        Result rs = new Result();

        rs.setData(chartDao.getSingleHourRain(stcd, sttm, entm));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result getSingleHourRiver(String stcd, String sttm, String entm){
        Result rs = new Result();

        rs.setData(chartDao.getSingleHourRiver(stcd, sttm, entm));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result getSingleRvffch(String stcd){
        Result rs = new Result();

        rs.setData(chartDao.getSingleRvffch(stcd));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result getSingleRZWRelation(String stcd){
        Result rs = new Result();

        rs.setData(chartDao.getSingleRZWRelation(stcd));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result getSingleZqrlRelation(String stcd){
        Result rs = new Result();

        rs.setData(chartDao.getSingleZqrlRelation(stcd));
        rs.setCode(Result.SUCCESS);

        return rs;
    }
}
