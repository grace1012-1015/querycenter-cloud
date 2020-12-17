package com.goldwater.querycenter.controller.ruku;

import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.service.ruku.TacklService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tackl")
public class TacklController {
    @Autowired
    private TacklService tacklService;

    @PostMapping("/query")
    @ResponseBody
    public Result query(@RequestParam(name = "DYP", defaultValue = "") String dyo,
                        @RequestParam(name = "STDM", defaultValue = "") String stdm,
                        @RequestParam(name = "STARTTM", defaultValue = "") String startTm,
                        @RequestParam(name = "ENDTM", defaultValue = "") String endTm,
                        @RequestParam(name = "page", defaultValue = "1") int pageIndex,
                        @RequestParam(name = "limit", defaultValue = "10") int length){
        return tacklService.query(dyo, stdm, startTm, endTm, pageIndex, length);
    }
}
