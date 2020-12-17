package com.goldwater.querycenter.controller.ruku;

import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.service.ruku.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/relation")
public class RelationController {
    @Autowired
    private RelationService relationService;

    /**
     * 水位流量关系数据查询
     */
    @PostMapping("/getZqrlList")
    @ResponseBody
    public Result getZqrlList(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                                @RequestParam(name = "page", defaultValue = "1") int pageIndex,
                                @RequestParam(name = "limit", defaultValue = "10") int length){
        return relationService.getZqrlList(stcd, pageIndex, length);
    }

    /**
     * 添加水位流量关系
     *
     */
    @PostMapping("/addZqrl")
    @ResponseBody
    public Result addZqrl(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                          @RequestParam(name = "PTNO", defaultValue = "") String ptno,
                          @RequestParam(name = "Z", defaultValue = "") String z,
                          @RequestParam(name = "Q", defaultValue = "") String q){
        return relationService.addZqrl(stcd, ptno, z, q);
    }

    /**
     * 单条水位流量关系数据查询
     *
     * @author qpp 2017-04-17
     */
    @PostMapping("/getZqrl")
    @ResponseBody
    public Result getZqrl(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                          @RequestParam(name = "PTNO", defaultValue = "") String ptno){
        return relationService.getZqrl(stcd, ptno);
    }

    /**
     * 删除水位流量关系
     *
     */
    @PostMapping("/deleteZqrl")
    @ResponseBody
    public Result deleteZqrl(@RequestParam(name = "STCD_PTNO", defaultValue = "") String stcd_ptno){
        return relationService.deleteZqrl(stcd_ptno);
    }

    /**
     * 水位库容关系数据查询
     */
    @PostMapping("/getZvarlList")
    @ResponseBody
    public Result getZvarlList(@RequestParam(name = "stcd", defaultValue = "") String stcd,
                               @RequestParam(name = "page", defaultValue = "1") int pageIndex,
                               @RequestParam(name = "limit", defaultValue = "10") int length){
        return relationService.getZvarlList(stcd, pageIndex, length);
    }

    /**
     * 添加水位库容关系
     *
     */
    @PostMapping("/addZvarl")
    @ResponseBody
    public Result addZvarl(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                           @RequestParam(name = "PTNO", defaultValue = "") String ptno,
                           @RequestParam(name = "RZ", defaultValue = "") String rz,
                           @RequestParam(name = "W", defaultValue = "") String w,
                           @RequestParam(name = "WSFA", defaultValue = "") String wsfa){
        return relationService.addZvarl(stcd, ptno, rz, w, wsfa);
    }

    /**
     * 单条水位库容关系数据查询
     *
     * @author qpp 2017-04-17
     */
    @PostMapping("/getZvarl")
    @ResponseBody
    public Result getZvarl(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                           @RequestParam(name = "PTNO", defaultValue = "") String ptno){
        return relationService.getZvarl(stcd, ptno);
    }

    /**
     * 删除水位库容关系
     *
     */
    @PostMapping("/deleteZvarl")
    @ResponseBody
    public Result deleteZvarl(@RequestParam(name = "STCD_PTNO", defaultValue = "") String stcd_ptno){
        return relationService.deleteZvarl(stcd_ptno);
    }

    /**
     * 添加测站和分类关系信息
     */
    @PostMapping("/addCosst")
    @ResponseBody
    public Result addCosst(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                           @RequestParam(name = "STNM", defaultValue = "") String stnm,
                           @RequestParam(name = "ARNM", defaultValue = "") String arnm,
                           @RequestParam(name = "ORDR", defaultValue = "") String ordr,
                           @RequestParam(name = "ID", defaultValue = "") String id){
        return relationService.addCosst(id, stcd, stnm, arnm, ordr);
    }

    /**
     * 单条测站和分类关系数据查询
     */
    @PostMapping("/getCosst")
    @ResponseBody
    public Result getCosst(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                           @RequestParam(name = "ID", defaultValue = "") String id){
        return relationService.getCosst(stcd, id);
    }

    /**
     * 删除测站和分类关系信息
     */
    @PostMapping("/deleteCosst")
    @ResponseBody
    public Result deleteCosst(@RequestParam(name = "STCD_ID", defaultValue = "") String stcd_id){
        return relationService.deleteCosst(stcd_id);
    }

    /**
     * 检查新增水位流量关系数据是否存在
     */
    @PostMapping("/checkZqrl")
    @ResponseBody
    public Result checkZqrl(@RequestParam(name = "stcd", defaultValue = "") String stcd,
                            @RequestParam(name = "yr", defaultValue = "") String yr,
                            @RequestParam(name = "ptno", defaultValue = "") String ptno){
        return relationService.checkZqrl(stcd, yr, ptno);
    }

    /**
     * 测站配置表的维护
     *
     */
    @PostMapping("/getStationList")
    @ResponseBody
    public Result getStationList(@RequestParam(name = "stcd", defaultValue = "") String stcd,
                                 @RequestParam(name = "protocol", defaultValue = "") String protocol,
                                 @RequestParam(name = "sttp", defaultValue = "") String sttp,
                                 @RequestParam(name = "page", defaultValue = "1") int pageIndex,
                                 @RequestParam(name = "limit", defaultValue = "10") int length){
        return relationService.getStationList(stcd, protocol, sttp, pageIndex, length);
    }

    /**
     *  获取stcd和stnm的对应关系(下拉选择框搜索)
     */
    @PostMapping("/getStationSelect")
    @ResponseBody
    public Result getStationSelect(){
        return relationService.getStationSelect();
    }

    /**
     * 检查新增水位流量关系数据是否存在
     */
    @PostMapping("/checkStation")
    @ResponseBody
    public Result checkStation(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                               @RequestParam(name = "ID", defaultValue = "") String id){
        return relationService.checkStation(stcd, id);
    }

    /**
     * 检查新增测站和分类关系数据是否存在
     */
    @PostMapping("/checkCosst")
    @ResponseBody
    public Result checkCosst(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                             @RequestParam(name = "ID", defaultValue = "") String id){
        return relationService.checkCosst(stcd, id);
    }

    /**
     * 添加新测站信息
     *
     */
    @PostMapping("/addStation")
    @ResponseBody
    public Result addStation(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                             @RequestParam(name = "RTUCD", defaultValue = "") String rtucd,
                             @RequestParam(name = "STCD8", defaultValue = "") String stcd8,
                             @RequestParam(name = "STNM", defaultValue = "") String stnm,
                             @RequestParam(name = "RVNM", defaultValue = "") String rvnm,
                             @RequestParam(name = "BSNM", defaultValue = "") String bsnm,
                             @RequestParam(name = "HNNM", defaultValue = "") String hnnm,
                             @RequestParam(name = "PROTOCOL", defaultValue = "") String protocol,
                             @RequestParam(name = "DTMEL", defaultValue = "") String dtmel,
                             @RequestParam(name = "STTP", defaultValue = "") String sttp,
                             @RequestParam(name = "STTP", defaultValue = "") String telphone,
                             @RequestParam(name = "FLAG_HD", defaultValue = "") String flag_hd,
                             @RequestParam(name = "CENTER", defaultValue = "") String center,
                             @RequestParam(name = "BORROW", defaultValue = "") String borrow){
        return relationService.addStation(stcd, rtucd, stcd8, stnm, rvnm, bsnm, hnnm, protocol, dtmel, sttp, telphone, flag_hd, center, borrow);
    }

    /**
     * 修改测站信息
     *
     */
    @PostMapping("/updateStation")
    @ResponseBody
    public Result updateStation(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                                @RequestParam(name = "RTUCD", defaultValue = "") String rtucd,
                                @RequestParam(name = "STCD8", defaultValue = "") String stcd8,
                                @RequestParam(name = "STNM", defaultValue = "") String stnm,
                                @RequestParam(name = "RVNM", defaultValue = "") String rvnm,
                                @RequestParam(name = "BSNM", defaultValue = "") String bsnm,
                                @RequestParam(name = "HNNM", defaultValue = "") String hnnm,
                                @RequestParam(name = "PROTOCOL", defaultValue = "") String protocol,
                                @RequestParam(name = "DTMEL", defaultValue = "") String dtmel,
                                @RequestParam(name = "STTP", defaultValue = "") String sttp,
                                @RequestParam(name = "STTP", defaultValue = "") String telphone,
                                @RequestParam(name = "FLAG_HD", defaultValue = "") String flag_hd,
                                @RequestParam(name = "CENTER", defaultValue = "") String center,
                                @RequestParam(name = "BORROW", defaultValue = "") String borrow){
        return relationService.updateStation(stcd, rtucd, stcd8, stnm, rvnm, bsnm, hnnm, protocol, dtmel, sttp, telphone, flag_hd, center, borrow);
    }

    /**
     * 删除测站信息
     */
    @PostMapping("/delStation")
    @ResponseBody
    public Result delStation(@RequestParam(name = "IDS", defaultValue = "") String ids){
        return relationService.delStation(ids);
    }

    /**
     * 添加自定义站点。
     */
    @PostMapping("/customStation")
    @ResponseBody
    public Result customStation(@RequestParam(name = "STCD_ID", defaultValue = "") String stcd_id){
        return relationService.customStation(stcd_id);
    }

    /**
     * 删除自定义站点。
     */
    @PostMapping("/deleteCustom")
    @ResponseBody
    public Result deleteCustom(@RequestParam(name = "STCD_ID", defaultValue = "") String stcd_id){
        return relationService.deleteCustom(stcd_id);
    }

    /**
     * 测站和分类关系数据查询
     */
    @PostMapping("/getCosstList")
    @ResponseBody
    public Result getCosstList(@RequestParam(name = "stcd", defaultValue = "") String stcd,
                               @RequestParam(name = "id", defaultValue = "") String id){
        return relationService.getCosstList(stcd, id);
    }

    /**
     * 入库测站数据查询
     */
    @PostMapping("/getConfigList")
    @ResponseBody
    public Result getConfigList(@RequestParam(name = "stcd", defaultValue = "") String stcd,
                                @RequestParam(name = "sttp", defaultValue = "") String sttp){
        return relationService.getConfigList(stcd, sttp);
    }

    /**
     * 检查新增测站和分类关系数据是否存在
     */
    @PostMapping("/checkConfig")
    @ResponseBody
    public Result checkConfig(@RequestParam(name = "stcd", defaultValue = "") String stcd,
                              @RequestParam(name = "sttp", defaultValue = "") String sttp){
        return relationService.checkConfig(stcd, sttp);
    }

    /**
     * 添加测站和分类关系
     *
     */
    @PostMapping("/addConfig")
    @ResponseBody
    public Result addConfig(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                            @RequestParam(name = "STNM", defaultValue = "") String stnm,
                            @RequestParam(name = "STTP", defaultValue = "") String sttp,
                            @RequestParam(name = "PMAX5M", defaultValue = "") String pmax5m,
                            @RequestParam(name = "PMAX1H", defaultValue = "") String pmax1h,
                            @RequestParam(name = "ZMIN", defaultValue = "") String zmin,
                            @RequestParam(name = "ZMAX", defaultValue = "") String zmax,
                            @RequestParam(name = "TIMECT", defaultValue = "") String timect,
                            @RequestParam(name = "RUKU", defaultValue = "") String ruku){
        return relationService.addConfig(stcd, stnm, sttp, pmax5m, pmax1h, zmin, zmax, timect, ruku);
    }

    /**
     * 单条测站和分类关系数据查询
     *
     */
    @PostMapping("/getConfig")
    @ResponseBody
    public Result getConfig(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                            @RequestParam(name = "STTP", defaultValue = "") String sttp){
        return relationService.getConfig(stcd, sttp);
    }

    /**
     * 删除测站和分类关系
     */
    @PostMapping("/deleteConfig")
    @ResponseBody
    public Result deleteConfig(@RequestParam(name = "STCD_STTP", defaultValue = "") String stcd_sttp){
        return relationService.deleteConfig(stcd_sttp);
    }

    /**
     * 批量导入
     */
    @PostMapping("/importZqrl")
    @ResponseBody
    public Result importZqrl(HttpServletRequest request,
                             HttpServletResponse response){
        try {
            return relationService.importZqrl(response);
        }
        catch (Exception e) {
            Result rs = new Result();
            Map resultMap = new HashMap<>();

            resultMap.put("ERRNO", "ERR01");
            resultMap.put("ERRMAS", "文档格式异常");

            rs.setCode(Result.FAILURE);

            return rs;
        }
    }

    /**
     * 批量导入
     */
    @PostMapping("/importZvarl")
    @ResponseBody
    public Result importZvarl(HttpServletRequest request,
                             HttpServletResponse response){
        try {
            return relationService.importZvarl(response);
        }
        catch (Exception e) {
            Result rs = new Result();
            Map resultMap = new HashMap<>();

            resultMap.put("ERRNO", "ERR01");
            resultMap.put("ERRMAS", "文档格式异常");

            rs.setCode(Result.FAILURE);

            return rs;
        }
    }

    /**
     * 下载模板
     */
    @PostMapping("/downloadXlsFile")
    @ResponseBody
    public void downloadXlsFile(@RequestParam(name = "fileName", defaultValue = "") String fileName,
                                HttpServletRequest request,
                                HttpServletResponse response){
        Result rs = new Result();

        try {
            relationService.downloadXlsFile(fileName, response, ReportController.class.getClassLoader().getResourceAsStream(fileName));
        }
        catch (Exception e) {
            rs.setCode(Result.FAILURE);
        }
    }
}
