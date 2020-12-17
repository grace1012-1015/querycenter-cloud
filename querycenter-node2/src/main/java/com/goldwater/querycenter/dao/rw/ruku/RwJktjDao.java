package com.goldwater.querycenter.dao.rw.ruku;

import com.goldwater.querycenter.entity.ruku.vo.PptnVo;
import com.goldwater.querycenter.entity.ruku.vo.RiverVo;
import com.goldwater.querycenter.entity.ruku.vo.StcdZVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@org.apache.ibatis.annotations.Mapper
public interface RwJktjDao {
    List<Map> querySoil(@Param("list") List<Map> list, @Param("stcd") String stcd);

    List<Map> querySwRainJk(@Param("list") List<Map> list, @Param("stcd") String stcd, @Param("time") String time, @Param("tomorTime") String tomorTime);

    List<Map> querySwWaterJk(@Param("list") List<Map> list, @Param("stcd") String stcd, @Param("time") String time, @Param("tomorTime") String tomorTime, String upOrDown, String ppupOrPpdown);

    List<PptnVo> querySwRainTJ(@Param("stcd") String stcd, @Param("time") String time, @Param("timeInterval") String timeInterval);

    List<PptnVo> querySwRainTJ_00(@Param("stcd") String stcd, @Param("timeInt") String timeInt);

    List<PptnVo> querySwRainTJ2(@Param("stcd") String stcd, @Param("nowTime") String nowTime, @Param("oldTime") String oldTime);

    List<PptnVo> querySwRainTJ2_00(@Param("stcd") String stcd, @Param("nowTime") String nowTime);

    List<StcdZVo> getStcdZ_1Or3(@Param("time") String time);

    List<StcdZVo> getStcdZ_2Or4(@Param("time") String time);

    List<StcdZVo> getStcdZ(@Param("time") String time);

    List<Map> getStbprp(@Param("id24Stcd") String id24Stcd);
}
