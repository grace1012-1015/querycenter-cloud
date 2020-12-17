package com.goldwater.querycenter.dao.ycdb.ruku;

import com.goldwater.querycenter.entity.ruku.Cosst;
import com.goldwater.querycenter.entity.ruku.vo.CosstVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@org.apache.ibatis.annotations.Mapper
public interface CosstDao extends Mapper<Cosst> {
    String getMaxOrdr(@Param("stcd") String stcd, @Param("id") String id);

    int deleteCosst(@Param("list") List<Map> list);

    int updateCosst(@Param("arnm") String arnm, @Param("stnm") String stnm, @Param("ordr") String ordr, @Param("stcd") String stcd, @Param("id") String id);

    CosstVo getCosst(@Param("stcd") String stcd, @Param("id") String id);

    List<CosstVo> getSoilCosst_Level3(@Param("privilegeId") String privilegeId, @Param("stcd") String stcd);

    List<CosstVo> getSoilCosst(@Param("stcd") String stcd);

    List<CosstVo> getSwRainCosst_Level3(@Param("privilegeId") String privilegeId, @Param("stcd") String stcd, @Param("type") String type, @Param("custom") String custom, @Param("addvcd") String addvcd);

    List<CosstVo> getSwRainCosst(@Param("stcd") String stcd, @Param("type") String type, @Param("custom") String custom, @Param("addvcd") String addvcd);
}