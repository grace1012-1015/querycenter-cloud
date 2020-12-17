package com.goldwater.querycenter.controller.ruku;

import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.service.ruku.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    /**
     * 数据查询
     */
    @PostMapping("/getSqjbList")
    @ResponseBody
    public Result getSqjbList(@RequestParam(name = "RID", defaultValue = "") String rid,
                              @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex,
                              @RequestParam(name = "limit", defaultValue = "10") int limit){
        return reportService.getSqjbList(rid, pageIndex, limit);
    }

    /**
     * 数据添加
     */
    @PostMapping("/addSqjb")
    @ResponseBody
    public Result addSqjb(@RequestParam(name = "LB", defaultValue = "") String lb,
                          @RequestParam(name = "STNM", defaultValue = "") String stnm,
                          @RequestParam(name = "STCD", defaultValue = "") String stcd,
                          @RequestParam(name = "RID", defaultValue = "") String rid){
        return reportService.addSqjb(lb, stnm, stcd, rid);
    }

    /**
     * 数据删除
     */
    @PostMapping("/deleteSqjb")
    @ResponseBody
    public Result deleteSqjb(@RequestParam(name = "RID", defaultValue = "") String rid,
                             @RequestParam(name = "IDENTIFYS", defaultValue = "") String identifys){
        return reportService.deleteSqjb(rid, identifys);
    }

    /**
     * 导出数据
     */
    @PostMapping("/exportSqjb")
    @ResponseBody
    public void exportSqjb(@RequestParam(name = "rid", defaultValue = "") String rid,
                           HttpServletRequest request,
                           HttpServletResponse response){
        Result rs = new Result();

        try {
            reportService.exportSqjb(rid, response);
        }
        catch (Exception e) {
            rs.setCode(Result.FAILURE);
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
            reportService.downloadXlsFile(fileName, response, ReportController.class.getClassLoader().getResourceAsStream(fileName));
        }
        catch (Exception e) {
            rs.setCode(Result.FAILURE);
        }
    }

    /**
     * 批量导入
     */
    @PostMapping("/importSqjb")
    @ResponseBody
    public Result importSqjb(@RequestParam(name = "rid", defaultValue = "") String rid,
                           HttpServletRequest request,
                           HttpServletResponse response){
        try {
            return reportService.importSqjb(rid, response);
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
}
