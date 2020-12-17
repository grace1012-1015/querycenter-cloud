package com.goldwater.querycenter.service.ruku;

import com.github.pagehelper.PageHelper;
import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.common.util.cache.SessionCache;
import com.goldwater.querycenter.dao.ycdb.management.RightDao;
import com.goldwater.querycenter.dao.ycdb.ruku.TacklDao;
import com.goldwater.querycenter.entity.management.Priviliges;
import com.goldwater.querycenter.entity.management.User;
import com.goldwater.querycenter.entity.ruku.vo.TacklVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TacklService {
    @Autowired
    private TacklDao tacklDao;

    @Autowired
    private RightDao rightDao;

    public Result query(String dyp, String stdm, String startTm, String endTm, int pageIndex, int length){
        Result rs = new Result();
        List<TacklVo> list = new ArrayList<>();

        User u = SessionCache.get();
        String userId = u.getUserCode();

        List<Priviliges> rightList = rightDao.getRightByUserId(userId);

        PageHelper.startPage(pageIndex, length);

        if(rightList.size() > 0 && Integer.parseInt(rightList.get(0).getLevel())==3){
            list = tacklDao.queryTackl_Level3(rightList.get(0).getPriviligeId(), dyp, stdm, startTm, endTm);
        }
        else{
            list = tacklDao.queryTackl(dyp, stdm, startTm, endTm);
        }

        rs.setData(list);
        rs.setTotal(Integer.parseInt(list.size() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }
}
