package com.goldwater.querycenter.controller.management;

import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.service.management.RightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/right")
public class RightController {
    @Autowired
    private RightService rightService;

    @PostMapping("/getRightsList")
    @ResponseBody
    public Result getRightsList(@RequestParam(name = "page", defaultValue = "1") int pageIndex,
                                @RequestParam(name = "limit", defaultValue = "10") int length){
        return rightService.getRightsList(pageIndex, length);
    }

    @PostMapping("/getMenuList")
    @ResponseBody
    public Result getMenuList(@RequestParam(name = "page", defaultValue = "1") int pageIndex,
                              @RequestParam(name = "limit", defaultValue = "10") int length){
        return rightService.getMenuList(pageIndex, length);
    }

    @PostMapping("/getPrvMenuList")
    @ResponseBody
    public Result getPrvMenuList(@RequestParam(name = "privilegeId", defaultValue = "") String privilegeId,
                                 @RequestParam(name = "page", defaultValue = "1") int pageIndex,
                                 @RequestParam(name = "limit", defaultValue = "10") int length){
        return rightService.getPrvMenuList( privilegeId, pageIndex, length);
    }

    @PostMapping("/getPrvStationList")
    @ResponseBody
    public Result getPrvStationList(@RequestParam(name = "privilegeId", defaultValue = "") String privilegeId,
                                    @RequestParam(name = "page", defaultValue = "1") int pageIndex,
                                    @RequestParam(name = "limit", defaultValue = "10") int length){
        return rightService.getPrvStationList( privilegeId, pageIndex, length);
    }

    @PostMapping("/updateRights")
    @ResponseBody
    public Result updateRights(@RequestParam(name = "privilegeId", defaultValue = "") String privilegeId,
                               @RequestParam(name = "name", defaultValue = "") String name,
                               @RequestParam(name = "level", defaultValue = "") String level,
                               @RequestParam(name = "ordr", defaultValue = "") String ordr,
                               @RequestParam(name = "menus", defaultValue = "") String menus,
                               @RequestParam(name = "stations", defaultValue = "") String stations){
        return rightService.updateRights(privilegeId, name, level, ordr, menus, stations);
    }

    @PostMapping("/addRights")
    @ResponseBody
    public Result addRights(@RequestParam(name = "name", defaultValue = "") String name,
                            @RequestParam(name = "level", defaultValue = "") String level,
                            @RequestParam(name = "ordr", defaultValue = "") String ordr,
                            @RequestParam(name = "menus", defaultValue = "") String menus,
                            @RequestParam(name = "stations", defaultValue = "") String stations){
        return rightService.addRights(name, level, ordr, menus, stations);
    }

    @PostMapping("/removeRights")
    @ResponseBody
    public Result removeRights(@RequestParam(name = "privilegeId", defaultValue = "") String privilegeId){
        return rightService.removeRights(privilegeId);
    }

    @PostMapping("/getMenuTree")
    @ResponseBody
    public Result getMenuTree(){
        //todo
        return null;
    }

    @PostMapping("/updateMenu")
    @ResponseBody
    public Result updateMenu(@RequestParam(name = "menuId", defaultValue = "") String menuId,
                             @RequestParam(name = "menuNm", defaultValue = "") String menuNm,
                             @RequestParam(name = "content", defaultValue = "") String content,
                             @RequestParam(name = "ordr", defaultValue = "") String ordr){
        return rightService.updateMenu(menuId, menuNm, content, ordr);
    }

    @PostMapping("/addMenu")
    @ResponseBody
    public Result addMenu(@RequestParam(name = "menuNm", defaultValue = "") String menuNm,
                          @RequestParam(name = "content", defaultValue = "") String content,
                          @RequestParam(name = "ordr", defaultValue = "") String ordr,
                          @RequestParam(name = "prt_id", defaultValue = "") String prt_id){
        return rightService.addMenu(menuNm, content, ordr, prt_id);
    }

    @PostMapping("/removeMenu")
    @ResponseBody
    public Result removeMenu(@RequestParam(name = "menuId", defaultValue = "") String menuId){
        return rightService.removeMenu(menuId);
    }

    @PostMapping("/getRightsMenuList")
    @ResponseBody
    public  Result getRightsMenuList(){
        return rightService.getRightsMenuList();
    }
}
