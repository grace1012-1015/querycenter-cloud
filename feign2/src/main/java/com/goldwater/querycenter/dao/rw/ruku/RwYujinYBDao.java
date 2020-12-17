package com.goldwater.querycenter.dao.rw.ruku;

import com.goldwater.querycenter.entity.ruku.vo.RiverVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface RwYujinYBDao {
    List<RiverVo> queryRiver(@Param("stcd") String stcd, @Param("startTm") String startTm, @Param("endTm") String endTm);
}
