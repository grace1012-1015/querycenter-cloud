package com.goldwater.querycenter.dao.ycdb.management;

import com.goldwater.querycenter.entity.management.User;
import com.goldwater.querycenter.entity.management.vo.UserVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface UserDao extends Mapper<User> {
    List<UserVo> getUserList(@Param("uName") String uName, @Param("uCode") String uCode, @Param("uStateQuery") String uStateQuery, @Param("uRoleQuery") String uRoleQuery);

    User getUser(@Param("uCode") String uCode);

    int updateStatus(@Param("uState") String uState, @Param("uCodeQuery") String uCodeQuery);

    int deleteUsers(@Param("uCodeQuery") String uCodeQuery);
}
