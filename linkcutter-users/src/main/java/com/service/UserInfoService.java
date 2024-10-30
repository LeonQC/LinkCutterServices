package com.service;

import com.domain.dto.AccountDetailDto;
import com.domain.dto.UserInfoDto;
import com.domain.request.UserInfoRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserInfoService {
    public boolean setupUser(UserInfoRequest userInfoRequest);

    public boolean changePassword(UserInfoRequest userInfoRequest);

    public boolean updateUserInfo(UserInfoRequest userInfoRequest);

    public AccountDetailDto getAccountDetail(String username);

    public Long getIdByUsername(String username);

    public Long getIdByUsername();

    public UserInfoDto getUserInfoByUsername(String username);
}
