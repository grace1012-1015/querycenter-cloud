package com.goldwater.querycenter.controller.ruku;

import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.service.ruku.WaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/water")
public class WaterController {
    @Autowired
    private WaterService waterService;

    /**
     * 查询五分钟河道水情
     */
    @PostMapping("/queryRiverFm")
    @ResponseBody
    public Result queryRiverFm(@RequestParam(name = "stdm", defaultValue = "") String stcd,
                               @RequestParam(name = "starttm", defaultValue = "") String startTm,
                               @RequestParam(name = "endtm", defaultValue = "") String endTm,
                               @RequestParam(name = "page", defaultValue = "1") int pageIndex,
                               @RequestParam(name = "limit", defaultValue = "10") int length){
        return waterService.queryRiver_R5(stcd, startTm, endTm, pageIndex, length);
    }

    /**
     * 查询五分钟水库水情
     */
    @PostMapping("/queryRsvrFm")
    @ResponseBody
    public Result queryRsvrFm(@RequestParam(name = "stdm", defaultValue = "") String stcd,
                              @RequestParam(name = "starttm", defaultValue = "") String startTm,
                              @RequestParam(name = "endtm", defaultValue = "") String endTm,
                              @RequestParam(name = "page", defaultValue = "1") int pageIndex,
                              @RequestParam(name = "limit", defaultValue = "10") int length){
        return waterService.queryRsvr_R5(stcd, startTm, endTm, pageIndex, length);
    }

    /**
     * 查询5分钟堰闸水情数据
     */
    @PostMapping("/queryWasFm")
    @ResponseBody
    public Result queryWasFm(@RequestParam(name = "stdm", defaultValue = "") String stcd,
                             @RequestParam(name = "starttm", defaultValue = "") String startTm,
                             @RequestParam(name = "endtm", defaultValue = "") String endTm,
                             @RequestParam(name = "page", defaultValue = "1") int pageIndex,
                             @RequestParam(name = "limit", defaultValue = "10") int length){
        return waterService.queryWas_R5(stcd, startTm, endTm, pageIndex, length);
    }

    /**
     * 查询5分钟泵站水情数据
     */
    @PostMapping("/queryPumpFm")
    @ResponseBody
    public Result queryPumpFm(@RequestParam(name = "stdm", defaultValue = "") String stcd,
                              @RequestParam(name = "starttm", defaultValue = "") String startTm,
                              @RequestParam(name = "endtm", defaultValue = "") String endTm,
                              @RequestParam(name = "page", defaultValue = "1") int pageIndex,
                              @RequestParam(name = "limit", defaultValue = "10") int length){
        return waterService.queryPump_R5(stcd, startTm, endTm, pageIndex, length);
    }

    /**
     * 导出测站的五分钟河道水情数据excel文件
     * @return
     */
    @PostMapping("/exportRiver5r")
    @ResponseBody
    public void exportRiver5r(@RequestParam(name = "stdm", defaultValue = "") String stcd,
                              @RequestParam(name = "starttm", defaultValue = "") String startTm,
                              @RequestParam(name = "endtm", defaultValue = "") String endTm,
                              HttpServletRequest request,
                              HttpServletResponse response){
        Result rs = new Result();

        try {
            waterService.exportRiver5r(stcd, startTm, endTm, response);
        }
        catch (Exception e) {
            rs.setCode(Result.FAILURE);
        }
    }

    /**
     * 导出测站的五分钟水库水情数据excel文件
     * @return
     */
    @PostMapping("/exportRsvr5r")
    @ResponseBody
    public void exportRsvr5r(@RequestParam(name = "stdm", defaultValue = "") String stcd,
                             @RequestParam(name = "starttm", defaultValue = "") String startTm,
                             @RequestParam(name = "endtm", defaultValue = "") String endTm,
                             HttpServletRequest request,
                             HttpServletResponse response){
        Result rs = new Result();

        try {
            waterService.exportRsvr5r(stcd, startTm, endTm, response);
        }
        catch (Exception e) {
            rs.setCode(Result.FAILURE);
        }
    }

    /**
     * 导出测站的五分钟堰闸水情数据excel文件
     * @return
     */
    @PostMapping("/exportWas5r")
    @ResponseBody
    public void exportWas5r(@RequestParam(name = "stdm", defaultValue = "") String stcd,
                             @RequestParam(name = "starttm", defaultValue = "") String startTm,
                             @RequestParam(name = "endtm", defaultValue = "") String endTm,
                             HttpServletRequest request,
                             HttpServletResponse response){
        Result rs = new Result();

        try {
            waterService.exportWas5r(stcd, startTm, endTm, response);
        }
        catch (Exception e) {
            rs.setCode(Result.FAILURE);
        }
    }

    /**
     * 导出测站的五分钟泵站水情数据excel文件
     * @return
     */
    @PostMapping("/exportPump5r")
    @ResponseBody
    public void exportPump5r(@RequestParam(name = "stdm", defaultValue = "") String stcd,
                            @RequestParam(name = "starttm", defaultValue = "") String startTm,
                            @RequestParam(name = "endtm", defaultValue = "") String endTm,
                            HttpServletRequest request,
                            HttpServletResponse response){
        Result rs = new Result();

        try {
            waterService.exportPump5r(stcd, startTm, endTm, response);
        }
        catch (Exception e) {
            rs.setCode(Result.FAILURE);
        }
    }
}
