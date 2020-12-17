package com.goldwater.querycenter.controller.ruku;

import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.service.ruku.FlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/flow")
public class FlowController {
    @Autowired
    private FlowService flowService;

    /**
     * 查询五分钟流速
     */
    @PostMapping("/queryFlowFm")
    @ResponseBody
    public Result queryFlowFm(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                               @RequestParam(name = "STARTTM", defaultValue = "") String startTm,
                               @RequestParam(name = "ENDTM", defaultValue = "") String endTm,
                               @RequestParam(name = "page", defaultValue = "1") int pageIndex,
                               @RequestParam(name = "limit", defaultValue = "10") int length){
        return flowService.queryFlow_R5(stcd, startTm, endTm, pageIndex, length);
    }

    /**
     * 查询五分钟流量
     */
    @PostMapping("/queryFluxFm")
    @ResponseBody
    public Result queryFluxFm(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                              @RequestParam(name = "STARTTM", defaultValue = "") String startTm,
                              @RequestParam(name = "ENDTM", defaultValue = "") String endTm){
        return flowService.queryFluxFm(stcd, startTm, endTm);
    }

    /**
     * 查询泵闸站开关情况
     */
    @PostMapping("/getPickFluxList")
    @ResponseBody
    public Result getPickFluxList(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                               @RequestParam(name = "EnterFlow", defaultValue = "") String enterFlow,
                               @RequestParam(name = "page", defaultValue = "1") int pageIndex,
                               @RequestParam(name = "limit", defaultValue = "10") int length){
        return flowService.getPickFluxList(stcd, enterFlow, pageIndex, length);
    }

    /**
     * 添加开关泵闸信息
     *
     */
    @PostMapping("/addPickFlux")
    @ResponseBody
    public Result addPickFlux(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                              @RequestParam(name = "STNM", defaultValue = "") String stnm,
                              @RequestParam(name = "EnterFlow", defaultValue = "") String enterFlow){
        return flowService.addPickFlux(stcd, stnm, enterFlow);
    }

    /**
     * 检查新增开关泵闸站数据是否存在
     * @param stcd,stnm
     */
    @PostMapping("/checkPickFlow")
    @ResponseBody
    public Result checkPickFlow(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                              @RequestParam(name = "STNM", defaultValue = "") String stnm){
        return flowService.checkPickFlow(stcd, stnm);
    }

    /**
     * 修改开关泵闸信息
     *
     */
    @PostMapping("/updatePickFlow")
    @ResponseBody
    public Result updatePickFlow(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                                 @RequestParam(name = "STNM", defaultValue = "") String stnm,
                                 @RequestParam(name = "EnterFlow", defaultValue = "") String enterFlow,
                                 @RequestParam(name = "oldStcd", defaultValue = "") String oldStcd,
                                 @RequestParam(name = "oldStnm", defaultValue = "") String oldStnm){
        return flowService.updatePickFlow(stcd, stnm, enterFlow, oldStcd, oldStnm);
    }

    /**
     * 删除开关泵闸信息
     *
     */
    @PostMapping("/delPickFlow")
    @ResponseBody
    public Result delPickFlow(@RequestParam(name = "IDS", defaultValue = "") String ids){
        return flowService.delPickFlow(ids);
    }

    /**
     * 单条开关泵闸信息数据查
     */
    @PostMapping("/getPickFlux")
    @ResponseBody
    public Result getPickFlux(@RequestParam(name = "STCD", defaultValue = "") String stcd,
                              @RequestParam(name = "STNM", defaultValue = "") String stnm){
        return flowService.getPickFlux(stcd, stnm);
    }

    /**
     * 导出测站的五分钟流速数据excel文件
     */
    @PostMapping("/exportFlow5r")
    @ResponseBody
    public void exportFlow5r(@RequestParam(name = "stdm", defaultValue = "") String stcd,
                               @RequestParam(name = "starttm", defaultValue = "") String startTm,
                               @RequestParam(name = "endtm", defaultValue = "") String endTm,
                               HttpServletRequest request,
                               HttpServletResponse response){
        Result rs = new Result();

        try {
            flowService.exportFlow5r(stcd, startTm, endTm, response);
        }
        catch (Exception e) {
            rs.setCode(Result.FAILURE);
        }
    }

    /**
     * 导出测站的五分钟流量数据excel文件
     * @return
     */
    @PostMapping("/exportFlux5r")
    @ResponseBody
    public void exportFlux5r(@RequestParam(name = "stdm", defaultValue = "") String stcd,
                             @RequestParam(name = "starttm", defaultValue = "") String startTm,
                             @RequestParam(name = "endtm", defaultValue = "") String endTm,
                             HttpServletRequest request,
                             HttpServletResponse response){
        Result rs = new Result();

        try {
            flowService.exportFlux5r(stcd, startTm, endTm, response);
        }
        catch (Exception e) {
            rs.setCode(Result.FAILURE);
        }
    }
}
