package com.goldwater.querycenter.service.ruku;

import com.github.pagehelper.PageHelper;
import com.goldwater.querycenter.common.util.BeanUtil;
import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.common.util.cache.SessionCache;
import com.goldwater.querycenter.common.util.string.StringUtil;
import com.goldwater.querycenter.dao.rtuex.RtuStationDao;
import com.goldwater.querycenter.dao.ycdb.management.RightDao;
import com.goldwater.querycenter.dao.ycdb.ruku.RainDao;
import com.goldwater.querycenter.entity.management.Priviliges;
import com.goldwater.querycenter.entity.management.User;
import com.goldwater.querycenter.entity.ruku.RtuStation;
import com.goldwater.querycenter.entity.ruku.vo.FiveMinuteRainVo;
import com.goldwater.querycenter.entity.ruku.vo.FlowR5Vo;
import com.goldwater.querycenter.entity.ruku.vo.MessageVo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RainService {
    @Autowired
    private RightDao rightDao;

    @Autowired
    private RainDao rainDao;

    @Autowired
    private RtuStationDao rtuStationDao;

    public Result queryRainFm(String stcd, String startTm, String endTm, int pageIndex, int length){
        Result rs = new Result();
        List<FiveMinuteRainVo> list = new ArrayList<>();

        User u = SessionCache.get();
        String userId = u.getUserCode();

        List<Priviliges> rightList = rightDao.getRightByUserId(userId);

        PageHelper.startPage(pageIndex, length);

        if(rightList.size() > 0 && Integer.parseInt(rightList.get(0).getLevel())==3){
            list = rainDao.queryFiveMinuteRain_Level3(rightList.get(0).getPriviligeId(), stcd, startTm, endTm);
        }
        else {
            list = rainDao.queryFiveMinuteRain(stcd, startTm, endTm);
        }

        rs.setData(list);
        rs.setTotal(Integer.parseInt(list.size() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result queryRainChart(String stcd, String time){
        Result rs = new Result();

        rs.setData(rainDao.queryRainChart(stcd,time));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public void exportRain5m(String stdm, String startTm, String endTm, HttpServletRequest request, HttpServletResponse response)  throws IOException {
        //测站和起始时间为必输项
        if(!StringUtil.isBlank(stdm)&&!StringUtil.isBlank(startTm)){
            List<RtuStation> list = rtuStationDao.queryStcdStnm(stdm, "", "");

            String filename="";

            if (list.size() > 0 && list.get(0).getStnm() !=null){
                filename = list.get(0).getStnm();
            }

            filename = filename + "("+stdm+")";

            HSSFWorkbook wb = getRain5mWb(stdm, startTm, endTm, filename);

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

    private HSSFWorkbook getRain5mWb(String stcd, String startTm, String endTm, String filename){
        HSSFWorkbook wb = new HSSFWorkbook();

        User u = SessionCache.get();
        String userId = u.getUserCode();

        List<Priviliges> rightList = rightDao.getRightByUserId(userId);

        List<FiveMinuteRainVo> list = new ArrayList<>();

        if(rightList.size() > 0 && Integer.parseInt(rightList.get(0).getLevel())==3){
            list = rainDao.queryFiveMinuteRain_Level3(rightList.get(0).getPriviligeId(), stcd, startTm, endTm);
        }
        else {
            list = rainDao.queryFiveMinuteRain(stcd, startTm, endTm);
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

            String[] cellNameArray = new String[] { "测站编码", "时间", "时段降水量"};
            String[] cellColumnArray = new String[] { "STCD", "TM", "DRP"};

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
