package com.goldwater.querycenter.controller.ruku;

import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.service.ruku.RainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/rain")
public class RainController {
    @Autowired
    private RainService rainService;

    /**
     * 查询5分钟雨量
     */
    @PostMapping("/queryRainFm")
    @ResponseBody
    public Result queryRainFm(@RequestParam(name = "stdm", defaultValue = "") String stcd,
                              @RequestParam(name = "starttm", defaultValue = "") String startTm,
                              @RequestParam(name = "endtm", defaultValue = "") String endTm,
                              @RequestParam(name = "page", defaultValue = "1") int pageIndex,
                              @RequestParam(name = "limit", defaultValue = "10") int length){
        return rainService.queryRainFm(stcd, startTm, endTm, pageIndex, length);
    }

    /**
     * 雨量过程线数据查询
     */
    @PostMapping("/queryChart")
    @ResponseBody
    public Result queryChart(@RequestParam(name = "stdm", defaultValue = "") String stcd,
                              @RequestParam(name = "time", defaultValue = "") String time){
        return rainService.queryRainChart(stcd, time);
    }

    /**
     * 导出测站的五分钟雨量数据excel文件
     * @return
     */
    @PostMapping("/exportRain5m")
    @ResponseBody
    public void exportRain5m(@RequestParam(name = "stdm", defaultValue = "") String stcd,
                               @RequestParam(name = "starttm", defaultValue = "") String startTm,
                               @RequestParam(name = "endtm", defaultValue = "") String endTm,
                             HttpServletRequest request,
                             HttpServletResponse response){
        Result rs = new Result();

        try {
            rainService.exportRain5m(stcd, startTm, endTm, request, response);
        }
        catch (Exception e) {
            rs.setCode(Result.FAILURE);
        }
    }
}
