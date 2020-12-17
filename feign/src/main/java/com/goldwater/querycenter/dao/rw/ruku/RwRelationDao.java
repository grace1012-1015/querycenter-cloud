package com.goldwater.querycenter.dao.rw.ruku;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@org.apache.ibatis.annotations.Mapper
public interface RwRelationDao {
    int updateZqrl(@Param("stcd") String stcd, @Param("ptno") String ptno, @Param("z") String z, @Param("q") String q);

    int insertZqrl(@Param("stcd") String stcd, @Param("lnnm") String lnnm, @Param("bgtm") String bgtm, @Param("ptno") String ptno, @Param("z") String z, @Param("q") String q);

    String getMaxZqrlPtno(@Param("stcd") String stcd);

    int updateZvarl(@Param("stcd") String stcd, @Param("ptno") String ptno, @Param("rz") String rz, @Param("w") String w, @Param("wsfa") String wsfa);

    int insertZvarl(@Param("stcd") String stcd, @Param("ptno") String ptno, @Param("rz") String rz, @Param("w") String w, @Param("wsfa") String wsfa);

    String getMaxZvarlPtno(@Param("stcd") String stcd);

    int deleteZvarl(@Param("list") List<Map> list);

    String getStlc(@Param("stcd") String stcd);

    int addAllZqrlToOrcl(@Param("list") List<Map> list);

    int addAllZvarlToOrcl(@Param("list") List<Map> list);
}
