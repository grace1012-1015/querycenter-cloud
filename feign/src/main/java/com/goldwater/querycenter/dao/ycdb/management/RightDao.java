package com.goldwater.querycenter.dao.ycdb.management;

import com.goldwater.querycenter.entity.management.*;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface RightDao extends Mapper<Priviliges> {
    List<Menu> getRightsMenuByRightId(@Param("rightsId") String rightsId);

    List<Menu> getMenuByUserId(@Param("userId") String userId);

    List<Priviliges> getRightByUserId(@Param("userId") String userId);

    List<Menu> getParentMenuList(@Param("menuId") String menuId);

    List<PriviligeStcds> getPrvStationList(@Param("privilegeId") String privilegeId);

    List<PriviligeMenu> getPrvMenuList(@Param("privilegeId") String privilegeId);

    List<Menu> getMenuList();

    List<UserPrivilige> getUserPrivilege(@Param("uCode") String uCode);

    int addUserPrivilege(@Param("uCode") String uCode, @Param("privilegeId") String privilegeId);

    int updateUserPrivilege(@Param("uCode") String uCode, @Param("privilegeId") String privilegeId);

    int addPrivilegeMenu(@Param("privilegeId") String privilegeId, @Param("list") List<String> list);

    int addPrvStation(@Param("privilegeId") String privilegeId, @Param("list") List<String> list);

    int removePrvMenu(@Param("privilegeId") String privilegeId);

    int removePrvStation(@Param("privilegeId") String privilegeId);

    int removeUserRight(@Param("privilegeId") String privilegeId);

    int addMenu(@Param("menuId") String menuId, @Param("menuNm") String menuNm, @Param("content") String content, @Param("ordr") String ordr, @Param("prt_id") String prt_id);

    int updateMenu(@Param("menuId") String menuId, @Param("menuNm") String menuNm, @Param("content") String content, @Param("ordr") String ordr);

    int removeMenu(@Param("list") List<String> list);
}
