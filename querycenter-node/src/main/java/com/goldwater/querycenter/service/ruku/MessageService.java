package com.goldwater.querycenter.service.ruku;

import com.github.pagehelper.PageHelper;
import com.goldwater.querycenter.common.util.BeanUtil;
import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.common.util.cache.SessionCache;
import com.goldwater.querycenter.common.util.date.DateUtil;
import com.goldwater.querycenter.dao.rtuex.RtuStationDao;
import com.goldwater.querycenter.dao.ycdb.ruku.MessageDao;
import com.goldwater.querycenter.dao.ycdb.management.RightDao;
import com.goldwater.querycenter.entity.management.Priviliges;
import com.goldwater.querycenter.entity.management.User;
import com.goldwater.querycenter.entity.ruku.vo.LastDataVo;
import com.goldwater.querycenter.entity.ruku.vo.LastSiteVo;
import com.goldwater.querycenter.entity.ruku.vo.MessageVo;
import com.goldwater.querycenter.entity.ruku.vo.StationMessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MessageService {
    @Autowired
    private MessageDao messageDao;

    @Autowired
    private RightDao rightDao;

    @Autowired
    private RtuStationDao rtuStationDao;

    public Result query(String stcd, String num){
        Result rs = new Result();

        User u = SessionCache.get();
        String userId = u.getUserCode();

        List<Priviliges> rightList = rightDao.getRightByUserId(userId);

        List<MessageVo> list = new ArrayList<>();

        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.DATE,-7);
        String sendTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss").format(calendar.getTime());

        if(rightList.size() > 0 && Integer.parseInt(rightList.get(0).getLevel())==3){
            list = messageDao.query_level3(rightList.get(0).getPriviligeId(), stcd, sendTime, num);
        }
        else {
            list = messageDao.query(stcd, sendTime, num);
        }

        rs.setData(list);
        rs.setTotal(Integer.parseInt(list.size() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result getLatestData(int pageIndex, int length){
        Result rs = new Result();
        List<Map<String, Object>> sileInfoList = new ArrayList<>();

        PageHelper.startPage(pageIndex, length);

        List<LastSiteVo> messageList = messageDao.getLatestSite();

        for(LastSiteVo vo : messageList) {
            List<LastDataVo> rtsrList = messageDao.getLatestData(vo.getStcd8(), vo.getLatestTime());
            StationMessageVo smvo = rtuStationDao.selectTop1StationMessage(vo.getStcd8(), vo.getLatestTime());

            if(smvo != null) {
                Map siteInfo = BeanUtil.objectToMap(smvo);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                for(LastDataVo rtsrMap : rtsrList) {
                    int valTyp = Integer.parseInt(rtsrMap.getValtpy());
                    Object deval = rtsrMap.getDeval();

                    if(valTyp == 11) {
                        //水位
                        siteInfo.put("WATER",deval);
                    }else if(valTyp == 21) {
                        //累计雨量
                        siteInfo.put("ACCRAIN",deval);
                    }else if(valTyp == 22) {
                        //小时雨量
                        siteInfo.put("HOURRAIN",deval);
                    }else if(valTyp == 24) {
                        //日雨量
                        siteInfo.put("DAYRAIN",deval);
                    }else if(valTyp == 51) {
                        //电压
                        siteInfo.put("VOLTAGE",deval);
                    }
                }

                siteInfo.put("SENDTIME", sdf.format((Timestamp)siteInfo.get("SENDTIME")));
                siteInfo.put("RECVTIME", sdf.format((Timestamp)siteInfo.get("RECVTIME")));

                sileInfoList.add(siteInfo);
            }
        }

        rs.setData(sileInfoList);
        rs.setTotal(Integer.parseInt(sileInfoList.size() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result getSingleLatest(String stcd, String tm, int pageIndex, int length){
        Result rs = new Result();

        List<Map<String, Object>> resultList = new ArrayList<>();
        Date startDate =  DateUtil.stringToDate(tm, "yyyy-MM-dd" );
        Date endDate = DateUtil.addDay(startDate, 1);

        PageHelper.startPage(pageIndex, length);

        List<LastDataVo> list = messageDao.getDayMessage(stcd, startDate, endDate);

        if(list != null && !list.isEmpty() && list.size() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (LastDataVo map :list) {
                Map<String,Object> resultMap = new HashMap<>();
                resultMap.put("YMDHM", sdf.format(Timestamp.valueOf(map.getYmdhm())));

                if(map.getValtpy() != null){
                    int valTyp = Integer.parseInt(map.getValtpy());
                    Object deval = map.getDeval();

                    if(valTyp == 11) {
                        //水位
                        resultMap.put("water", deval);
                    }else if(valTyp == 22) {
                        //小时雨量
                        resultMap.put("hourRain", deval);
                    }else if(valTyp == 23) {
                        //五分钟雨量
                        resultMap.put("minuteRain", deval);
                    }else if(valTyp == 51) {
                        //电压
                        resultMap.put("voltage", deval);
                    }
                }

                resultList.add(resultMap);
            }
        }

        rs.setData(resultList);
        rs.setTotal(Integer.parseInt(resultList.size() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }
}
