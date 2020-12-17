package com.goldwater.querycenter.dao.ycdb.ruku;

import com.goldwater.querycenter.entity.ruku.vo.LastDataVo;
import com.goldwater.querycenter.entity.ruku.vo.LastSiteVo;
import com.goldwater.querycenter.entity.ruku.vo.MessageVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@org.apache.ibatis.annotations.Mapper
public interface MessageDao {
    List<MessageVo> query_level3(@Param("priviligeId") String priviligeId, @Param("stcd") String stcd, @Param("sendTime") String sendTime, @Param("num") String num);

    List<MessageVo> query(@Param("stcd") String stcd, @Param("sendTime") String sendTime, @Param("num") String num );

    List<LastSiteVo> getLatestSite();

    List<LastDataVo> getLatestData(@Param("stcd") String stcd, @Param("ymdhm") String ymdhm);

    List<LastDataVo> getDayMessage(@Param("stcd") String stcd, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
