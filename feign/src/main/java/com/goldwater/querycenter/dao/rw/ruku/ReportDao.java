package com.goldwater.querycenter.dao.rw.ruku;

import com.goldwater.querycenter.entity.ruku.ReportConfigMetaData;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@org.apache.ibatis.annotations.Mapper
public interface ReportDao {
    List<ReportConfigMetaData> getReportConfigMetaData(@Param("rid") String rid);

    List<Map<String, Object>> getSqjbList(@Param("rid") String rid, @Param("list") List<ReportConfigMetaData> list);

    Integer getMaxSortValue(@Param("mid") String mid);

    int addSqjb(@Param("list") List<Map> list);

    int deleteSqjb(@Param("list") List<Map> list);
}