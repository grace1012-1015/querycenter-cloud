package com.goldwater.querycenter.controller.rtu;

import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.service.rtu.CallerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/caller")
public class CallerController {
    @Autowired
    private CallerService callerService;

    @PostMapping("/getSolidStateData")
    @ResponseBody
    public Result getSolidStateData(@RequestParam(name = "RTUCDS", defaultValue = "") String rtucds,
                                    @RequestParam(name = "FUNC", defaultValue = "") String func,
                                    @RequestParam(name = "DATE", defaultValue = "") String params){
        return callerService.getSolidStateData(rtucds, func, params);
    }

    @PostMapping("/jiaoshi")
    @ResponseBody
    public Result jiaoshi(@RequestParam(name = "PARAMS", defaultValue = "") String params){
        return callerService.jiaoshi(params);
    }

    @PostMapping("/realTime")
    @ResponseBody
    public Result realTime(@RequestParam(name = "PARAMS", defaultValue = "") String params){
        return callerService.realTime(params);
    }
}
