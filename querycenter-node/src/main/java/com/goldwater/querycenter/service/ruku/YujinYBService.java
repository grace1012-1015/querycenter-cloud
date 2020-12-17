package com.goldwater.querycenter.service.ruku;

import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.common.util.cache.SessionCache;
import com.goldwater.querycenter.common.util.string.StringUtil;
import com.goldwater.querycenter.dao.rw.ruku.RwYujinYBDao;
import com.goldwater.querycenter.dao.ycdb.management.RightDao;
import com.goldwater.querycenter.dao.ycdb.ruku.YcdbYujinYBDao;
import com.goldwater.querycenter.entity.management.Priviliges;
import com.goldwater.querycenter.entity.management.User;
import com.goldwater.querycenter.entity.ruku.vo.CosstVo;
import com.goldwater.querycenter.entity.ruku.vo.RiverVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class YujinYBService {
    @Autowired
    private YcdbYujinYBDao ycdbYujinYBDao;

    @Autowired
    private RwYujinYBDao rwYujinYBDao;

    @Autowired
    private RightDao rightDao;

    public Result querySwWaterLevelJk(String stdm, String startTm, String endTm){
        Result rs = new Result();

        List<CosstVo> listCosst = new ArrayList<>();
        List<RiverVo> listRiver = new ArrayList<>();

        User u = SessionCache.get();
        String userId = u.getUserCode();

        List<Priviliges> rightList = rightDao.getRightByUserId(userId);

        if (rightList.size() > 0 && Integer.parseInt(rightList.get(0).getLevel()) == 3) {
            String priviligeId = rightList.get(0).getPriviligeId();

            listCosst =   ycdbYujinYBDao.queryCosst_Level3(priviligeId, stdm, "");
        }
        else{
            listCosst =   ycdbYujinYBDao.queryCosst(stdm, "");
        }

        for(CosstVo cv : listCosst) {
            if (!StringUtil.isBlank(cv.getStcd())){
                listRiver.addAll(rwYujinYBDao.queryRiver(cv.getStcd(), startTm, endTm));
            }
        }

        rs.setData(listRiver);
        rs.setTotal(Integer.parseInt(listRiver.size() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }
}
