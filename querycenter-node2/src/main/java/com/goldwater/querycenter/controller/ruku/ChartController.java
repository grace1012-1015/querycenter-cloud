package com.goldwater.querycenter.controller.ruku;

import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.service.ruku.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chart")
public class ChartController {
    @Autowired
    private ChartService chartService;

    /**
     * 单站时段雨量
     */
    @PostMapping("/getSingleHourRain")
    @ResponseBody
    public Result getSingleHourRain(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                                    @RequestParam(name = "STTM", defaultValue = "") String sttm,
                                    @RequestParam(name = "ENTM", defaultValue = "") String entm){
        return chartService.getSingleHourRain(stcd, sttm, entm);
    }

    /**
     * 单站时段水位
     */
    @PostMapping("/getSingleRiver")
    @ResponseBody
    public Result getSingleRiver(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                                    @RequestParam(name = "STTM", defaultValue = "") String sttm,
                                    @RequestParam(name = "ENTM", defaultValue = "") String entm){
        return chartService.getSingleHourRiver(stcd, sttm, entm);
    }

    /**
     * 返回河道单站的防洪指标信息
     */
    @PostMapping("/getSingleRiverRf")
    @ResponseBody
    public Result getSingleRiverRf(@RequestParam(name = "STCD", defaultValue = "") String stcd){
        return chartService.getSingleRvffch(stcd);
    }

    /**
     * 2、返回单站的水位库容关系
     */
    @PostMapping("/getSingleRZWRelation")
    @ResponseBody
    public Result getSingleRZWRelation(@RequestParam(name = "STCD", defaultValue = "") String stcd){
        return chartService.getSingleRZWRelation(stcd);
    }

    /**
     * 2、返回单站的水位流量关系
     */
    @PostMapping("/getSingleZqrlRelation")
    @ResponseBody
    public Result getSingleZqrlRelation(@RequestParam(name = "STCD", defaultValue = "") String stcd){
        return chartService.getSingleZqrlRelation(stcd);
    }
}
