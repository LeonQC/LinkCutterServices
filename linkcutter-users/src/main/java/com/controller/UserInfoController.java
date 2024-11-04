package com.controller;

import com.domain.dto.AccountDetailDto;
import com.llh.enums.ResultCode;
import com.llh.utils.Result;
import com.llh.utils.ResultUtil;
import com.domain.request.UserInfoRequest;
import com.service.UserInfoService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @PermitAll
    @PostMapping("/register")
    public Result<Boolean> userInfoSetupController(@RequestBody UserInfoRequest userInfoRequest) {
        boolean setupSuccess = userInfoService.setupUser(userInfoRequest);

        if (!setupSuccess) {
            return ResultUtil.wrapFailure(ResultCode.USER_SETUP_ERROR);
        }

        return ResultUtil.wrapSuccess(true, "注册成功！");
    }

    @PostMapping("/changePassword")
    public Result<Boolean> changePasswordController(@RequestBody UserInfoRequest userInfoRequest) {
        boolean changePassword = userInfoService.changePassword(userInfoRequest);

        if (!changePassword) {
            return ResultUtil.wrapFailure(ResultCode.CHANGE_PASSWORD_ERROR);
        }

        return ResultUtil.wrapSuccess(true);
    }

    @PutMapping("/update")
    public Result<Boolean> updateUser(@RequestBody UserInfoRequest userInfoRequest) {
        // 获取当前登录用户的用户名
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            // 检查用户是否试图修改自己的信息
            if (!loggedInUsername.equals(userInfoRequest.getUsername())) {
                return ResultUtil.wrapFailure(ResultCode.INSUFFICIENT_PERMISSIONS, false);
            }
            // 调用 service 层进行信息更新
            userInfoService.updateUserInfo(userInfoRequest);
            return ResultUtil.wrapSuccess(true, "修改成功！");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/accountDetail")
    public Result<AccountDetailDto> getAccountDetail() {
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResultUtil.wrapSuccess(userInfoService.getAccountDetail(loggedInUsername));
    }
}
