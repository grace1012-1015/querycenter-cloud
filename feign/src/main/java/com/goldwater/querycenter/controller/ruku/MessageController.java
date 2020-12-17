package com.goldwater.querycenter.controller.ruku;

import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.service.ruku.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    /**
     * 雨量统计
     */
    @PostMapping("/query")
    @ResponseBody
    public Result query(@RequestParam(name = "stcd", defaultValue = "") String stcd,
                        @RequestParam(name = "num", defaultValue = "") String num){
        return messageService.query(stcd, num);
    }

    /**
     * 	查询最新的数据
     */
    @PostMapping("/getLatestData")
    @ResponseBody
    public Result getLatestData(@RequestParam(name = "page", defaultValue = "1") int pageIndex,
                                @RequestParam(name = "limit", defaultValue = "10") int length){
        return messageService.getLatestData(pageIndex, length);
    }

    /**
     * 	获取单个站点某天的数据
     */
    @PostMapping("/getSingleLatest")
    @ResponseBody
    public Result getSingleLatest(@RequestParam(name = "stcd", defaultValue = "") String stcd,
                                @RequestParam(name = "tm", defaultValue = "") String tm,
                                @RequestParam(name = "page", defaultValue = "1") int pageIndex,
                                @RequestParam(name = "limit", defaultValue = "10") int length){
        return messageService.getSingleLatest(stcd, tm, pageIndex, length);
    }
}
