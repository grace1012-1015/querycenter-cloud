package com.goldwater.querycenter.common.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateToolkits {
    /**
     * 单位小时
     */
    public static int hour = Calendar.HOUR_OF_DAY;

    /**
     * 单位 天（一周可以用7天代替）
     */
    public static int Day = Calendar.DATE;

    /**
     * 单位 月
     */
    public static int Month = Calendar.MONTH;

    /**
     * 单位 年
     */
    public static int Year = Calendar.YEAR;

    /**
     * 日期相加
     * @author mxd
     * @param start 开始日期
     * @param dateType 累加的类型
     * @param value 累加值
     * @return 返回新的日期
     *
     */
    public static Date dateAdd(Date start, int dateType , int value)
    {
        Date result = new Date();

        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(start);
        calBegin.add(dateType, value);
        result = calBegin.getTime();

        return result;
    }

    /**
     * 日期相加
     * @author qpp
     * @param starttm(yyyy-MM-dd) 开始日期
     * @param dateType 累加的类型
     * @param value 累加值
     * @return 返回新的日期(yyyy-MM-dd)
     *
     */
    public static String StrDateAdd(String starttm,int dateType ,int value)
    {
        String result = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calBegin = Calendar.getInstance();
            calBegin.setTime(sdf.parse(starttm));
            calBegin.add(dateType, value);
            result = sdf.format(calBegin.getTime());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 日期相减返回天数
     * @param sttm 开始时间(YYYY-MM-DD)
     * @param entm 结束时间(YYYY-MM-DD)
     * @int
     */
    public static long dateSub_day(String sttm,String entm)
    {
        long result = 0;
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date st = sdf.parse(sttm);
            Date en = sdf.parse(entm);
            result = (en.getTime()-st.getTime())/(24*60*60*1000);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 日期相减返回小时数
     * @param sttm 开始时间(YYYY-MM-DD HH:mm)
     * @param entm 结束时间(YYYY-MM-DD HH:mm)
     * @int
     */
    public static long dateSub_hour(String sttm,String entm)
    {
        long result = 0;
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm");
            Date st = sdf.parse(sttm);
            Date en = sdf.parse(entm);
            result = (en.getTime()-st.getTime())/(60*60*1000);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 根据标志位（年、月、旬、日）把查询的开始和结束时间补全
     * @param sttm 传入的查询开始时间
     * @param entm 传入的查询结束时间
     * @param sttdrcd 是间标志位 1、日 4 旬 5 月 6 年
     */
    public static Map<String,String> getEvsOrAvgQueryTmBySttd(String sttm, String entm, int sttdrcd)
    {
        Map<String,String> result = new HashMap<String, String>();
        String newSttm = "";
        String newEntm = "";
        if(sttdrcd == 6)
        {
            //如果是年，默认的传入的是年份，后面变成当年1月1日8点
            newSttm = sttm.substring(0,4)+"-01-01 08";
            newEntm = entm.substring(0,4)+"-01-01 08";
        }
        else if(sttdrcd == 5)
        {
            //如果是月，默认的传入的是年份，后面变成当年当月1日8点
            newSttm = sttm.substring(0,7)+"-01 08";
            newEntm = entm.substring(0,7)+"-01 08";
        }
        else if(sttdrcd == 4 || sttdrcd == 1)
        {
            //如果是旬或者日，默认传入的是年月日格式，后面加8点
            newSttm = sttm.substring(0,10)+" 08";
            newEntm = entm.substring(0,10)+" 08";
        }
        result.put("STTM", newSttm);
        result.put("ENTM", newEntm);
        return result;
    }
}
