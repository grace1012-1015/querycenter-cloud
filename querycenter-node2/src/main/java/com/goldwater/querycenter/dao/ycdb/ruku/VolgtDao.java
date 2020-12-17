package com.goldwater.querycenter.dao.ycdb.ruku;

import com.goldwater.querycenter.entity.ruku.VolGate;
import com.goldwater.querycenter.entity.ruku.vo.AnomalyVoltageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface VolgtDao {
    List<VolGate> queryChart(@Param("stdm") String stdm, @Param("time") String time);

    List<AnomalyVoltageVo> queryAnomalyVoltage_Level3_start_end(@Param("priviligeId") String priviligeId, @Param("startTm") String startTm, @Param("endTm") String endTm, @Param("stcd") String stcd, @Param("volGate") String volGate);

    List<AnomalyVoltageVo> queryAnomalyVoltage_Level3_end(@Param("priviligeId") String priviligeId, @Param("endTm") String endTm, @Param("stcd") String stcd, @Param("volGate") String volGate);

    List<AnomalyVoltageVo> queryAnomalyVoltage_Level3(@Param("priviligeId") String priviligeId, @Param("stcd") String stcd, @Param("volGate") String volGate);

    List<AnomalyVoltageVo> queryAnomalyVoltage_start_end(@Param("startTm") String startTm, @Param("endTm") String endTm, @Param("stcd") String stcd, @Param("volGate") String volGate);

    List<AnomalyVoltageVo> queryAnomalyVoltage_end(@Param("endTm") String endTm, @Param("stcd") String stcd, @Param("volGate") String volGate);

    List<AnomalyVoltageVo> queryAnomalyVoltage(@Param("stcd") String stcd, @Param("volGate") String volGate);

}
