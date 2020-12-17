package com.goldwater.querycenter.dao.ycdb.ruku;

import com.goldwater.querycenter.entity.ruku.vo.FiveMinuteRainVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@org.apache.ibatis.annotations.Mapper
public interface RainDao {
    List<FiveMinuteRainVo> queryFiveMinuteRain_Level3(@Param("priviligeId") String priviligeId, @Param("stcd") String stcd, @Param("startTm") String startTm, @Param("endTm") String endTm);

    List<FiveMinuteRainVo> queryFiveMinuteRain(@Param("stcd") String stcd, @Param("startTm") String startTm, @Param("endTm") String endTm);

    List<FiveMinuteRainVo> queryRainChart(@Param("stcd") String stcd, @Param("time") String time);
}
