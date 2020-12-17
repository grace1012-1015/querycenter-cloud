package com.goldwater.querycenter.controller.ruku;

import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.service.ruku.QbtjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/qbtj")
public class QbtjController {
    @Autowired
    private QbtjService obtjService;

    /**
     * 日雨量缺报统计
     */
    @PostMapping("/queryDayRain")
    @ResponseBody
    public Result queryDayRain(@RequestParam(name = "STDM", defaultValue = "") String stcd,
                               @RequestParam(name = "STARTTM", defaultValue = "") String starttm,
                               @RequestParam(name = "ENDTM", defaultValue = "") String endtm,
                               @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex,
                               @RequestParam(name = "limit", defaultValue = "10") int limit){
        return obtjService.queryDayRain(stcd, starttm, endtm, pageIndex, limit);
    }

    /**
     * 时段雨量缺报统计
     */
    @PostMapping("/queryHourRain")
    @ResponseBody
    public Result queryHourRain(@RequestParam(name = "STDM", defaultValue = "") String stcd,
                               @RequestParam(name = "STARTTM", defaultValue = "") String starttm,
                               @RequestParam(name = "ENDTM", defaultValue = "") String endtm,
                               @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex,
                               @RequestParam(name = "limit", defaultValue = "10") int limit){
        return obtjService.queryHourRain(stcd, starttm, endtm, pageIndex, limit);
    }
}
