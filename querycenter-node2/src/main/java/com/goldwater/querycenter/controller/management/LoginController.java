package com.goldwater.querycenter.controller.management;

import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.service.management.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/userLogin")
    @ResponseBody
    public Result userLogin(@RequestParam(name = "UROLE", defaultValue = "") String uRole,
                            @RequestParam(name = "UCODE", defaultValue = "") String uCode,
                            @RequestParam(name = "UPWD", defaultValue = "") String uPwd){
        return loginService.userLogin(uRole, uCode, uPwd);
    }

    @PostMapping("/cuLoginOut")
    @ResponseBody
    public Result cuLoginOut(@RequestParam(name = "UCODE", defaultValue = "") String uCode){
        return loginService.cuLoginOut(uCode);
    }
}
