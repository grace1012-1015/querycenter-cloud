package com.goldwater.querycenter.controller.ruku;


import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.service.ruku.JktjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/jktj")
public class JktjController {
    @Autowired
    private JktjService jktjService;

    /**
     * 墒情监控(限站)
     */
    @PostMapping("/querySoil")
    @ResponseBody
    public Result querySoil(@RequestParam(name = "stcd", defaultValue = "") String stcd,
                            @RequestParam(name = "time", defaultValue = "") String time){
        return jktjService.querySoil(stcd, time);
    }

    /**
     * 水文雨情监控(限站)
     */
    @PostMapping("/querySwRainJk")
    @ResponseBody
    public Result querySwRainJk(@RequestParam(name = "stcd", defaultValue = "") String stcd,
                                @RequestParam(name = "time", defaultValue = "") String time,
                                @RequestParam(name = "addvcd", defaultValue = "") String addvcd,
                                @RequestParam(name = "type", defaultValue = "") String type,
                                @RequestParam(name = "custom", defaultValue = "") String custom){
        return jktjService.querySwRainJk(stcd, time, type, custom, addvcd);
    }

    /**
     * 水位监控(限站)
     */
    @PostMapping("/querySwWaterlevelJk")
    @ResponseBody
    public Result querySwWaterlevelJk(@RequestParam(name = "stcd", defaultValue = "") String stcd,
                                @RequestParam(name = "time", defaultValue = "") String time,
                                @RequestParam(name = "addvcd", defaultValue = "") String addvcd,
                                @RequestParam(name = "type", defaultValue = "") String type,
                                @RequestParam(name = "custom", defaultValue = "") String custom){
        return jktjService.querySwWaterlevelJk(stcd, time, type, custom, addvcd);
    }

    /**
     * 单个站整点水位查询
     */
    @PostMapping("/querySwWaterlevelJk2")
    @ResponseBody
    public Result querySwWaterlevelJk2(@RequestParam(name = "stcd", defaultValue = "") String stcd,
                                       @RequestParam(name = "time", defaultValue = "") String time,
                                       @RequestParam(name = "addvcd", defaultValue = "") String addvcd,
                                       @RequestParam(name = "type", defaultValue = "") String type,
                                       @RequestParam(name = "custom", defaultValue = "") String custom){
        return jktjService.querySwRainJk(stcd, time, type, custom, addvcd);
    }

    /**
     * 水文雨量统计(限站)
     */
    @PostMapping("/querySwRainTJ")
    @ResponseBody
    public Result querySwRainTJ(@RequestParam(name = "stcd", defaultValue = "") String stcd,
                                @RequestParam(name = "time", defaultValue = "") String time,
                                @RequestParam(name = "type", defaultValue = "") String type,
                                @RequestParam(name = "interval", defaultValue = "") String interval,
                                @RequestParam(name = "start", defaultValue = "1") int start,
                                @RequestParam(name = "limit", defaultValue = "10") int limit){
        return jktjService.querySwRainTJ(stcd, time, type, interval, start, limit);
    }

    /**
     * 获取当前时间前几个小时的雨量累计值20200329加
     */
    @PostMapping("/querySwRainTJ2")
    @ResponseBody
    public Result querySwRainTJ2(@RequestParam(name = "stcd", defaultValue = "") String stcd,
                                 @RequestParam(name = "type", defaultValue = "") String type,
                                 @RequestParam(name = "interval", defaultValue = "") String interval,
                                 @RequestParam(name = "start", defaultValue = "1") int start,
                                 @RequestParam(name = "limit", defaultValue = "10") int limit){
        return jktjService.querySwRainTJ2(stcd, type, interval, start, limit);
    }

    /**
     * 测站缺报统计(限站)
     */
    @PostMapping("/queryQbStationTJ")
    @ResponseBody
    public Result queryQbStationTJ(@RequestParam(name = "stcd", defaultValue = "") String stcd,
                                   @RequestParam(name = "time", defaultValue = "") String time,
                                   @RequestParam(name = "cosId", defaultValue = "") String cosId){
        return jktjService.queryQbStationTJ(stcd, time, cosId);
    }

    @PostMapping("/exportSwRainTJ")
    @ResponseBody
    public void exportSwRainTJ(@RequestParam(name = "stcd", defaultValue = "") String stcd,
                               @RequestParam(name = "time", defaultValue = "") String time,
                               @RequestParam(name = "type", defaultValue = "") String type,
                               @RequestParam(name = "interval", defaultValue = "") String interval,
                               @RequestParam(name = "filename", defaultValue = "") String filename,
                               HttpServletRequest request,
                               HttpServletResponse response,
                               @RequestParam(name = "start", defaultValue = "1") int start,
                               @RequestParam(name = "limit", defaultValue = "1000") int limit){
        Result rs = new Result();

        try {
            jktjService.exportSwRainTJ(stcd, time, type, interval, filename, response, start, limit);
        }
        catch (Exception e) {
            rs.setCode(Result.FAILURE);
        }
    }
}
