package com.goldwater.querycenter.dao.ycdb.ruku;

import com.goldwater.querycenter.entity.ruku.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface WaterDao {
    List<RiverR5Vo> queryRiver_R5_Level3(@Param("priviligeId") String priviligeId, @Param("stdm") String stdm, @Param("startTm") String startTm, @Param("endTm") String endTm);

    List<RiverR5Vo> queryRiver_R5(@Param("stdm") String stdm, @Param("startTm") String startTm, @Param("endTm") String endTm);

    List<RsvrR5Vo> queryRsvr_R5_Level3(@Param("priviligeId") String priviligeId, @Param("stdm") String stdm, @Param("startTm") String startTm, @Param("endTm") String endTm);

    List<RsvrR5Vo> queryRsvr_R5(@Param("stdm") String stdm, @Param("startTm") String startTm, @Param("endTm") String endTm);

    List<WasR5Vo> queryWas_R5_Level3(@Param("priviligeId") String priviligeId, @Param("stdm") String stdm, @Param("startTm") String startTm, @Param("endTm") String endTm);

    List<WasR5Vo> queryWas_R5(@Param("stdm") String stdm, @Param("startTm") String startTm, @Param("endTm") String endTm);

    List<PumpR5Vo> queryPump_R5_Level3(@Param("priviligeId") String priviligeId, @Param("stdm") String stdm, @Param("startTm") String startTm, @Param("endTm") String endTm);

    List<PumpR5Vo> queryPump_R5(@Param("stdm") String stdm, @Param("startTm") String startTm, @Param("endTm") String endTm);
}
