package com.goldwater.querycenter.service.management;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.common.util.ToolkitsBase;
import com.goldwater.querycenter.common.util.string.StringUtil;
import com.goldwater.querycenter.dao.ycdb.management.RightDao;
import com.goldwater.querycenter.dao.ycdb.management.UserDao;
import com.goldwater.querycenter.entity.management.User;
import com.goldwater.querycenter.entity.management.UserPrivilige;
import com.goldwater.querycenter.entity.management.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.goldwater.querycenter.common.security.*;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RightDao rightDao;

    public Result getUserList(String uName,String uCode, String uState, String uRole, int pageIndex, int length){
        Result rs = new Result();
        PageHelper.startPage(pageIndex, length);
        String uStateQuery = "";
        String uRoleQuery = "";

        uStateQuery = ToolkitsBase.getQueryStcds("u.USTATE", uState);
        uRoleQuery = ToolkitsBase.getQueryStcds("u.UROLE", uRole);

        List<UserVo> userList = userDao.getUserList(uName, uCode, uStateQuery, uRoleQuery);

        PageInfo p = new PageInfo<>(userList);
        rs.setData(p);
        rs.setTotal(Integer.parseInt(p.getTotal() + ""));
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result getUser(String uCode){
        Result rs = new Result();

        if (StringUtil.isBlank(uCode)){
            rs.setCode(Result.FAILURE);
            rs.setMsg("UCODE不能为空！");

            return rs;
        }

        User user = userDao.getUser(uCode);

        user.setUserPwd("");

        rs.setData(user);
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result checkUcode(String uCode){
        Result rs = new Result();
        User user = userDao.getUser(uCode);
        Map map = new HashMap();

        if (user == null){
            map.put("STATUS", false);
        }
        else{
            map.put("STATUS", true);
        }

        rs.setData(map);
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result addUser(String uCode, String uPwd, String uCompany, String uCbrief, String uCtel, String uName, String uEmail, String uPhone, String uKey, String uState, String uRole){
        Result rs = new Result();
        User u = new User();
        Map map = new HashMap();

        u.setUserCode(uCode);
        u.setUserPwd(Crypt.md5Encrypt(uPwd));
        u.setUserCompany(uCompany);
        u.setUserCbrief(uCbrief);
        u.setUserCtel(uCtel);
        u.setUserName(uName);
        u.setUserEmail(uEmail);
        u.setUserPhone(uPhone);
        u.setUserKey(uKey);
        u.setUserState(uState);
        u.setUserRole(uRole);

        if (userDao.insertSelective(u) > 0){
            map.put("ERRNO", "0");
            map.put("user", u);

            rs.setCode(Result.SUCCESS);
        }
        else{
            map.put("ERRNO", "ERR01");
            map.put("ERRMAS", "新增用户失败");

            rs.setCode(Result.FAILURE);
        }

        rs.setData(map);

        return rs;
    }

    public Result updateUser(String uCode, String uPwd, String uCompany, String uCbrief, String uCtel, String uName, String uEmail, String uPhone, String uKey, String uState, String uRole, String privilegeId){
        Result rs = new Result();
        User u = new User();
        Map map = new HashMap();

        if (StringUtil.isBlank(privilegeId)){
            rs.setCode(Result.FAILURE);
            rs.setMsg("privilegeId不能为空！");

            return rs;
        }

        u.setUserCode(uCode);
        u.setUserCompany(uCompany);
        u.setUserCbrief(uCbrief);
        u.setUserCtel(uCtel);
        u.setUserName(uName);
        u.setUserEmail(uEmail);
        u.setUserPhone(uPhone);
        u.setUserState(uState);

        if (userDao.updateByPrimaryKeySelective(u) > 0){
            List<UserPrivilige> list = rightDao.getUserPrivilege(uCode);

            if (list.size() > 0){
                rightDao.updateUserPrivilege(uCode, privilegeId);
            }
            else{
                rightDao.addUserPrivilege(uCode, privilegeId);
            }

            map.put("ERRNO", "0");
            map.put("user", u);

            rs.setCode(Result.SUCCESS);
        }
        else{
            map.put("ERRNO", "ERR01");
            map.put("ERRMAS", "更新用户失败");

            rs.setCode(Result.FAILURE);
        }

        rs.setData(map);

        return rs;
    }

    public Result updateStatus(String uCodes, String uState){
        Result rs = new Result();
        Map map = new HashMap();

        if (userDao.updateStatus(uState, ToolkitsBase.getQueryStcds("UCODE", uCodes)) > 0){
            map.put("ERRNO", "0");

            rs.setCode(Result.SUCCESS);
        }
        else{
            map.put("ERRNO", "ERR01");
            map.put("ERRMAS", "更新用户状态失败");

            rs.setCode(Result.FAILURE);
        }

        rs.setData(map);

        return rs;
    }

    public Result changePwd(String uCode, String oldPwd, String newPwd){
        Result rs = new Result();
        Map map = new HashMap();

        if(getUpwd(uCode, "").equals(Crypt.md5Encrypt(oldPwd))){
            User u = new User();

            u.setUserCode(uCode);
            u.setUserPwd(Crypt.md5Encrypt(newPwd));

            if (userDao.updateByPrimaryKeySelective(u) > 0){
                map.put("ERRNO", "0");
                map.put("user", u);

                rs.setCode(Result.SUCCESS);
            }
            else{
                map.put("ERRNO", "ERR01");
                map.put("ERRMAS", "修改用户密码失败");

                rs.setCode(Result.FAILURE);
            }
        }
        else{
            map.put("ERRNO", "ERR01");
            map.put("ERRMAS", "原密码错误");

            rs.setCode(Result.FAILURE);
        }

        rs.setData(map);

        return rs;
    }

    public String getUpwd(String uCode,String uRole){
        String result = "";
        User user = userDao.getUser(uCode);

        if (user != null){
            String tempUrole = user.getUserRole().toString();
            if (!((uRole != null && !uRole.equals("")) && !tempUrole.equals(uRole))){
                result = user.getUserPwd().toString();
            }
        }

        return result;
    }

    public Result dropAdmin(String uCodes){
        Result rs = new Result();
        Map map = new HashMap();

        if (userDao.deleteUsers(ToolkitsBase.getQueryStcds("UCODE", uCodes)) > 0){
            map.put("ERRNO", "0");

            rs.setCode(Result.SUCCESS);
        }
        else{
            map.put("ERRNO", "ERR01");
            map.put("ERRMAS", "删除管理员失败");

            rs.setCode(Result.FAILURE);
        }

        rs.setData(map);

        return rs;
    }
}
