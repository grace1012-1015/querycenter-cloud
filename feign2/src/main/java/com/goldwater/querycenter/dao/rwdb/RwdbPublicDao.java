package com.goldwater.querycenter.dao.rwdb;

import java.util.List;
import java.util.Map;

@org.apache.ibatis.annotations.Mapper
public interface RwdbPublicDao {
    int datas();

    List<Map> selectPublicItemList();
}
