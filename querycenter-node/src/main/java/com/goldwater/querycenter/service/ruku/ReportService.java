package com.goldwater.querycenter.service.ruku;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.dao.rw.ruku.ReportDao;
import com.goldwater.querycenter.entity.ruku.ReportConfigMetaData;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Service
public class ReportService {
    @Autowired
    private ReportDao reportDao;

    private File xlsFile;

    public File getXlsFile() {
        return xlsFile;
    }

    public void setXlsFile(File xlsFile) {
        this.xlsFile = xlsFile;
    }

    public Result getSqjbList(String rid, int pageIndex, int length){
        Result rs = new Result();

        List<ReportConfigMetaData> metaList = reportDao.getReportConfigMetaData(rid);

        PageHelper.startPage(pageIndex, length);

        List<Map<String, Object>> list = reportDao.getSqjbList(rid, metaList);

        PageInfo p = new PageInfo<>(list);
        rs.setData(p);
        rs.setData(list);
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result addSqjb(String lb, String stnm, String stcd, String rid){
        Result rs = new Result();
        Map map = new HashMap();

        List<Map> list = getInsertList(lb, stnm, stcd, rid, 1);

        if (reportDao.addSqjb(list) > 0){
            map.put("ERRNO", "0");

            rs.setCode(Result.SUCCESS);
        }
        else{
            map.put("ERRNO", "ERR01");
            map.put("ERRMAS", "添加数据失败");

            rs.setCode(Result.FAILURE);
        }

        rs.setData(map);

        return rs;
    }

    public Result deleteSqjb(String rid, String identifys){
        Result rs = new Result();
        Map map = new HashMap();

        List<ReportConfigMetaData> metaList = reportDao.getReportConfigMetaData(rid);

        if(null != metaList && metaList.size() > 0) {
            List<Map> list = new ArrayList<>();
            String[] identify = identifys.split(";");

            for (int k = 0; k < identify.length; k++) {
                for (int i = 0; i < metaList.size(); i++) {

                    Map m = new HashMap();

                    m.put("mid", metaList.get(i).getMid());
                    m.put("identify", identify[k]);

                    list.add(m);
                }
            }

            if (reportDao.deleteSqjb(list) > 0){
                map.put("bol", true);
                map.put("ERRNO", "0");

                rs.setCode(Result.SUCCESS);
            }
            else{
                map.put("ERRNO", "ERR01");
                map.put("ERRMAS", "删除数据失败");

                rs.setCode(Result.FAILURE);
            }
        }

        rs.setData(map);

        return rs;
    }

    private List<Map> getInsertList(String lb, String stnm, String stcd, String rid,int sorti){
        List<Map> list = new ArrayList<>();

        List<ReportConfigMetaData> metaList = reportDao.getReportConfigMetaData(rid);

        if(null != metaList && metaList.size() > 0) {
            int sort = reportDao.getMaxSortValue(metaList.get(0).getMid());

            sort+=sorti;

            for (int i = 0; i < metaList.size(); i++) {
                Map m = new HashMap();
                ReportConfigMetaData meta = metaList.get(i);

                m.put("mid", meta.getMid());
                m.put("sortValue", sort);
                m.put("identify", sort);
                putFieldValue(meta.getFieldName(), lb, stnm, stcd, m);


                list.add(m);
            }
        }

        return list;
    }

    private void putFieldValue(String fieldName, String lb, String stnm, String stcd, Map m){
        switch(fieldName) {
            case "水系":
                m.put("fieldValue", lb);
                break;
            case "类别":
                m.put("fieldValue", lb);
                break;
            case "河名":
                m.put("fieldValue", lb);
                break;
            case "类型":
                m.put("fieldValue", lb);
                break;
            case "站别":
                m.put("fieldValue", lb);
                break;
            case "县市区名":
                m.put("fieldValue", lb);
                break;
            case "工程名称":
                m.put("fieldValue", lb);
                break;
            case "站名":
                m.put("fieldValue", stnm);
                break;
            case "站码":
                m.put("fieldValue", stcd);
                break;
        }
    }

    public void exportSqjb(String rid, HttpServletResponse response) throws IOException {
        String filename = getExportSqjbFileName(rid);

        HSSFWorkbook wb = getSqjbWorkbook(rid);

        ServletOutputStream outStream = null;

        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment; filename="+ new String(filename.getBytes("gb2312"), "ISO-8859-1")
                + ".xls");
        outStream = response.getOutputStream();
        wb.write(outStream);
        outStream.flush();
        outStream.close();
    }

    private String getExportSqjbFileName(String rid){
        String filename ="";

        switch (rid) {
            case "1":
                filename = "sqjbhd";
                break;
            case "2":
                filename = "sqjbsk";
                break;
            case "3":
                filename = "sqjbyz";
                break;
            case "4":
                filename = "sqjbbz";
                break;
            case "5":
                filename = "sqjbhp";
                break;
            case "6":
                filename = "dxssq";
                break;
            case "7":
                filename = "sksq";
                break;
            case "8":
                filename = "yqjb";
                break;
            case "9":
                filename = "jhzykzz";
                break;
            case "10":
                filename = "yjjhsq";
                break;
            case "11":
                filename = "dxmk";
                break;
            case "12":
                filename = "qszbsq";
                break;
            case "13":
                filename = "qsgxzhsq";
                break;
            default:
                filename = "bbpzxx";
                break;
        }

        return filename;
    }

    private HSSFWorkbook getSqjbWorkbook(String rid) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(rid);
        String[] cellNameArray = new String[3];
        String[] cellColumnArray = new String[] { "STCD", "TM", "DRP"};

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
        valueStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
        valueStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        List<ReportConfigMetaData> metaList = reportDao.getReportConfigMetaData(rid);

        if(null != metaList && metaList.size() > 0) {
            for (int i = 0; i < metaList.size(); i++) {
                ReportConfigMetaData meta = metaList.get(i);

                if(i==1 && "站码".equals(meta.getFieldName())) {
                    cellNameArray[2] = meta.getFieldName();
                }else if(i==2 && "站名".equals(meta.getFieldName())){
                    cellNameArray[1] = meta.getFieldName();
                }else {
                    cellNameArray[i] = meta.getFieldName();
                }
            }
        }

        // 设置列名
        HSSFRow row2 = sheet.createRow((short) 0);

        // 设置列名
        for (int i = 0; i < cellNameArray.length; i++) {
            HSSFCell cellfield = row2.createCell(i);
            cellfield.setCellStyle(columnStyle);
            cellfield.setCellValue(cellNameArray[i]);
        }

        // 传入要导出的报表类型即可
        List<Map<String, Object>> valuelist = reportDao.getSqjbList(rid, metaList);

        String coulumnName = null;
        Map valueMap = null;

        for (int i = 0; i < valuelist.size(); i++) {
            HSSFRow rowtemp = sheet.createRow((i + 1));
            valueMap = valuelist.get(i);

            for (int j = 0; j < cellNameArray.length; j++) {
                HSSFCell cellvalue = rowtemp.createCell(j);
                coulumnName = cellNameArray[j];
                cellvalue.setCellStyle(valueStyle);

                if (valueMap.get(coulumnName) != null) {
                    cellvalue.setCellValue(valueMap.get(coulumnName).toString());
                } else {
                    cellvalue.setCellValue("");
                }
            }
        }

        return wb;
    }

    public void downloadXlsFile(String fileName, HttpServletResponse response, InputStream is) throws IOException {
        ServletOutputStream outStream = null;

        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        outStream = response.getOutputStream();
        byte[] data=new byte[1024];

        while(is.read(data)!=-1){
            outStream.write(data);
        }

        outStream.flush();
        outStream.close();

        if(outStream!=null){
            outStream.close();
        }
    }

    public Result importSqjb(String rid, HttpServletResponse response) throws IOException {
        Result rs = new Result();
        Map resultMap = new HashMap<>();
        PrintWriter out = response.getWriter();
        FileInputStream fileIn;
        long startTime=System.currentTimeMillis();
        fileIn = new FileInputStream(xlsFile);

        //根据指定的文件输入流导入Excel从而产生Workbook对
        HSSFWorkbook wb0 = new HSSFWorkbook(fileIn);
        //获取Excel文档中的第一个表单
        Iterator<Row> rowIter = wb0.getSheetAt(0).rowIterator();

        //对Sheet中的每一行进行迭代
        List<Object[]> listParam=new ArrayList<Object[]>();
        while(rowIter.hasNext()){
            Row row=rowIter.next();
            if(row.getRowNum()>0){
                row.getCell(0).setCellType(HSSFCell.CELL_TYPE_STRING);
                row.getCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);
                row.getCell(2).setCellType(HSSFCell.CELL_TYPE_STRING);

                if(row.getCell(0).getStringCellValue().trim().equals("")){
                    break;
                }

                Object[] obj=new Object[]{
                        row.getCell(0).getStringCellValue().trim(),
                        row.getCell(1).getStringCellValue().trim(),
                        row.getCell(2).getStringCellValue().trim()};

                listParam.add(obj);
            }
        }

        List<Map> listMap = new ArrayList<>();

        if(null != listParam && listParam.size() > 0){
            for(int i=0 ;i<listParam.size();){
                String lb = listParam.get(i)[0].toString();
                String stnm = listParam.get(i)[1].toString();
                String stcd = listParam.get(i)[2].toString();

                listMap.addAll(getInsertList(lb, stnm, stcd, rid,++i));
            }
        }

        if (reportDao.addSqjb(listMap) > 0){
            resultMap.put("ERRNO", "0");

            rs.setCode(Result.SUCCESS);
        }
        else{
            resultMap.put("ERRNO", "ERR01");
            resultMap.put("ERRMAS", "部分数据插入失败");

            rs.setCode(Result.FAILURE);
        }

        long endTime =System.currentTimeMillis();
        System.out.println("批量执行时间:"+((endTime-startTime)/1000)+"秒。");

        out.flush();
        out.close();

        rs.setData(resultMap);

        return rs;
    }
}
