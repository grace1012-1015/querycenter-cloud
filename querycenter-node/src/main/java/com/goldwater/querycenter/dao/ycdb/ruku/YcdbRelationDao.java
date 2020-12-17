package com.goldwater.querycenter.dao.ycdb.ruku;

import com.goldwater.querycenter.entity.ruku.StConfig;
import com.goldwater.querycenter.entity.ruku.vo.CosstVo;
import com.goldwater.querycenter.entity.ruku.vo.ZqrlRelationVo;
import com.goldwater.querycenter.entity.ruku.vo.ZvarlRelationVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@org.apache.ibatis.annotations.Mapper
public interface YcdbRelationDao {
    List<ZqrlRelationVo> queryZqrlList_Level3(@Param("priviligeId") String priviligeId, @Param("stcd") String stcd);

    List<ZqrlRelationVo> queryZqrlList(@Param("stcd") String stcd);

    ZqrlRelationVo getZqrl(@Param("stcd") String stcd, @Param("ptno") String ptno, @Param("yr") String yr);

    int deleteZqrl(@Param("list") List<Map> list);

    List<ZvarlRelationVo> getZvarlList_Level3(@Param("priviligeId") String priviligeId, @Param("stcd") String stcd);

    List<ZvarlRelationVo> getZvarlList(@Param("stcd") String stcd);

    ZvarlRelationVo getZvarl(@Param("stcd") String stcd, @Param("ptno") String ptno);

    List<CosstVo> getCosstList(@Param("stdm") String stdm, @Param("id") String id);

    List<Map> getCosst(@Param("stcd") String stcd, @Param("id") String id);

    List<StConfig> getConfigList(@Param("stdm") String stdm, @Param("sttp") String sttp);

    StConfig getConfig(@Param("stcd") String stcd, @Param("sttp") String sttp);

    int addConfig(@Param("stcd") String stcd, @Param("stnm") String stnm, @Param("sttp") String sttp, @Param("pmax5m") String pmax5m, @Param("pmax1h") String pmax1h, @Param("zmin") String zmin, @Param("zmax") String zmax, @Param("timect") String timect, @Param("srage") boolean srage, @Param("dye") boolean dye, @Param("wtmp") boolean wtmp, @Param("gate") boolean gate, @Param("volgate") boolean volgate, @Param("soil") boolean soil, @Param("velocity") boolean velocity, @Param("ott") boolean ott);

    int updateConfig(@Param("stcd") String stcd, @Param("stnm") String stnm, @Param("sttp") String sttp, @Param("pmax5m") String pmax5m, @Param("pmax1h") String pmax1h, @Param("zmin") String zmin, @Param("zmax") String zmax, @Param("timect") String timect, @Param("srage") boolean srage, @Param("dye") boolean dye, @Param("wtmp") boolean wtmp, @Param("gate") boolean gate, @Param("volgate") boolean volgate, @Param("soil") boolean soil, @Param("velocity") boolean velocity, @Param("ott") boolean ott);

    int deleteConfig(@Param("list") List<Map> list);

    int addAllZqrl(@Param("list") List<Map> list);

    int addAllZvarl(@Param("list") List<Map> list);
}
