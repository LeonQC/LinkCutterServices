package com.service.impl;

import com.entity.UserInfoEntity;
import com.llh.utils.StringUtil;
import com.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 检查用户名是否为空
        if(StringUtil.isBlank(username)){
            throw new UsernameNotFoundException("Username cannot be blank");
        }

        // 从数据库查找用户
        UserInfoEntity user = userInfoRepository.findFirstByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        // 构建用户角色（这里你可以从数据库加载角色）
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        // 返回 UserDetails 对象给 Spring Security
        return new User(
                user.getUsername(),
                user.getPassword(),
                authorities);
    }
}