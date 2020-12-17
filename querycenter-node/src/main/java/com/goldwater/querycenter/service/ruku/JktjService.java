package com.goldwater.querycenter.service.ruku;

import com.goldwater.querycenter.common.util.BeanUtil;
import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.common.util.cache.SessionCache;
import com.goldwater.querycenter.common.util.date.DateToolkits;
import com.goldwater.querycenter.common.util.string.StringUtil;
import com.goldwater.querycenter.dao.rw.ruku.RwJktjDao;
import com.goldwater.querycenter.dao.ycdb.management.RightDao;
import com.goldwater.querycenter.dao.ycdb.ruku.CosstDao;
import com.goldwater.querycenter.dao.ycdb.ruku.YcdbJktjDao;
import com.goldwater.querycenter.entity.management.Priviliges;
import com.goldwater.querycenter.entity.management.User;
import com.goldwater.querycenter.entity.ruku.vo.CosstVo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class JktjService {
    @Autowired
    private CosstDao cosstDao;

    @Autowired
    private RightDao rightDao;

    @Autowired
    private RwJktjDao rwJktjDao;

    @Autowired
    private YcdbJktjDao ycdbJktjDao;

    public Result querySoil(String stcd, String time){
        Result rs = new Result();
        List<Map> result = new ArrayList<>();
        List<CosstVo> listCosst = new ArrayList<>();

        List<Priviliges> rightList = getRights();

        if(rightList.size() > 0 && Integer.parseInt(rightList.get(0).getLevel())==3) {
            listCosst = cosstDao.getSoilCosst_Level3(rightList.get(0).getPriviligeId(), stcd);
        }
        else{
            listCosst = cosstDao.getSoilCosst(stcd);
        }

        try {
            for (CosstVo csvo : listCosst){
                List<Map> list = new ArrayList<>();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(time));
                int thisMaxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                int today = calendar.get(Calendar.DATE);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int lastMaxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

                calendar.add(Calendar.MONTH, -1);
                String monthStr = month < 10 ? "0" + month : "" + month;
                String lastMonthStr = month - 1 < 10 ? "0" + (month - 1) : "" + (month - 1);

                if (today == thisMaxDay || today >= lastMaxDay) {
                    int dayIndex = 1;

                    for (int i = 1; i <= thisMaxDay; i++) {
                        Map m = new HashMap();
                        String day;
                        dayIndex++;

                        if (i < 10) {
                            day = "0" + i;
                        } else {
                            day = "" + i;
                        }

                        m.put("drp", "day" + dayIndex);
                        m.put("date", year + monthStr + day + "08");

                        list.add(m);
                    }
                } else {
                    String day;
                    int dayIndex = 0;

                    for (int i = today; i <= lastMaxDay; i++) {
                        Map m = new HashMap();
                        dayIndex++;

                        if (i < 10) {
                            day = "0" + i;
                        } else {
                            day = "" + i;
                        }

                        m.put("drp", "day" + dayIndex);
                        m.put("date", year + lastMonthStr + day + "08");

                        list.add(m);
                    }

                    for(int i=1;i<=today;i++){
                        Map m = new HashMap();
                        dayIndex++;

                        if(i<10){
                            day="0"+i;
                        }else{
                            day=""+i;
                        }

                        m.put("drp", "day" + dayIndex);
                        m.put("date", year + monthStr + day + "08");

                        list.add(m);
                    }
                }

                result.addAll(rwJktjDao.querySoil(list, csvo.getStcd()));
            }

            rs.setCode(Result.SUCCESS);
            rs.setData(result);
        }
        catch (Exception ex){
            rs.setCode(Result.FAILURE);
            rs.setMsg(ex.getMessage());
        }

        return rs;
    }

    public Result querySwRainJk(String stcd, String time, String type, String custom, String addvcd){
        Result rs = new Result();
        List<Map> result = new ArrayList<>();
        List<CosstVo> listCosst = getSwjkCosst(stcd, type, custom, addvcd);

        try {
            for (CosstVo csvo : listCosst) {
                List<Map> list = new ArrayList<>();

                for(int i=0;i<24;i++){
                    String index;
                    Map m = new HashMap();

                    if(i<10){
                        index="0"+i;
                    }else{
                        index=""+i;
                    }

                    m.put("index", index);
                    m.put("drp", "tm" + i);

                    list.add(m);
                }

                result.addAll(rwJktjDao.querySwRainJk(list, csvo.getStcd(), time, StringUtil.isBlank(time) ? "" : DateToolkits.StrDateAdd(time,DateToolkits.Day ,1)));
            }

            rs.setCode(Result.SUCCESS);
            rs.setData(result);
        }
        catch (Exception ex){
            rs.setCode(Result.FAILURE);
            rs.setMsg(ex.getMessage());
        }

        return rs;
    }

    public Result querySwWaterlevelJk(String stcd, String time, String type, String custom, String addvcd){
        Result rs = new Result();
        List<Map> result = new ArrayList<>();
        List<CosstVo> listCosst = getSwjkCosst(stcd, type, custom, addvcd);

        try {
            for (CosstVo csvo : listCosst) {
                List<Map> list = new ArrayList<>();

                if("61074750".equals(csvo.getStcd())){
                    System.out.println(csvo.getStcd());

                    continue;
                }

                for(int i=0;i<24;i++){
                    String index;
                    Map m = new HashMap();

                    if(i<10){
                        index="0"+i;
                    }else{
                        index=""+i;
                    }

                    m.put("index", index);
                    m.put("drp", "tm" + i);

                    list.add(m);
                }

                String sttp = ycdbJktjDao.getSttp(csvo.getStcd());
                String upOrDown = "R.DWZ";
                String ppupOrPpdown = "R.PPDWZ";

                if( !StringUtil.isBlank(sttp) && sttp.contains("闸上") ) {
                    upOrDown = "R.UPZ";
                }
                if(!StringUtil.isBlank(sttp)&&sttp.contains("泵上")) {
                    ppupOrPpdown = " R.PPUPZ";
                }

                result.addAll(rwJktjDao.querySwWaterJk(list, csvo.getStcd(), time, StringUtil.isBlank(time) ? "" : DateToolkits.StrDateAdd(time,DateToolkits.Day ,1), upOrDown, ppupOrPpdown));
            }

            rs.setCode(Result.SUCCESS);
            rs.setData(result);
        }
        catch (Exception ex){
            rs.setCode(Result.FAILURE);
            rs.setMsg(ex.getMessage());
        }

        return rs;
    }

    public Result querySwRainTJ(String stcd, String searchTime, String type, String interval, int start, int limit){
        Result rs = new Result();
        List<CosstVo> listCosst = getSwjkCosst(stcd, type, "", "");

        try {
            for (CosstVo csvo : listCosst) {
                String time = searchTime.replaceAll("-", "").replaceAll(" ", "");

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                calendar.setTime(simpleDateFormat.parse(time));
                calendar.add(Calendar.DAY_OF_MONTH, -1);

                if (!StringUtil.isBlank(csvo.getStcd())){
                    if(!"00".equals(interval)){
                        csvo.setListPptn(rwJktjDao.querySwRainTJ(stcd, time, time + interval));
                    }
                    else {
                        csvo.setListPptn(rwJktjDao.querySwRainTJ_00(stcd, (Integer.parseInt(time)) + "08"));
                    }
                }
            }

            rs.setCode(Result.SUCCESS);
            rs.setData(sortMap((List<Map<String, Object>>) BeanUtil.objectToMap(listCosst), start, limit));
        }
        catch (Exception ex){
            rs.setCode(Result.FAILURE);
            rs.setMsg(ex.getMessage());
        }

        return rs;
    }

    public Result querySwRainTJ2(String stcd, String type, String interval, int start, int limit){
        Result rs = new Result();
        List<CosstVo> listCosst = getSwjkCosst(stcd, type, "", "");

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHH");
        // 当前时间
        Date nowdate = new Date();
        String nowTime = df.format(nowdate);
        System.out.println("当前时间" + nowTime);
        //获取前几个小时的时间
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - Integer.parseInt(interval));
        String oldTime = df.format(calendar.getTime());

        for (CosstVo csvo : listCosst) {
            if (!StringUtil.isBlank(csvo.getStcd())){
                if(!"00".equals(interval)) {
                    csvo.setListPptn(rwJktjDao.querySwRainTJ2(stcd, nowTime, oldTime));
                }
                else{
                    csvo.setListPptn(rwJktjDao.querySwRainTJ2_00(stcd, nowTime));
                }
            }
        }

        rs.setCode(Result.SUCCESS);
        rs.setData(sortMap((List<Map<String, Object>>) BeanUtil.objectToMap(listCosst), start, limit));

        return rs;
    }

    public Result queryQbStationTJ(String stcd, String time, String cosId){
        Result rs = new Result();
        List<Map> dataResult = new ArrayList<>();
        Map<String,Map<String, Object>> stations = new HashMap<String,Map<String, Object>>();
        String type = "";
        boolean flag = false;
        int iCosId = 0;
        long starttime=System.currentTimeMillis();

        if (!StringUtil.isBlank(cosId)&&!"".equals(cosId)) {
            flag = true;
            iCosId = Integer.parseInt(cosId);
            type = cosId;
        }

        List<CosstVo> listCosst = getSwjkCosst(stcd, type, "", "");

        for (CosstVo csvo : listCosst) {
            if(!StringUtil.isBlank(csvo.getStcd())){
                stations.put(csvo.getStcd() + "|" + csvo.getId(), (Map<String, Object>) BeanUtil.objectToMap(csvo));
            }
        }

        if (flag && (iCosId == 1 || iCosId == 3)){
            dataResult.addAll((List<Map>) BeanUtil.objectToMap(rwJktjDao.getStcdZ_1Or3(time)));
        }
        else if(flag && (iCosId == 2 || iCosId == 4)){
            dataResult.addAll((List<Map>) BeanUtil.objectToMap(rwJktjDao.getStcdZ_2Or4(time)));
        }
        else{
            dataResult.addAll((List<Map>) BeanUtil.objectToMap(rwJktjDao.getStcdZ(time)));
        }

        //將rainlist嵌套入測站結果集中
        Map<String, Object> datamap =new HashMap<String, Object>();
        Iterator<Map> datatiter = dataResult.iterator();

        while(datatiter.hasNext()) {
            datamap=datatiter.next();

            if(datamap.get("STCD") != null && !StringUtil.isBlank(datamap.get("STCD").toString())){
                String id1=datamap.get("ID1").toString();
                String id2=datamap.get("ID2").toString();

                stations.remove(datamap.get("STCD").toString()+"|"+id1);
                stations.remove(datamap.get("STCD").toString()+"|"+id2);
            }
        }

        List<Map<String, Object>>  result = new ArrayList<Map<String, Object>>();
        Map<String,Map<String, Object>> id13stations = new HashMap<String,Map<String, Object>>();
        Map<String,Map<String, Object>> id24stations = new HashMap<String,Map<String, Object>>();
        Map<String,Map<String, Object>> id25stations = new HashMap<String,Map<String, Object>>();

        if(stations.size() > 0){
            StringBuffer id13Stcd=new StringBuffer();
            StringBuffer id24Stcd=new StringBuffer();
            StringBuffer id25Stcd=new StringBuffer();

            for (Map.Entry<String, Map<String, Object>> entry : stations.entrySet()) {
                Map<String,Object> map=stations.get(entry.getKey());
                if(Integer.parseInt(map.get("ID").toString())==1||Integer.parseInt(map.get("ID").toString())==3){
                    id13Stcd.append(",").append("'").append(stations.get(entry.getKey()).get("STCD")).append("'");
                    id13stations.put((String)stations.get(entry.getKey()).get("STCD"),stations.get(entry.getKey()));
                }else if(Integer.parseInt(map.get("ID").toString())==5){
                    id25Stcd.append(",").append("'").append(stations.get(entry.getKey()).get("STCD")).append("'");
                    id25stations.put((String)stations.get(entry.getKey()).get("STCD"),stations.get(entry.getKey()));
                }else{
                    id24Stcd.append(",").append("'").append(stations.get(entry.getKey()).get("STCD")).append("'");
                    id24stations.put((String)stations.get(entry.getKey()).get("STCD"),stations.get(entry.getKey()));
                }
            }

            if(id13Stcd.toString().length()>0){
                List<Map> vmapList = ycdbJktjDao.getStation_13(time + ":00:00", id13Stcd.substring(1));

                if(vmapList != null && vmapList.size() > 0){
                    for(Map tmp : vmapList){
                        id13stations.get(tmp.get("STCD")).putAll(tmp);
                    }
                }
            }

            if(id24Stcd.toString().length()>0){
                List<Map> sttpList = rwJktjDao.getStbprp(id24Stcd.substring(1));

                StringBuffer idrrStcd=new StringBuffer();
                StringBuffer idddStcd=new StringBuffer();
                StringBuffer iddpStcd=new StringBuffer();
                StringBuffer idppStcd=new StringBuffer();

                if(sttpList!=null&&sttpList.size()>0){
                    for(Map<String,Object> tmp:sttpList){
                        //河道水位站提取的是ST_RIVER_R 里的水位（Z）字段，水库水位站提取的是ST_RSVR_R 的库上水位（RZ），堰闸站提取的是ST_WAS_R 里的闸上水位(UPZ)。
                        if(!StringUtil.isBlank(tmp.get("STTP").toString()) && "RR".equals(tmp.get("STTP").toString())){
                            idrrStcd.append(",").append("'").append(tmp.get("STCD")).append("'");
                        }else if(!StringUtil.isBlank(tmp.get("STTP").toString()) && "DD".equals(tmp.get("STTP").toString())){
                            idddStcd.append(",").append("'").append(tmp.get("STCD")).append("'");
                        }else if(!StringUtil.isBlank(tmp.get("STTP").toString()) && "DP".equals(tmp.get("STTP").toString())){
                            iddpStcd.append(",").append("'").append(tmp.get("STCD")).append("'");
                        }else{
                            idppStcd.append(",").append("'").append(tmp.get("STCD")).append("'");
                        }
                    }
                }

                List<Map> vmapList = ycdbJktjDao.getStation_24(time, (idrrStcd.toString().length() > 0 ? idrrStcd.substring(1) : ""), (idddStcd.toString().length() > 0 ? idddStcd.substring(1) : ""), (iddpStcd.toString().length() > 0 ? iddpStcd.substring(1) : ""), (idppStcd.toString().length() > 0 ? idppStcd.substring(1) : ""));

                if(vmapList!=null&&vmapList.size()>0){
                    for(Map<String,Object> tmp:vmapList){
                        id24stations.get(tmp.get("STCD")).putAll(tmp);
                    }
                }
            }

            for(Map.Entry<String, Map<String, Object>> entry:id13stations.entrySet()){
                result.add(entry.getValue());
            }
            for(Map.Entry<String, Map<String, Object>> entry:id24stations.entrySet()){
                result.add(entry.getValue());
            }
        }

        long endtime=System.currentTimeMillis();
        System.out.println("查询耗时："+((endtime-starttime)/1000.0)+"秒。");

        rs.setCode(Result.SUCCESS);
        rs.setData(sortMap(result, 1, 10));

        return rs;
    }

    private List<Map<String,Object>> sortMap(List<Map<String,Object>> map,int start, int limit){
        Collections.sort(map,new Comparator<Map<String,Object>>() {
            public int compare(Map<String, Object> o1,Map<String, Object> o2) {
                //o1，o2是list中的Map，可以在其内取得值，按其排序，此例为升序，s1和s2是排序字段值
                Double s1=0.0,s2=0.0;
                Iterator<Map.Entry<String,Object>> it1 = o1.entrySet().iterator();

                while (it1.hasNext()) {
                    Map.Entry<String, Object> entry = it1.next();

                    if("TJ".equals(entry.getKey().trim())){
                        if(entry.getValue()!=null){
                            s1=Double.parseDouble(entry.getValue().toString());
                        }
                    }
                }

                Iterator<Map.Entry<String,Object>> it2 = o2.entrySet().iterator();

                while (it2.hasNext()) {
                    Map.Entry<String, Object> entry = it2.next();

                    if("TJ".equals(entry.getKey().trim())){
                        if(entry.getValue()!=null){
                            s2=Double.parseDouble(entry.getValue().toString());
                        }
                    }
                }

                if(s2!=null){
                    return s2.compareTo(s1);
                }

                return -1;
            }
        });

        int end=start*limit==0?limit:(start+1)*limit;

        if(end>=map.size()){
            end=map.size();
        }

        start=start*limit==0?0:start*limit-1;

        return map.subList(start,end);
    }

    private List<Priviliges> getRights(){
        User u = SessionCache.get();
        String userId = u.getUserCode();

        return rightDao.getRightByUserId(userId);
    }

    private List<CosstVo> getSwjkCosst(String stcd, String type, String custom, String addvcd){
        List<Priviliges> rightList = getRights();

        if(rightList.size() > 0 && Integer.parseInt(rightList.get(0).getLevel())==3) {
            return cosstDao.getSwRainCosst_Level3(rightList.get(0).getPriviligeId(), stcd, type, custom, addvcd);
        }

        return cosstDao.getSwRainCosst(stcd, type, custom, addvcd);
    }

    public void exportSwRainTJ(String stcd, String time, String type, String interval, String filename, HttpServletResponse response, int start, int limit) throws IOException {
        Result r = querySwRainTJ(stcd, time, type, interval, start, limit);

        if (r.isSuccess()){
            List<Map<String,Object>> list = (List<Map<String, Object>>) r.getData();
            String fileName = time + ("00".equals(interval)?" 日雨量——":" " + interval + "点——") + new String(filename.getBytes("ISO-8859-1"), "UTF-8");

            HSSFWorkbook wb = getSwRainTJWb(list, fileName);

            ServletOutputStream outStream = null;

            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename="+ new String(filename.getBytes("gb2312"), "ISO-8859-1")
                    + ".xls");
            outStream = response.getOutputStream();
            wb.write(outStream);
            outStream.flush();
            outStream.close();
        }
    }

    private HSSFWorkbook getSwRainTJWb(List<Map<String,Object>> valuelist, String filename) {
        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet();
        sheet.setDefaultColumnWidth(32);
        sheet.setDefaultRowHeightInPoints(35);;

        // 标题样式
        HSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 单元格标题样式
        HSSFCellStyle columnStyle = wb.createCellStyle();
        columnStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFFont f = wb.createFont();
        f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
        columnStyle.setFont(f);
        titleStyle.setFont(f);
        // 单元格列内容的样式
        HSSFCellStyle valueStyle = wb.createCellStyle();
        valueStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_LEFT);// 垂直居中
        valueStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

        String[] cellNameArray = new String[] { "测站编码","测站名称","时段累计雨量","测站地址"};
        String[] cellColumnArray = new String[] { "STCD", "STNM", "TJ","STLC"};

        HSSFRow row = sheet.createRow((short) 0);
        row.setHeight((short) 500);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, cellNameArray.length-1));

        // 第一行（标题）
        HSSFCell ce = row.createCell(0);

        // 设置标题
        ce.setCellValue(filename);
        ce.setCellStyle(titleStyle);
        // 设置列名
        HSSFRow row2 = sheet.createRow((short) 1);

        // 设置列名
        for (int i = 0; i < cellNameArray.length; i++) {
            HSSFCell cellfield = row2.createCell(i);
            cellfield.setCellStyle(columnStyle);
            cellfield.setCellValue(cellNameArray[i]);
        }

        String coulumnName = null;
        Map valueMap = null;

        for (int i = 0; i < valuelist.size(); i++) {
            HSSFRow rowtemp = sheet.createRow((i + 2));
            valueMap = valuelist.get(i);
            for (int j = 0; j < cellColumnArray.length; j++) {
                HSSFCell cellvalue = rowtemp.createCell(j);
                coulumnName = cellColumnArray[j];
                cellvalue.setCellStyle(valueStyle);
                Iterator<Map.Entry<String,Object>> it1 = valueMap.entrySet().iterator();
                boolean flag=true;
                while (it1.hasNext()) {
                    Map.Entry<String, Object> entry = it1.next();
                    if(coulumnName.equals(entry.getKey().trim())){
                        try {
                            cellvalue.setCellValue(entry.getValue()==null?null:entry.getValue().toString());
                            flag=false;
                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                if(flag){
                    cellvalue.setCellValue("");
                }
            }
        }

        return wb;
    }
}
