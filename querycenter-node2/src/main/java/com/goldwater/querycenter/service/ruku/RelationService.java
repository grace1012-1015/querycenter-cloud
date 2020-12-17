package com.goldwater.querycenter.service.ruku;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.common.util.cache.SessionCache;
import com.goldwater.querycenter.common.util.string.StringUtil;
import com.goldwater.querycenter.dao.rtuex.RtuStationDao;
import com.goldwater.querycenter.dao.rw.ruku.RwRelationDao;
import com.goldwater.querycenter.dao.ycdb.management.RightDao;
import com.goldwater.querycenter.dao.ycdb.ruku.CosstDao;
import com.goldwater.querycenter.dao.ycdb.ruku.YcdbRelationDao;
import com.goldwater.querycenter.entity.management.Priviliges;
import com.goldwater.querycenter.entity.management.User;
import com.goldwater.querycenter.entity.ruku.Cosst;
import com.goldwater.querycenter.entity.ruku.RtuStation;
import com.goldwater.querycenter.entity.ruku.StConfig;
import com.goldwater.querycenter.entity.ruku.vo.CosstVo;
import com.goldwater.querycenter.entity.ruku.vo.ZqrlRelationVo;
import com.goldwater.querycenter.entity.ruku.vo.ZvarlRelationVo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RelationService {
    private File xlsFile;

    @Autowired
    private YcdbRelationDao ycdbRelationDao;

    @Autowired
    private RwRelationDao rwRelationDao;

    @Autowired
    private CosstDao cosstDao;

    @Autowired
    private RightDao rightDao;

    @Autowired
    private RtuStationDao rtuStationDao;

    public File getXlsFile() {
        return xlsFile;
    }

    public void setXlsFile(File xlsFile) {
        this.xlsFile = xlsFile;
    }

    public Result getZqrlList(String stcd, int pageIndex, int length){
        Result rs = new Result();

        User u = SessionCache.get();
        String userId = u.getUserCode();

        List<Priviliges> rightList = rightDao.getRightByUserId(userId);

        List<ZqrlRelationVo> list = new ArrayList<>();

        PageHelper.startPage(pageIndex, length);

        if(rightList.size() > 0 && Integer.parseInt(rightList.get(0).getLevel())==3){
            list = ycdbRelationDao.queryZqrlList_Level3(rightList.get(0).getPriviligeId(), stcd);
        }
        else {
            list = ycdbRelationDao.queryZqrlList(stcd);
        }

        PageInfo p = new PageInfo<>(list);
        rs.setData(p);
        rs.setTotal(Integer.parseInt(p.getTotal() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result addZqrl(String stcd, String ptno, String z, String q){
        Result rs = new Result();
        int result = 0;
        Map map = new HashMap();

        // 点序号不为空时说明是更新数据
        if(ptno != null && !StringUtil.isBlank(ptno)){
            result = rwRelationDao.updateZqrl(stcd, ptno, z, q);
        }
        else {
            //点序号为空时，说明是新增数据，根据测站编码自增长
            String maxPtno = rwRelationDao.getMaxZqrlPtno(stcd);

            if(null == maxPtno || "".equals(maxPtno)){
                maxPtno="1";
            }else{
                maxPtno=String.valueOf(Integer.valueOf(ptno)+1);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date date = new Date();
            String yr = sdf.format(date);

            result = rwRelationDao.insertZqrl(stcd, "水位流量曲线", yr, maxPtno, z, q);
        }

        if (result > 0){
            map.put("ERRNO", "0");

            rs.setCode(Result.SUCCESS);
        }
        else{
            map.put("ERRNO", "ERR01");
            map.put("ERRMAS", "添加水位流量关系失败");

            rs.setCode(Result.FAILURE);
        }

        rs.setData(map);

        return rs;
    }

    public Result getZqrl(String stcd, String ptno){
        Result rs = new Result();

        //rs.setData(relationDao.getZqrl(stcd,ptno));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result deleteZqrl(String stcd_ptno){
        Result rs = new Result();
        Map map = new HashMap();

        if (!"".equals(stcd_ptno) && !"null".equals(stcd_ptno)) {
            String[] stcd_ptnoarr = stcd_ptno.split(";");
            List<Map> list = new ArrayList<>();

            for (int i = 0; i < stcd_ptnoarr.length; i++) {
                Map m = new HashMap();

                String[] stcdarr = stcd_ptnoarr[i].split(",");

                m.put("stcd", stcdarr[0]);
                m.put("ptno", stcdarr[1]);

                list.add(m);
            }

            if (ycdbRelationDao.deleteZqrl(list) > 0){
                map.put("ERRNO", "0");

                rs.setCode(Result.SUCCESS);
            }
            else{
                map.put("ERRNO", "ERR01");
                map.put("ERRMAS", "删除水位流量关系失败");

                rs.setCode(Result.FAILURE);
            }
        }

        rs.setData(map);

        return rs;
    }

    public Result getZvarlList(String stcd, int pageIndex, int length){
        Result rs = new Result();

        User u = SessionCache.get();
        String userId = u.getUserCode();

        List<Priviliges> rightList = rightDao.getRightByUserId(userId);

        List<ZvarlRelationVo> list = new ArrayList<>();

        PageHelper.startPage(pageIndex, length);

        if(rightList.size() > 0 && Integer.parseInt(rightList.get(0).getLevel())==3){
            list = ycdbRelationDao.getZvarlList_Level3(rightList.get(0).getPriviligeId(), stcd);
        }
        else {
            list = ycdbRelationDao.getZvarlList(stcd);
        }

        PageInfo p = new PageInfo<>(list);
        rs.setData(p);
        rs.setTotal(Integer.parseInt(p.getTotal() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result addZvarl(String stcd, String ptno, String rz, String w, String wsfa){
        Result rs = new Result();
        int result = 0;
        Map map = new HashMap();

        // 点序号不为空时说明是更新数据
        if(ptno != null && !StringUtil.isBlank(ptno)){
            result = rwRelationDao.updateZvarl(stcd, ptno, rz, w, wsfa);
        }
        else {
            //点序号为空时，说明是新增数据，根据测站编码自增长
            String maxPtno = rwRelationDao.getMaxZvarlPtno(stcd);

            if(null == maxPtno || "".equals(maxPtno)){
                maxPtno="1";
            }else{
                maxPtno=String.valueOf(Integer.valueOf(ptno)+1);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date date = new Date();
            String yr = sdf.format(date);

            result = rwRelationDao.insertZvarl(stcd, maxPtno, rz, w, wsfa);
        }

        if (result > 0){
            map.put("ERRNO", "0");

            rs.setCode(Result.SUCCESS);
        }
        else{
            map.put("ERRNO", "ERR01");
            map.put("ERRMAS", "添加水位库容关系失败");

            rs.setCode(Result.FAILURE);
        }

        rs.setData(map);

        return rs;
    }

    public Result getZvarl(String stcd, String ptno){
        Result rs = new Result();

        rs.setData(ycdbRelationDao.getZvarl(stcd, ptno));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result deleteZvarl(String stcd_ptno){
        Result rs = new Result();
        Map map = new HashMap();

        if (!"".equals(stcd_ptno) && !"null".equals(stcd_ptno)) {
            String[] stcd_ptnoarr = stcd_ptno.split(";");
            List<Map> list = new ArrayList<>();

            for (int i = 0; i < stcd_ptnoarr.length; i++) {
                Map m = new HashMap();

                String[] stcdarr = stcd_ptnoarr[i].split(",");

                m.put("stcd", stcdarr[0]);
                m.put("ptno", stcdarr[1]);

                list.add(m);
            }

            if (rwRelationDao.deleteZvarl(list) > 0){
                map.put("ERRNO", "0");

                rs.setCode(Result.SUCCESS);
            }
            else{
                map.put("ERRNO", "ERR01");
                map.put("ERRMAS", "删除水位库容关系失败");

                rs.setCode(Result.FAILURE);
            }
        }

        rs.setData(map);

        return rs;
    }

    public Result addCosst(String id, String stcd, String stnm, String arnm, String  ordr){
        Result rs = new Result();
        int result = 0;
        Map map = new HashMap();

        try {
            Cosst cosst = new Cosst();

            if (!StringUtil.isBlank(id)) {
                cosst.setId(id);
            }
            if (!StringUtil.isBlank(stcd)) {
                cosst.setStcd(stcd);
            }
            if (!StringUtil.isBlank(stnm)) {
                cosst.setStnm(stnm);
            }
            if (!StringUtil.isBlank(arnm)) {
                cosst.setArnm(arnm);
            }

            // 顺序号不为空时说明是更新数据
            if (!StringUtil.isBlank(ordr)) {
                cosst.setOrdr(ordr);

                result = cosstDao.updateCosst(id, stcd, stnm, arnm, ordr);
            } else {
                String ptnosql = cosstDao.getMaxOrdr(stcd, id);

                if (null == ptnosql || "".equals(ordr)) {
                    ptnosql = "1";
                } else {
                    ptnosql = String.valueOf(Integer.valueOf(ptnosql) + 1);
                }

                cosst.setOrdr(ptnosql);

                result = cosstDao.insertSelective(cosst);
            }

            if (result > 0){
                map.put("ERRNO", "0");

                rs.setCode(Result.SUCCESS);
            }
            else{
                map.put("ERRNO", "ERR01");
                map.put("ERRMAS", "添加测站和分类关系信息失败");

                rs.setCode(Result.FAILURE);
            }
        }
        catch(Exception ex)
        {
            map.put("ERRNO", "ERR01");
            map.put("ERRMAS", "添加测站和分类关系信息失败");

            rs.setCode(Result.FAILURE);
        }

        rs.setData(map);

        return rs;
    }

    public Result getCosst(String stcd, String id){
        Result rs = new Result();

        Cosst c = new Cosst();

        c.setId(id);
        c.setStcd(stcd);

        rs.setCode(Result.SUCCESS);
        rs.setData(cosstDao.selectOne(c));

        return rs;
    }

    public Result deleteCosst(String stcd_id) {
        Result rs = new Result();
        Map map = new HashMap();

        if (!StringUtil.isBlank(stcd_id)) {
            List<Map> list = new ArrayList<>();
            String[] stcd_ptnoarr = stcd_id.split(";");

            for (int i = 0; i < stcd_ptnoarr.length; i++) {
                String[] stcdarr = stcd_ptnoarr[i].split(",");
                Map m = new HashMap();

                m.put("stcd", stcdarr[0]);
                m.put("id", stcdarr[1]);

                list.add(m);
            }

            cosstDao.deleteCosst(list);

            map.put("ERRNO", "0");

            rs.setCode(Result.SUCCESS);
        }
        else{
            map.put("ERRNO", "ERR01");
            map.put("ERRMAS", "删除测站和分类失败");

            rs.setCode(Result.FAILURE);
        }

        rs.setData(map);

        return rs;
    }


    public Result checkZqrl(String stcd, String yr, String ptno){
        Result rs = new Result();
        Map<String,Object> map = new HashMap<String,Object>();

        ZqrlRelationVo zqrl = ycdbRelationDao.getZqrl(stcd, ptno, yr);

        if (zqrl != null){
            map.put("STATUS", false);
        }
        else {
            map.put("STATUS", true);
        }

        rs.setData(map);
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result getStationList(String stcd, String protocol, String sttp, int pageIndex, int length){
        Result rs = new Result();
        List<RtuStation> list = new ArrayList<>();

        PageHelper.startPage(pageIndex, length);

        list = rtuStationDao.getStationList(stcd, protocol, sttp);

        PageInfo p = new PageInfo<>(list);

        rs.setData(p);
        rs.setTotal(Integer.parseInt(p.getTotal() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result getStationSelect(){
        Result rs = new Result();

        rs.setData(rtuStationDao.getStationSelect());
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result checkStation(String stcd, String id){
        Result rs = new Result();
        Map<String,Object> map = new HashMap<String,Object>();

        CosstVo vo = cosstDao.getCosst(stcd, id);

        if (vo != null){
            map.put("STATUS", false);
        }
        else {
            map.put("STATUS", true);
        }

        rs.setData(map);
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result checkCosst(String stcd, String id){
        Result rs = new Result();
        Map<String,Object> map = new HashMap<String,Object>();

        List<Map> list = ycdbRelationDao.getCosst(stcd, id);

        if (list.size() > 0){
            map.put("STATUS", false);
        }
        else {
            map.put("STATUS", true);
        }

        rs.setData(map);
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result addStation(String stcd, String rtucd, String stcd8, String stnm, String rvnm, String bsnm, String hnnm, String protocol, String dtmel, String sttp, String telphone, String flag_hd, String center, String borrow){
        Result rs = new Result();
        RtuStation station = new RtuStation();
        Map<String, Object> map = new HashMap<String, Object>();

        station.setStcd(stcd);
        station.setRtucd(rtucd);
        station.setStcd8(stcd8);
        station.setStnm(stnm);
        station.setRvnm(rvnm);
        station.setBsnm(bsnm);
        station.setHnnm(hnnm);
        station.setProtocol(protocol);
        station.setSttp(sttp);
        station.setTelephone(telphone);
        station.setFlag_hd(flag_hd);
        station.setCenter(center);
        station.setBorrow(borrow);

        if(!StringUtil.isBlank(dtmel)){
            station.setDtmel(Double.parseDouble(dtmel));
        }

        if("SL651".equals(protocol)) {
            String flag_rain="0";
            String flag_water="0";
            if("1".equals(sttp)) {
                // 雨量站
                flag_rain="1";
            }else if("2".equals(sttp)) {
                // 水位站
                flag_water="1";
            }else {
                // 水位雨量站
                flag_water="1";
                flag_rain="1";
            }

            station.setFlag_rain(flag_rain);
            station.setFlag_water(flag_water);
        }

        if (rtuStationDao.insertSelective(station) >0){
            map.put("ERRNO", "0");

            rs.setCode(Result.SUCCESS);
        }
        else{
            map.put("ERRNO", "ERR01");
            map.put("ERRMAS", "添加新测站信息失败！");

            rs.setCode(Result.FAILURE);
        }

        rs.setData(map);

        return rs;
    }

    public Result updateStation(String stcd, String rtucd, String stcd8, String stnm, String rvnm, String bsnm, String hnnm, String protocol, String dtmel, String sttp, String telphone, String flag_hd, String center, String borrow){
        Result rs = new Result();
        Double dt = null;
        String flag_rain = "";
        String flag_water = "";
        Map<String, Object> map = new HashMap<String, Object>();

        if(!StringUtil.isBlank(dtmel)){
            dt = (Double.parseDouble(dtmel));
        }

        if("SL651".equals(protocol)) {
            flag_rain="0";
            flag_water="0";

            if("1".equals(sttp)) {
                // 雨量站
                flag_rain="1";
            }else if("2".equals(sttp)) {
                // 水位站
                flag_water="1";
            }else {
                // 水位雨量站
                flag_water="1";
                flag_rain="1";
            }
        }

        if (rtuStationDao.updateStation(stcd, rtucd, stcd8, stnm, rvnm, bsnm, hnnm, protocol, dt, sttp, telphone, flag_hd, center, borrow, flag_rain, flag_water) > 0){
            map.put("ERRNO", "0");

            rs.setCode(Result.SUCCESS);
        }
        else{
            map.put("ERRNO", "ERR01");
            map.put("ERRMAS", "修改测站信息失败！");

            rs.setCode(Result.FAILURE);
        }

        rs.setData(map);

        return rs;
    }

    public Result delStation(String ids){
        Result rs = new Result();
        Map<String, Object> map = new HashMap<String, Object>();

        if (!"".equals(ids) && !"null".equals(ids)) {
            String[] idlist = ids.split(";");
            List<Map> list = new ArrayList<>();

            for (int i = 0; i < idlist.length; i++) {
                Map m = new HashMap();
                String id = idlist[i];
                String [] cds = id.split(",");

                m.put("stcd", cds[0]);
                m.put("rtucd", cds[1]);
                m.put("stcd8", cds[2]);

                list.add(m);
            }

            if (rtuStationDao.delStation(list) > 0){
                map.put("ERRNO", "0");

                rs.setCode(Result.SUCCESS);
            }
            else{
                map.put("ERRNO", "ERR01");
                map.put("ERRMAS", "删除测站信息失败！");

                rs.setCode(Result.FAILURE);
            }
        }

        rs.setData(map);

        return rs;
    }

    public Result customStation(String stcd_id){
        Result rs = new Result();
        Map<String, Object> map = new HashMap<String, Object>();

        if (!"".equals(stcd_id) && !"null".equals(stcd_id)) {
            String[] stcd_ptnoarr = stcd_id.split(";");
            List<Map> list = new ArrayList<>();

            for (int i = 0; i < stcd_ptnoarr.length; i++) {
                Map m = new HashMap();
                String[] stcdarr = stcd_ptnoarr[i].split(",");

                m.put("stcd", stcdarr[0]);
                m.put("id", stcdarr[1]);

                list.add(m);
            }

            if (rtuStationDao.customStation(list) > 0){
                map.put("ERRNO", "0");

                rs.setCode(Result.SUCCESS);
            }
            else{
                map.put("ERRNO", "ERR01");
                map.put("ERRMAS", "添加自定义站点信息失败！");

                rs.setCode(Result.FAILURE);
            }
        }

        rs.setData(map);

        return rs;
    }

    public Result deleteCustom(String stcd_id){
        Result rs = new Result();
        Map<String, Object> map = new HashMap<String, Object>();

        if (!"".equals(stcd_id) && !"null".equals(stcd_id)) {
            String[] stcd_ptnoarr = stcd_id.split(";");
            List<Map> list = new ArrayList<>();

            for (int i = 0; i < stcd_ptnoarr.length; i++) {
                Map m = new HashMap();
                String[] stcdarr = stcd_ptnoarr[i].split(",");

                m.put("stcd", stcdarr[0]);
                m.put("id", stcdarr[1]);

                list.add(m);
            }

            if (rtuStationDao.deleteCustom(list) > 0){
                map.put("ERRNO", "0");

                rs.setCode(Result.SUCCESS);
            }
            else{
                map.put("ERRNO", "ERR01");
                map.put("ERRMAS", "删除自定义站点信息失败！");

                rs.setCode(Result.FAILURE);
            }
        }

        return rs;
    }

    public Result getCosstList(String stdm, String id){
        Result rs = new Result();

        List<CosstVo> list = ycdbRelationDao.getCosstList(stdm, id);

        for (CosstVo vo : list){
            vo.setStlc(rwRelationDao.getStlc(vo.getStcd()));
        }

        rs.setData(list);
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result getConfigList(String stdm, String sttp){
        Result rs = new Result();

        rs.setData(ycdbRelationDao.getCosstList(stdm, sttp));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result checkConfig(String stcd, String sttp){
        Result rs = new Result();
        Map<String,Object> map = new HashMap<String,Object>();

        StConfig sc = ycdbRelationDao.getConfig(stcd, sttp);

        if (sc != null){
            map.put("STATUS", false);
        }
        else {
            map.put("STATUS", true);
        }

        rs.setData(map);
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result addConfig(String stcd, String stnm, String sttp, String pmax5m, String pmax1h, String zmin, String zmax, String timect, String ruku){
        Result rs = new Result();
        Map<String,Object> map = new HashMap<String,Object>();
        Map<String,Object> rukuMap = new HashMap<String,Object>();

        boolean srage=false;
        boolean dye=false;
        boolean wtmp=false;
        boolean gate=false;
        boolean volgate=false;
        boolean soil=false;
        boolean velocity=false;
        boolean ott=false;

        if(!StringUtil.isBlank(ruku)){
            String[] arr = ruku.split(",");

            for(String r:arr){
                rukuMap.put(r, true);
            }

            srage = rukuMap.get("SRAGE")==null?false:true;
            dye = rukuMap.get("DYE")==null?false:true;
            wtmp = rukuMap.get("WTMP")==null?false:true;
            gate = rukuMap.get("GATE")==null?false:true;
            volgate = rukuMap.get("VOLGATE")==null?false:true;
            velocity = rukuMap.get("VELOCITY")==null?false:true;
            ott = rukuMap.get("OTT")==null?false:true;
        }

        StConfig sc = ycdbRelationDao.getConfig(stcd, "");

        // 顺序号不为空时说明是更新数据
        if(sc != null){
            if (ycdbRelationDao.updateConfig(stcd, stnm, sttp, pmax5m, pmax1h, zmin, zmax, timect, srage, dye, wtmp, gate, volgate, soil, velocity, ott) > 0){
                map.put("ERRNO", "0");
            }
            else{
                map.put("ERRNO", "ERR01");
                map.put("ERRMAS", "更新测站和分类关系失败！");
            }
        }
        else{
            if (ycdbRelationDao.addConfig(stcd, stnm, sttp, pmax5m, pmax1h, zmin, zmax, timect, srage, dye, wtmp, gate, volgate, soil, velocity, ott) > 0){
                map.put("ERRNO", "0");
            }
            else{
                map.put("ERRNO", "ERR01");
                map.put("ERRMAS", "添加测站和分类关系失败！");
            }
        }

        rs.setData(map);
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result getConfig(String stcd, String sttp){
        Result rs = new Result();

        rs.setData(ycdbRelationDao.getConfig(stcd, sttp));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result deleteConfig(String stcd_sttp){
        Result rs = new Result();
        Map<String,Object> map = new HashMap<String,Object>();

        if (!"".equals(stcd_sttp) && !"null".equals(stcd_sttp)) {
            String[] idlist = stcd_sttp.split(";");
            List<Map> list = new ArrayList<>();

            for (int i = 0; i < idlist.length; i++) {
                Map m = new HashMap();
                String id = idlist[i];
                String [] cds = id.split(",");

                m.put("stcd", cds[0]);
                m.put("sttp", cds[1]);

                list.add(m);
            }

            if (ycdbRelationDao.deleteConfig(list) > 0){
                map.put("ERRNO", "0");

                rs.setCode(Result.SUCCESS);
            }
            else{
                map.put("ERRNO", "ERR01");
                map.put("ERRMAS", "删除测站和分类关系信息失败！");

                rs.setCode(Result.FAILURE);
            }
        }

        rs.setData(map);

        return rs;
    }

    public Result importZqrl(HttpServletResponse response) throws IOException {
        Result rs = new Result();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        PrintWriter out = response.getWriter();
        FileInputStream fileIn;
        File xls = null;
        long startTime = System.currentTimeMillis();

        xls=new File("D:\\ruku\\uploadFile\\"+UUID.randomUUID().toString().replaceAll("-","")+".xls");
        xlsFile.renameTo(xls);
        fileIn = new FileInputStream(xls);

        //根据指定的文件输入流导入Excel从而产生Workbook对象
        HSSFWorkbook wb0 = new HSSFWorkbook(fileIn);
        //获取Excel文档中的第一个表单
        Iterator<Row> rowIter = wb0.getSheetAt(0).rowIterator();
        //对Sheet中的每一行进行迭代
        List<Map> listParam=new ArrayList();
        List<Map> orclParam=new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int ptno = 0;
        String stcdTemp = "";

        while(rowIter.hasNext()){
            Row row=rowIter.next();

            if(row.getRowNum()>1){
                try{
                    row.getCell(0).setCellType(HSSFCell.CELL_TYPE_STRING);
                    row.getCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);
                    row.getCell(2).setCellType(HSSFCell.CELL_TYPE_STRING);
                    row.getCell(3).setCellType(HSSFCell.CELL_TYPE_STRING);

                    if(row.getCell(0).getStringCellValue().trim().equals("")){
                        break;
                    }
                    if(!stcdTemp.equals(row.getCell(0).getStringCellValue())) {
                        ptno = 0;
                    }

                    stcdTemp = row.getCell(0).getStringCellValue();
                    String stcd = row.getCell(0).getStringCellValue();
                    String year = row.getCell(1).getStringCellValue();
                    double z = Float.parseFloat(row.getCell(2).getStringCellValue());
                    double q = Float.parseFloat(row.getCell(3).getStringCellValue());

                    Map obj = new HashMap();
                    Map orclObj = new HashMap();

                    obj.put("stcd", stcd);
                    obj.put("yr", year);
                    obj.put("ptno", ++ptno);
                    obj.put("z", z);
                    obj.put("q", q);

                    orclObj.put("stcd", stcd);
                    orclObj.put("bgtm", sdf.parse(year+"-01-01 08:00:00"));
                    orclObj.put("ptno", ++ptno);
                    orclObj.put("z", z);
                    orclObj.put("q", q);
                    orclObj.put("modiTime", new Date());

                    orclParam.add(orclObj);
                    listParam.add(obj);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        rwRelationDao.addAllZqrlToOrcl(orclParam);

        if (ycdbRelationDao.addAllZqrl(listParam) > 0){
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

    public Result importZvarl(HttpServletResponse response) throws IOException {
        Result rs = new Result();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        PrintWriter out = response.getWriter();
        FileInputStream fileIn;
        File xls = null;
        long startTime = System.currentTimeMillis();

        xls=new File("D:\\ruku\\uploadFile\\"+UUID.randomUUID().toString().replaceAll("-","")+".xls");
        xlsFile.renameTo(xls);
        fileIn = new FileInputStream(xls);

        //根据指定的文件输入流导入Excel从而产生Workbook对象
        HSSFWorkbook wb0 = new HSSFWorkbook(fileIn);
        //获取Excel文档中的第一个表单
        Iterator<Row> rowIter = wb0.getSheetAt(0).rowIterator();
        //对Sheet中的每一行进行迭代
        List<Map> listParam=new ArrayList();
        List<Map> orclParam=new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        while(rowIter.hasNext()){
            Row row=rowIter.next();

            if(row.getRowNum()>1){
                try{
                    row.getCell(0).setCellType(HSSFCell.CELL_TYPE_STRING);
                    row.getCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);
                    row.getCell(2).setCellType(HSSFCell.CELL_TYPE_STRING);
                    row.getCell(3).setCellType(HSSFCell.CELL_TYPE_STRING);
                    row.getCell(4).setCellType(HSSFCell.CELL_TYPE_STRING);
                    row.getCell(5).setCellType(HSSFCell.CELL_TYPE_STRING);

                    if(row.getCell(0).getStringCellValue().trim().equals("")){
                        break;
                    }

                    String stcd = row.getCell(0).getStringCellValue();
                    String date = row.getCell(1).getStringCellValue()+"-01-01 08:00:00";
                    int ptno = Integer.valueOf(row.getCell(2).getStringCellValue().toString());
                    float rz = Float.parseFloat(row.getCell(3).getStringCellValue());
                    float w = Float.parseFloat(row.getCell(4).getStringCellValue());
                    float wsfa = Float.parseFloat(row.getCell(5).getStringCellValue());
                    Date modiTime = new Date();

                    Map obj = new HashMap();
                    Map orclObj = new HashMap();

                    obj.put("stcd", stcd);
                    obj.put("ptno", ptno);
                    obj.put("rz", rz);
                    obj.put("w", w);
                    obj.put("wsfa", wsfa);

                    orclObj.put("stcd", stcd);
                    orclObj.put("ptno", ptno);
                    orclObj.put("rz", rz);
                    orclObj.put("w", w);
                    orclObj.put("wsfa", wsfa);
                    orclObj.put("mstm", sdf.parse(date));
                    orclObj.put("modiTime", modiTime);

                    listParam.add(obj);
                    orclParam.add(orclObj);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        rwRelationDao.addAllZvarlToOrcl(orclParam);

        if (ycdbRelationDao.addAllZvarl(listParam) > 0){
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
}
