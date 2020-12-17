package com.goldwater.querycenter.service.management;

import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.common.security.Crypt;
import com.goldwater.querycenter.common.util.cache.SessionCache;
import com.goldwater.querycenter.entity.management.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {
    @Autowired
    private UserService userService;

    public Result userLogin(String uRole, String uCode, String uPwd){
        Result rs = new Result();
        Map map = new HashMap();
        String pwd = Crypt.md5Encrypt(uPwd);
        String resultUpwd = userService.getUpwd(uCode, uRole);

        if(!resultUpwd.equals("")) {
            if(resultUpwd.equals(pwd)) {
                Result r = userService.getUser(uCode);
                User user = (User) r.getData();

                if(Integer.parseInt(user.getUserState()) == 1){
                    SessionCache.put(user);

                    map.put("ERRNO", "0");
                    map.put("ERRMAS", "");
                }
                else{
                    map.put("ERRNO", "ERR03");
                    map.put("ERRMAS", "用户已停用！");
                }
            }
            else {
                map.put("ERRNO", "ERR01");
                map.put("ERRMAS", "密码错误！");
            }
        }
        else{
            map.put("ERRNO", "ERR02");
            map.put("ERRMAS", "用户不存在！");
        }

        rs.setData(map);
        rs.setCode(Result.SUCCESS);

        return rs;
    }

    public Result cuLoginOut(String uCode){
        Result rs = new Result();
        Map map = new HashMap();

        try {
            SessionCache.remove();

            map.put("STATUS", true);

            rs.setData(map);
            rs.setCode(Result.SUCCESS);
        }
        catch (Exception ex)
        {
            map.put("STATUS", false);

            rs.setCode(Result.FAILURE);
            rs.setMsg("注销失败！");
        }

        return rs;
    }
}
