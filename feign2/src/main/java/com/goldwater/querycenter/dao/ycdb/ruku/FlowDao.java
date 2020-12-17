package com.goldwater.querycenter.dao.ycdb.ruku;

import com.goldwater.querycenter.entity.ruku.PickFlux;
import com.goldwater.querycenter.entity.ruku.vo.FlowR5Vo;
import com.goldwater.querycenter.entity.ruku.vo.FluxR5Vo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface FlowDao extends Mapper<PickFlux> {
    List<FlowR5Vo> queryFlow_R5_Level3(@Param("priviligeId") String priviligeId, @Param("stcd") String stcd, @Param("startTm") String startTm, @Param("endTm") String endTm);

    List<FlowR5Vo> queryFlow_R5(@Param("stcd") String stcd, @Param("startTm") String startTm, @Param("endTm") String endTm);

    List<FluxR5Vo> queryFlux_R5_Level3(@Param("priviligeId") String priviligeId, @Param("stcd") String stcd, @Param("startTm") String startTm, @Param("endTm") String endTm);

    List<FluxR5Vo> queryFlux_R5(@Param("stcd") String stcd, @Param("startTm") String startTm, @Param("endTm") String endTm);

    List<PickFlux> getPickFluxList(@Param("stcd") String stcd, @Param("enterFlow") String enterFlow);

    int updatePickFlow(@Param("stcd") String stcd, @Param("stnm") String stnm, @Param("enterFlow") String enterFlow, @Param("oldStcd") String oldStcd, @Param("oldStnm") String oldStnm);

    int delPickFlow(@Param("list")List<Map> list);

    PickFlux getPickFlux(@Param("stcd") String stcd, @Param("stnm") String stnm);
}
