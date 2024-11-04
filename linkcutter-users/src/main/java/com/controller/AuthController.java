package com.controller;

import com.entity.UserInfoEntity;
import com.llh.enums.ResultCode;
import com.llh.request.AuthRequest;
import com.llh.response.AuthResponse;
import com.llh.utils.IpUtil;
import com.llh.utils.JwtUtil;
import com.llh.utils.Result;
import com.llh.utils.ResultUtil;
import com.repository.UserInfoRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
public class AuthController {

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private HttpServletRequest request;

    @PostMapping("/login")
    public Result<ResponseEntity<?>> login(@RequestBody AuthRequest authRequest) {
        try {
            // 使用 Spring Security 的 AuthenticationManager 验证用户名和密码
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            // 更新认证上下文
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 验证认证上下文中的用户名
            String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

            final String token = jwtUtil.generateToken(currentUser);
            Optional<UserInfoEntity> userOpt = userInfoRepository.findFirstByUsername(authRequest.getUsername());
            if (userOpt.isPresent()) {
                UserInfoEntity user = userOpt.get();
                user.setIp(IpUtil.getClientIp(request));
                user.setLastLogin(LocalDateTime.now());
                userInfoRepository.save(user);
            }
            // 返回生成的 JWT
            return ResultUtil.wrapSuccess(ResponseEntity.ok(new AuthResponse(token)));
        } catch (BadCredentialsException e) {
            return ResultUtil.wrapFailure(ResultCode.USER_NOT_EXIST, ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户名或密码错误"));
        }
    }

}