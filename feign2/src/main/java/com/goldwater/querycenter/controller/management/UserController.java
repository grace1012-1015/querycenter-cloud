package com.goldwater.querycenter.controller.management;


import com.goldwater.querycenter.common.util.Result;
import com.goldwater.querycenter.service.management.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/getUserList")
    @ResponseBody
    public Result getUserList(@RequestParam(name = "UNAME", defaultValue = "") String uName,
                              @RequestParam(name = "UCODE", defaultValue = "") String uCode,
                              @RequestParam(name = "USTATE", defaultValue = "") String uState,
                              @RequestParam(name = "UROLE", defaultValue = "") String uRole,
                              @RequestParam(name = "page", defaultValue = "1") int pageIndex,
                              @RequestParam(name = "limit", defaultValue = "10") int length){
        return userService.getUserList(uName, uCode, uState, uRole, pageIndex, length);
    }

    @PostMapping("/getUser")
    @ResponseBody
    public Result getUser(@RequestParam(name = "UCODE", defaultValue = "") String uCode){
        return userService.getUser(uCode);
    }

    @PostMapping("/checkUcode")
    @ResponseBody
    public Result checkCode(@RequestParam(name = "UCODE", defaultValue = "") String uCode){
        return userService.checkUcode(uCode);
    }

    @PostMapping("/addUser")
    @ResponseBody
    public Result addUser(@RequestParam(name = "UCODE", defaultValue = "") String uCode,
                          @RequestParam(name = "UPWD", defaultValue = "") String uPwd,
                          @RequestParam(name = "UCOMPANY", defaultValue = "") String uCompany,
                          @RequestParam(name = "UCBRIEF", defaultValue = "") String uCbrief,
                          @RequestParam(name = "UCTEL", defaultValue = "") String uCtel,
                          @RequestParam(name = "UNAME", defaultValue = "") String uName,
                          @RequestParam(name = "UEMAIL", defaultValue = "") String uEmail,
                          @RequestParam(name = "UPHONE", defaultValue = "") String uPhone,
                          @RequestParam(name = "UKEY", defaultValue = "") String uKey,
                          @RequestParam(name = "USTATE", defaultValue = "") String uState,
                          @RequestParam(name = "UROLE", defaultValue = "") String uRole){
        return userService.addUser(uCode, uPwd, uCompany, uCbrief, uCtel, uName, uEmail, uPhone, uKey, uState, uRole);
    }

    @PostMapping("/updateUser")
    @ResponseBody
    public Result updateUser(@RequestParam(name = "UCODE", defaultValue = "") String uCode,
                             @RequestParam(name = "UPWD", defaultValue = "") String uPwd,
                             @RequestParam(name = "UCOMPANY", defaultValue = "") String uCompany,
                             @RequestParam(name = "UCBRIEF", defaultValue = "") String uCbrief,
                             @RequestParam(name = "UCTEL", defaultValue = "") String uCtel,
                             @RequestParam(name = "UNAME", defaultValue = "") String uName,
                             @RequestParam(name = "UEMAIL", defaultValue = "") String uEmail,
                             @RequestParam(name = "UPHONE", defaultValue = "") String uPhone,
                             @RequestParam(name = "UKEY", defaultValue = "") String uKey,
                             @RequestParam(name = "USTATE", defaultValue = "") String uState,
                             @RequestParam(name = "UROLE", defaultValue = "") String uRole,
                             @RequestParam(name = "PRIVILEGE_ID", defaultValue = "") String privilegeId){
        return userService.updateUser(uCode, uPwd, uCompany, uCbrief, uCtel, uName, uEmail, uPhone, uKey, uState, uRole, privilegeId);
    }

    @PostMapping("/updateStatus")
    @ResponseBody
    public Result updateStatus(@RequestParam(name = "UCODES", defaultValue = "") String uCodes,
                               @RequestParam(name = "USTATE", defaultValue = "") String uState){
        return userService.updateStatus(uCodes, uState);
    }

    @PostMapping("/changePwd")
    @ResponseBody
    public Result changePwd(@RequestParam(name = "UCODE", defaultValue = "") String uCode,
                            @RequestParam(name = "OUPWD", defaultValue = "") String oldPwd,
                            @RequestParam(name = "NUPWD", defaultValue = "") String newPwd){
        return userService.changePwd(uCode, oldPwd, newPwd);
    }

    @PostMapping("/dropAdmin")
    @ResponseBody
    public Result dropAdmin(@RequestParam(name = "UCODES", defaultValue = "") String uCodes){
        return userService.dropAdmin(uCodes);
    }
}
