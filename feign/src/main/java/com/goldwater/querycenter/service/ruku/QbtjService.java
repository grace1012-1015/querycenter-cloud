package com.goldwater.querycenter.service.ruku;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.common.util.date.DateToolkits;
import com.goldwater.querycenter.common.util.string.StringUtil;
import com.goldwater.querycenter.dao.rw.ruku.QbtjDao;
import com.goldwater.querycenter.entity.ruku.vo.QueryRainPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class QbtjService {
    @Autowired
    private QbtjDao qbtjDao;

    public Result queryDayRain(String stdm, String starttm, String endtm, int pageIndex, int length){
        Result rs = new Result();

        if(!StringUtil.isBlank(starttm) && !StringUtil.isBlank(endtm)) {
            int countday = (int) DateToolkits.dateSub_day(starttm, endtm);
            List<QueryRainPo> list = getQueryRainPoList(countday, DateToolkits.Day, starttm);

            PageHelper.startPage(pageIndex, length);

            List<Map<String, Object>> listResult = qbtjDao.queryRain(list, stdm);

            setResultHeji(listResult, list, endtm);

            PageInfo p = new PageInfo<>(list);
            rs.setData(p);
            rs.setData(list);
            rs.setCode(Result.SUCCESS);
        }

        return rs;
    }

    public Result queryHourRain(String stdm, String starttm, String endtm, int pageIndex, int length){
        Result rs = new Result();

        if(!StringUtil.isBlank(starttm) && !StringUtil.isBlank(endtm)) {
            int countday = (int) DateToolkits.dateSub_hour(starttm, endtm);
            List<QueryRainPo> list = getQueryRainPoList(countday, DateToolkits.hour, starttm);

            PageHelper.startPage(pageIndex, length);

            List<Map<String, Object>> listResult = qbtjDao.queryRain(list, stdm);

            setResultHeji(listResult, list, endtm);

            PageInfo p = new PageInfo<>(list);
            rs.setData(p);
            rs.setData(list);
            rs.setCode(Result.SUCCESS);
        }

        return rs;
    }

    private List<QueryRainPo> getQueryRainPoList(int countNumber, int unit, String starttm){
        List<QueryRainPo> list = new ArrayList<>();

        for(int i = 0; i <= countNumber; i++){
            QueryRainPo po = new QueryRainPo();

            po.setTableName("M" + (i + 1));
            po.setColumnName("TM" + (i + 1));

            if (unit == DateToolkits.hour){
                po.setTm(DateToolkits.StrDateAdd(starttm, DateToolkits.hour ,i));
            }
            else if(unit == DateToolkits.Day){
                po.setTm(DateToolkits.StrDateAdd(starttm, DateToolkits.Day ,i) +" 08:00");
            }

            list.add(po);
        }

        return list;
    }

    private void setResultHeji(List<Map<String, Object>> listResult, List<QueryRainPo> list, String endtm){
        //计算合计缺报次数，并存进list中
        Map<String, Object> map =new HashMap<String, Object>();
        Iterator<Map<String, Object>> iter = listResult.iterator();

        while (iter.hasNext()) {
            int heji = 0;
            map = iter.next();

            for(int i = 1; i < list.size(); i++){
                //统计该时间点的数据为1时，缺报次数为0；统计该时间点的数据为null或者0时，说明当天缺报，缺报次数为1
                int qbcount;

                if(map.get(list.get(i).getColumnName()) != null && !StringUtil.isBlank(list.get(i).getColumnName())
                        && Integer.parseInt(list.get(i).getColumnName()) == 1 ){
                    qbcount =0;
                }else{
                    qbcount =1;
                }

                //将缺报次数存入结果集中
                map.put(list.get(i).getColumnName(), qbcount);
                //缺报总数相加得到时间段内一共缺报的次数
                heji += qbcount;
            }

            //结束时间为当天时，系统时间不到8点不算缺报
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH");//设置日期格式
            String nowtm=df.format(new Date());// new Date()为获取当前系统时间

            if(nowtm.substring(0,10).equals(endtm) && Integer.parseInt(nowtm.substring(11))<8){
                map.put(list.get(list.size() - 1).getColumnName(), 0);
                heji--;
            }

            map.put("HEJI", heji);
        }
    }
}
