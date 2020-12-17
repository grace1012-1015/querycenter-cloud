package com.goldwater.querycenter.controller.ruku;

import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.service.ruku.TelemetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/telemetry")
public class TelemetryController {
    @Autowired
    private TelemetryService telemetryService;

    /**
     * 遥测数据查询（限站）
     */
    @PostMapping("/queryYcData")
    @ResponseBody
    public Result queryYcData(@RequestParam(name = "stdm", defaultValue = "") String stdm,
                              @RequestParam(name = "state", defaultValue = "") String state,
                              @RequestParam(name = "valtype", defaultValue = "") String valtyp,
                              @RequestParam(name = "starttm", defaultValue = "") String startTm,
                              @RequestParam(name = "endtm", defaultValue = "") String endTm,
                              @RequestParam(name = "page", defaultValue = "1") int pageIndex,
                              @RequestParam(name = "limit", defaultValue = "10") int length){
        return telemetryService.queryTelemetryDatas(startTm, endTm, stdm, valtyp, state, pageIndex, length);
    }

    @PostMapping("/queryStationData")
    @ResponseBody
    public  Result queryStationData(@RequestParam(name = "stcd", defaultValue = "") String stcd,
                                    @RequestParam(name = "valtype", defaultValue = "") String valtyp,
                                    @RequestParam(name = "page", defaultValue = "1") int pageIndex,
                                    @RequestParam(name = "limit", defaultValue = "10") int length){
        return telemetryService.queryStationData(valtyp, stcd, pageIndex, length);
    }
}
