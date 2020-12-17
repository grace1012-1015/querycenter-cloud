package com.goldwater.querycenter.dao.ycdb.ruku;

import com.goldwater.querycenter.entity.ruku.vo.CosstVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface YcdbYujinYBDao {
    List<CosstVo> queryCosst_Level3(@Param("priviligeId") String priviligeId, @Param("stdm") String stdm, @Param("customer") String customer);

    List<CosstVo> queryCosst(@Param("stdm") String stdm, @Param("customer") String customer);
}
