package com.goldwater.querycenter.dao.rw;

import com.goldwater.querycenter.entity.Test;

import java.util.List;
import java.util.Map;

@org.apache.ibatis.annotations.Mapper
public interface RwPublicDao {
    List<Test> datas(Map map);

    int updateAddvcdByCd(Test test);

    int insertAddvcd(Test test);

    int deleteAddvcdByCd(Test test);

    int testDatas();

    List<Map> selectPublicItemList();
}
