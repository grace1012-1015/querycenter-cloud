package com.goldwater.querycenter.dao.ycdb.ruku;

import com.goldwater.querycenter.entity.ruku.vo.RtsrVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface TelemetryDao {
    List<RtsrVo> queryTelemetryDatas_Level3(@Param("startTm") String startTm, @Param("endTm") String endTm, @Param("stdm") String stdm, @Param("valtyp") String valtyp, @Param("state") String state);

    List<RtsrVo> queryTelemetryDatas(@Param("startTm") String startTm, @Param("endTm") String endTm, @Param("stdm") String stdm, @Param("valtyp") String valtyp, @Param("state") String state);

    List<RtsrVo> queryStationData(@Param("valtyp") String valtyp, @Param("stcd") String stcd);
}
