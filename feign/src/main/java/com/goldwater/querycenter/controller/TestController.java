package com.goldwater.querycenter.controller;

import com.goldwater.querycenter.common.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.goldwater.querycenter.service.*;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestService testService;

    @PostMapping("/datas")
    @ResponseBody
    public Result datas(@RequestParam(name = "addvcd", defaultValue = "") String addvcd,
                        @RequestParam(name = "page", defaultValue = "1") int pageIndex,
                        @RequestParam(name = "limit", defaultValue = "10") int length){
        return testService.datas(addvcd, pageIndex, length);
    }

    @PostMapping("/updateAddvcdByCd")
    @ResponseBody
    public Result updateAddvcdByCd(){
        return testService.updateAddvcdByCd();
    }

    @PostMapping("/insertAddvcd")
    @ResponseBody
    public Result insertAddvcd(){
        return testService.insertAddvcd();
    }

    @PostMapping("/deleteAddvcdByCd")
    @ResponseBody
    public Result deleteAddvcdByCd(){
        return testService.deleteAddvcdByCd();
    }

    @PostMapping("/redisDatas")
    @ResponseBody
    public Result redisDatas(){
        return testService.redisDatas();
    }


    @PostMapping("/ycdbDatas")
    @ResponseBody
    public Result ycdbDatas() {
        return testService.ycdbDatas();
    }

    @PostMapping("/mutiDatas")
    @ResponseBody
    public Result mutiDatas() {
        return testService.mutiDatas();
    }

    @PostMapping("/hello")
    @ResponseBody
    public String hello() {
        return testService.hello();
    }
}
