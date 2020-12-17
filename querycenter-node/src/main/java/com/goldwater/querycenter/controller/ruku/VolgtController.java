package com.goldwater.querycenter.controller.ruku;

import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.service.ruku.VolgtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/volgt")
public class VolgtController {
    @Autowired
    private VolgtService volgtService;

    /**
     * 电压数据查询
     */
    @PostMapping("/query")
    @ResponseBody
    public Result query(@RequestParam(name = "stcd", defaultValue = "") String stcd,
                        @RequestParam(name = "time", defaultValue = "") String time,
                        @RequestParam(name = "page", defaultValue = "1") int pageIndex,
                        @RequestParam(name = "limit", defaultValue = "10") int length){
        return volgtService.queryAnomalyVoltage(stcd, time, pageIndex, length);
    }

    /**
     * 电压过程线数据查询
     */
    @PostMapping("/queryChart")
    @ResponseBody
    public Result queryChart(@RequestParam(name = "stcd", defaultValue = "") String stcd,
                             @RequestParam(name = "time", defaultValue = "") String time){
        return volgtService.queryChart(stcd, time);
    }
}
