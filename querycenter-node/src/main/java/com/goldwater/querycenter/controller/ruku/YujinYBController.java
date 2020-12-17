package com.goldwater.querycenter.controller.ruku;

import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.service.ruku.YujinYBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/yujinyb")
public class YujinYBController {
    @Autowired
    private YujinYBService yujinYBService;

    /**
     * 查询水位预警
     */
    @PostMapping("/query")
    @ResponseBody
    public Result query(@RequestParam(name = "stdm", defaultValue = "") String stdm,
                        @RequestParam(name = "starttm", defaultValue = "") String startTm,
                        @RequestParam(name = "endtm", defaultValue = "") String endTm){
        return yujinYBService.querySwWaterLevelJk(stdm, startTm, endTm);
    }
}
