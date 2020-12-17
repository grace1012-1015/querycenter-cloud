package com.goldwater.querycenter.dao.ycdb;

import java.util.List;
import java.util.Map;

@org.apache.ibatis.annotations.Mapper
public interface YcdbPublicDao {
    int datas();

    List<Map> selectPublicItemList();
}
