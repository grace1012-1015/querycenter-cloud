package com.goldwater.querycenter.dao.rw.ruku;

import com.goldwater.querycenter.entity.ruku.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@org.apache.ibatis.annotations.Mapper
public interface QbtjDao {
    List<Map<String, Object>> queryRain(@Param("list") List<QueryRainPo> list, @Param("stdm") String stdm);
}
