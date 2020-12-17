package com.goldwater.querycenter.dao.rtuex;

import java.util.List;
import java.util.Map;

@org.apache.ibatis.annotations.Mapper
public interface RtuexPublicDao {
    int datas();

    List<Map> selectPublicItemList();
}
