package com.goldwater.querycenter.service.ruku;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goldwater.querycenter.common.util.BeanUtil;
import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.common.util.cache.SessionCache;
import com.goldwater.querycenter.common.util.string.StringUtil;
import com.goldwater.querycenter.dao.rtuex.RtuStationDao;
import com.goldwater.querycenter.dao.ycdb.management.RightDao;
import com.goldwater.querycenter.dao.ycdb.ruku.FlowDao;
import com.goldwater.querycenter.entity.management.Priviliges;
import com.goldwater.querycenter.entity.management.User;
import com.goldwater.querycenter.entity.ruku.PickFlux;
import com.goldwater.querycenter.entity.ruku.RtuStation;
import com.goldwater.querycenter.entity.ruku.vo.FlowR5Vo;
import com.goldwater.querycenter.entity.ruku.vo.FluxR5Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.IOException;

@Service
public class FlowService {
    @Autowired
    private FlowDao flowDao;

    @Autowired
    private RightDao rightDao;

    @Autowired
    private RtuStationDao rtuStationDao;

    public Result queryFlow_R5(String stcd, String startTm, String endTm, int pageIndex, int length){
        Result rs = new Result();

        User u = SessionCache.get();
        String userId = u.getUserCode();

        List<Priviliges> rightList = rightDao.getRightByUserId(userId);
        List<FlowR5Vo> list = new ArrayList<>();

        PageHelper.startPage(pageIndex, length);

        if(rightList.size() > 0 && Integer.parseInt(rightList.get(0).getLevel())==3){
            list = flowDao.queryFlow_R5_Level3(rightList.get(0).getPriviligeId(), stcd, startTm, endTm);
        }
        else {
            list = flowDao.queryFlow_R5(stcd, startTm, endTm);
        }

        PageInfo p = new PageInfo<>(list);
        rs.setData(p);
        rs.setTotal(Integer.parseInt(p.getTotal() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result queryFluxFm(String stcd, String startTm, String endTm){
        Result rs = new Result();

        User u = SessionCache.get();
        String userId = u.getUserCode();

        List<Priviliges> rightList = rightDao.getRightByUserId(userId);

        List<FluxR5Vo> list = new ArrayList<>();

        if(rightList.size() > 0 && Integer.parseInt(rightList.get(0).getLevel())==3){
            list = flowDao.queryFlux_R5_Level3(rightList.get(0).getPriviligeId(), stcd, startTm, endTm);
        }
        else {
            list = flowDao.queryFlux_R5(stcd, startTm, endTm);
        }

        PageInfo p = new PageInfo<>(list);
        rs.setData(p);
        rs.setTotal(Integer.parseInt(p.getTotal() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result getPickFluxList(String stcd, String enterFlow, int pageIndex, int length){
        Result rs = new Result();
        PageHelper.startPage(pageIndex, length);

        List<PickFlux> list = flowDao.getPickFluxList(stcd, enterFlow);

        PageInfo p = new PageInfo<>(list);
        rs.setData(p);
        rs.setTotal(Integer.parseInt(p.getTotal() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result addPickFlux(String stcd, String stnm, String enterFlow){
        Result rs = new Result();
        PickFlux pf = new PickFlux();
        Map map = new HashMap();

        pf.setStcd(stcd);
        pf.setStnm(stnm);
        pf.setEnterFlow(enterFlow);

        if (flowDao.insertSelective(pf) > 0){
            map.put("ERRNO", "0");

            rs.setCode(Result.SUCCESS);
        }
        else{
            map.put("ERRNO", "ERR01");
            map.put("ERRMAS", "添加开关泵闸信息失败");

            rs.setCode(Result.FAILURE);
        }

        rs.setData(map);

        return rs;
    }

    public Result checkPickFlow(String stcd, String stnm){
        Result rs = new Result();
        PickFlux pf = new PickFlux();
        Map<String,Object> map = new HashMap<String,Object>();

        pf.setStcd(stcd);
        pf.setStnm(stnm);

        List<PickFlux> list = flowDao.select(pf);

        if(list.size() < 1)
        {
            map.put("STATUS", false);
        }
        else
        {
            map.put("STATUS", true);
        }

        rs.setData(map);
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result updatePickFlow(String stcd, String stnm, String enterFlow, String oldStcd, String oldStnm){
        Result rs = new Result();
        Map map = new HashMap();

        if (flowDao.updatePickFlow(stcd, stnm, enterFlow, oldStcd, oldStnm) > 0){
            map.put("ERRNO", "0");

            rs.setCode(Result.SUCCESS);
        }
        else{
            map.put("ERRNO", "ERR01");
            map.put("ERRMAS", "修改开关泵闸信息失败");

            rs.setCode(Result.FAILURE);
        }

        rs.setData(map);

        return rs;
    }

    public Result delPickFlow(String ids){
        Result rs = new Result();
        Map map = new HashMap();

        if(!ids.equals("")){
            List<Map> list = getDelPickFlows(ids);

            flowDao.delPickFlow(list);

            map.put("ERRNO", "0");

            rs.setCode(Result.SUCCESS);
        }
        else{
            map.put("ERRNO", "ERR01");
            map.put("ERRMAS", "删除开关泵闸信息失败");

            rs.setCode(Result.FAILURE);
        }

        rs.setData(map);

        return rs;
    }

    private List<Map> getDelPickFlows(String ids){
        List<Map> list = new ArrayList<>();
        String[] idlist = ids.split(";");

        for (int i = 0; i < idlist.length; i++) {
            String id = idlist[i];

            if(!"".equals(id)){
                String [] cds = id.split(",");
                Map m = new HashMap();

                m.put("stcd", cds[0]);
                m.put("stnm", cds[1]);

                list.add(m);
            }
        }

        return list;
    }

    public Result getPickFlux(String stcd, String stnm){
        Result rs = new Result();

        rs.setData(flowDao.getPickFlux(stcd, stnm));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public void exportFlow5r(String stdm, String startTm, String endTm, HttpServletResponse response) throws IOException{
        //测站和起始时间为必输项
        if(!StringUtil.isBlank(stdm)&&!StringUtil.isBlank(startTm)){
            List<RtuStation> list = rtuStationDao.queryStcdStnm(stdm, "", "");
            String filename="";

            if (list.size() > 0 && list.get(0).getStnm() !=null){
                filename = list.get(0).getStnm();
            }

            filename = filename + "("+stdm+")";

            HSSFWorkbook wb = getFlowR5Wb(stdm, startTm, endTm, filename);

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

    private HSSFWorkbook getFlowR5Wb(String stcd, String startTm, String endTm, String filename){
        HSSFWorkbook wb = new HSSFWorkbook();

        User u = SessionCache.get();
        String userId = u.getUserCode();

        List<Priviliges> rightList = rightDao.getRightByUserId(userId);

        List<FlowR5Vo> list = new ArrayList<>();

        if(rightList.size() > 0 && Integer.parseInt(rightList.get(0).getLevel())==3){
            list = flowDao.queryFlow_R5_Level3(rightList.get(0).getPriviligeId(), stcd, startTm, endTm);
        }
        else {
            list = flowDao.queryFlow_R5(stcd, startTm, endTm);
        }

        int sheetCount=1;

        if(list.size() % 65534 == 0){
            sheetCount = list.size() / 65534;
        }else{
            sheetCount += list.size() / 65534;
        }

        for(int count = 0; count < sheetCount; count++){
            HSSFSheet sheet = wb.createSheet(filename+"("+count+")");
            // 标题样式
            HSSFCellStyle titleStyle = wb.createCellStyle();
            titleStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

            // 单元格标题样式
            HSSFCellStyle columnStyle = wb.createCellStyle();
            columnStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            HSSFFont f = wb.createFont();
            f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
            columnStyle.setFont(f);
            // 单元格列内容的样式
            HSSFCellStyle valueStyle = wb.createCellStyle();
            valueStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            String[] cellNameArray = new String[] { "测站编码", "时间", "流速", "点序号"};
            String[] cellColumnArray = new String[] { "STCD", "TM", "FLOW", "DEVNO"};

            HSSFRow row = sheet.createRow((short) 0);
            row.setHeight((short) 500);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,
                    cellNameArray.length - 1));

            // 第一行（标题）
            HSSFCell ce = row.createCell(0);
            String ceTitle = filename + "  " + startTm;
            if (endTm != null) {
                ceTitle = ceTitle + " —— " + endTm;
            }
            // 设置标题
            ce.setCellValue(ceTitle);
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
            int maxTimes=(count+1)*65534>list.size()?list.size():(count+1)*65534;
            int rowindex=0;
            for (int i = count*65534; i<maxTimes;i++) {
                HSSFRow rowtemp = sheet.createRow((rowindex + 2));
                valueMap = BeanUtil.objectToMap(list.get(i));
                for (int j = 0; j < cellColumnArray.length; j++) {
                    HSSFCell cellvalue = rowtemp.createCell(j);
                    coulumnName = cellColumnArray[j];
                    cellvalue.setCellStyle(valueStyle);
                    if (valueMap.get(coulumnName) != null) {
                        cellvalue.setCellValue(valueMap.get(coulumnName)
                                .toString());
                    } else {
                        cellvalue.setCellValue("");
                    }
                }
                rowindex++;
            }
        }

        return wb;
    }

    public void exportFlux5r(String stdm, String startTm, String endTm, HttpServletResponse response) throws IOException{
        //测站和起始时间为必输项
        if(!StringUtil.isBlank(stdm)&&!StringUtil.isBlank(startTm)){
            List<RtuStation> list = rtuStationDao.queryStcdStnm(stdm, "", "");
            String filename="";

            if (list.size() > 0 && list.get(0).getStnm() !=null){
                filename = list.get(0).getStnm();
            }

            filename = filename + "("+stdm+")";

            HSSFWorkbook wb = getFluxR5Wb(stdm, startTm, endTm, filename);

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

    private HSSFWorkbook getFluxR5Wb(String stcd, String startTm, String endTm, String filename){
        HSSFWorkbook wb = new HSSFWorkbook();

        User u = SessionCache.get();
        String userId = u.getUserCode();

        List<Priviliges> rightList = rightDao.getRightByUserId(userId);

        List<FluxR5Vo> list = new ArrayList<>();

        if(rightList.size() > 0 && Integer.parseInt(rightList.get(0).getLevel())==3){
            list = flowDao.queryFlux_R5_Level3(rightList.get(0).getPriviligeId(), stcd, startTm, endTm);
        }
        else {
            list = flowDao.queryFlux_R5(stcd, startTm, endTm);
        }

        int sheetCount=1;

        if(list.size() % 65534 == 0){
            sheetCount = list.size() / 65534;
        }else{
            sheetCount += list.size() / 65534;
        }

        for(int count = 0; count < sheetCount; count++){
            HSSFSheet sheet = wb.createSheet(filename+"("+count+")");
            // 标题样式
            HSSFCellStyle titleStyle = wb.createCellStyle();
            titleStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

            // 单元格标题样式
            HSSFCellStyle columnStyle = wb.createCellStyle();
            columnStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            HSSFFont f = wb.createFont();
            f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
            columnStyle.setFont(f);
            // 单元格列内容的样式
            HSSFCellStyle valueStyle = wb.createCellStyle();
            valueStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            String[] cellNameArray = new String[] { "测站编码", "时间", "流量"};
            String[] cellColumnArray = new String[] { "STCD", "TM", "FLUX"};

            HSSFRow row = sheet.createRow((short) 0);
            row.setHeight((short) 500);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,
                    cellNameArray.length - 1));

            // 第一行（标题）
            HSSFCell ce = row.createCell(0);
            String ceTitle = filename + "  " + startTm;
            if (endTm != null) {
                ceTitle = ceTitle + " —— " + endTm;
            }
            // 设置标题
            ce.setCellValue(ceTitle);
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
            int maxTimes=(count+1)*65534>list.size()?list.size():(count+1)*65534;
            int rowindex=0;
            for (int i = count*65534; i<maxTimes;i++) {
                HSSFRow rowtemp = sheet.createRow((rowindex + 2));
                valueMap = BeanUtil.objectToMap(list.get(i));
                for (int j = 0; j < cellColumnArray.length; j++) {
                    HSSFCell cellvalue = rowtemp.createCell(j);
                    coulumnName = cellColumnArray[j];
                    cellvalue.setCellStyle(valueStyle);
                    if (valueMap.get(coulumnName) != null) {
                        cellvalue.setCellValue(valueMap.get(coulumnName)
                                .toString());
                    } else {
                        cellvalue.setCellValue("");
                    }
                }
                rowindex++;
            }
        }

        return wb;
    }
}
