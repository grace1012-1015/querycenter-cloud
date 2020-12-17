package com.goldwater.querycenter.service.ruku;

import com.github.pagehelper.PageHelper;
import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.common.util.cache.SessionCache;
import com.goldwater.querycenter.common.util.string.StringUtil;
import com.goldwater.querycenter.dao.ycdb.management.RightDao;
import com.goldwater.querycenter.dao.ycdb.ruku.VolgtDao;
import com.goldwater.querycenter.entity.management.Priviliges;
import com.goldwater.querycenter.entity.management.User;
import com.goldwater.querycenter.entity.ruku.VolGate;
import com.goldwater.querycenter.entity.ruku.vo.AnomalyVoltageVo;
import com.goldwater.querycenter.entity.ruku.vo.RtsrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VolgtService {
    @Autowired
    private VolgtDao volgtDao;

    @Autowired
    private RightDao rightDao;

    public Result queryChart(String stcd, String time){
        Result rs = new Result();
        List<VolGate> list;
        String timeCondition = "";

        if (!StringUtil.isBlank(time)){
            if(time.indexOf(",")!=-1){
                String[] timeArr = time.toString().split(",");
                timeCondition = timeArr[1] + ":00:00";
            }else{
                timeCondition = time + ":00:00";
            }
        }

        list = volgtDao.queryChart(stcd, timeCondition);

        rs.setData(list);
        rs.setTotal(Integer.parseInt(list.size() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result queryAnomalyVoltage(String stcd, String time, int pageIndex, int length){
        Result rs = new Result();
        List<AnomalyVoltageVo> list = new ArrayList();

        User u = SessionCache.get();
        String userId = u.getUserCode();

        List<Priviliges> rightList = rightDao.getRightByUserId(userId);

        PageHelper.startPage(pageIndex, length);

        if (rightList.size() > 0 && Integer.parseInt(rightList.get(0).getLevel()) == 3) {
            String priviligeId = rightList.get(0).getPriviligeId();

            if (!StringUtil.isBlank(time)) {
                if(time.indexOf(",") != -1){
                    String[] timeArr = time.split(",");

                    list = volgtDao.queryAnomalyVoltage_Level3_start_end(priviligeId, timeArr[0]+":00:00", timeArr[1]+":00:00", stcd, "");
                }else{
                    list = volgtDao.queryAnomalyVoltage_Level3_end(priviligeId, time + ":00:00", stcd, "");
                }
            }else{
                list = volgtDao.queryAnomalyVoltage_Level3(priviligeId, stcd, "");
            }
        }
        else{
            if (!StringUtil.isBlank(time)) {
                if(time.indexOf(",") != -1){
                    String[] timeArr = time.split(",");

                    list = volgtDao.queryAnomalyVoltage_start_end(timeArr[0]+":00:00", timeArr[1]+":00:00", stcd, "");
                }else{
                    list = volgtDao.queryAnomalyVoltage_end(time + ":00:00", stcd, "");
                }
            }else{
                list = volgtDao.queryAnomalyVoltage(stcd, "");
            }
        }

        rs.setData(list);
        rs.setTotal(Integer.parseInt(list.size() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }
}
