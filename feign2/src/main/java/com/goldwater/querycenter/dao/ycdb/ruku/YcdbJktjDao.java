package com.goldwater.querycenter.dao.ycdb.ruku;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@org.apache.ibatis.annotations.Mapper
public interface YcdbJktjDao {
    String getSttp(@Param("stcd") String stcd);

    List<Map> getStation_13(@Param("time") String time, @Param("id13Stcd") String id13Stcd);

    List<Map> getStation_24(@Param("time") String time, @Param("idrrStcd") String idrrStcd, @Param("idddStcd") String idddStcd, @Param("iddpStcd") String iddpStcd, @Param("idppStcd") String idppStcd);
}
