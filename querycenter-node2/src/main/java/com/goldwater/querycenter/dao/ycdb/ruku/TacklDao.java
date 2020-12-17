package com.goldwater.querycenter.dao.ycdb.ruku;

import com.goldwater.querycenter.entity.ruku.vo.TacklVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface TacklDao {
    List<TacklVo> queryTackl_Level3(@Param("priviligeId") String priviligeId, @Param("dyp") String dyp, @Param("stdm") String stdm,  @Param("startTm") String startTm, @Param("endTm") String endTm);

    List<TacklVo> queryTackl(@Param("dyp") String dyp, @Param("stdm") String stdm,  @Param("startTm") String startTm, @Param("endTm") String endTm);
}
