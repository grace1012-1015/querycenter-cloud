package com.goldwater.querycenter.service.ruku;

import com.github.pagehelper.PageHelper;
import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.common.util.cache.SessionCache;
import com.goldwater.querycenter.dao.ycdb.management.RightDao;
import com.goldwater.querycenter.dao.ycdb.ruku.TelemetryDao;
import com.goldwater.querycenter.entity.management.Priviliges;
import com.goldwater.querycenter.entity.management.User;
import com.goldwater.querycenter.entity.ruku.vo.RtsrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TelemetryService {
    @Autowired
    private TelemetryDao telemetryDao;

    @Autowired
    private RightDao rightDao;

    public Result queryTelemetryDatas(String startTm, String endTm, String stdm, String valtyp, String state, int pageIndex, int length) {
        Result rs = new Result();
        List<RtsrVo> list = new ArrayList();

        User u = SessionCache.get();
        String userId = u.getUserCode();

        List<Priviliges> rightList = rightDao.getRightByUserId(userId);

        PageHelper.startPage(pageIndex, length);

        if (rightList.size() > 0 && Integer.parseInt(rightList.get(0).getLevel()) == 3) {
            list = telemetryDao.queryTelemetryDatas_Level3(startTm, endTm, stdm, valtyp, state);
        }
        else{
            list = telemetryDao.queryTelemetryDatas(startTm, endTm, stdm, valtyp, state);
        }

        rs.setData(list);
        rs.setTotal(Integer.parseInt(list.size() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result queryStationData(String valtyp, String stcd, int pageIndex, int length){
        Result rs = new Result();

        PageHelper.startPage(pageIndex, length);

        List<RtsrVo> list = telemetryDao.queryStationData(valtyp, stcd);

        rs.setData(list);
        rs.setTotal(Integer.parseInt(list.size() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }
}
