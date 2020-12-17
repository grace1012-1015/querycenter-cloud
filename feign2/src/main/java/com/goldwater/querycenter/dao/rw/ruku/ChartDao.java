package com.goldwater.querycenter.dao.rw.ruku;

import com.goldwater.querycenter.entity.ruku.SingleHourRain;
import com.goldwater.querycenter.entity.ruku.SingleHourRiver;
import com.goldwater.querycenter.entity.ruku.SingleRZWRelation;
import com.goldwater.querycenter.entity.ruku.SingleZqrlRelation;
import com.goldwater.querycenter.entity.ruku.vo.SingleRvffchVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface ChartDao {
    List<SingleHourRain> getSingleHourRain(@Param("stcd") String stcd, @Param("sttm") String sttm, @Param("entm") String entm);

    List<SingleHourRiver> getSingleHourRiver(@Param("stcd") String stcd, @Param("sttm") String sttm, @Param("entm") String entm);

    List<SingleRvffchVo> getSingleRvffch(@Param("stcd") String stcd);

    List<SingleRZWRelation> getSingleRZWRelation(@Param("stcd") String stcd);

    List<SingleZqrlRelation> getSingleZqrlRelation(@Param("stcd") String stcd);
}
