package com.service.impl;

import com.domain.dto.AccountDetailDto;
import com.domain.dto.UserInfoDto;
import com.entity.UserInfoEntity;
import com.llh.enums.PhoneCode;
import com.llh.enums.ResultCode;
import com.llh.exception.BusinessException;
import com.llh.utils.DateUtil;
import com.llh.utils.StringUtil;
import com.repository.UserInfoRepository;
import com.domain.request.UserInfoRequest;
import com.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // 注入 PasswordEncoder

    @Override
    public boolean setupUser(UserInfoRequest userInfoRequest) {
        if (userInfoRequest == null) {
            return false;
        }

        if (userInfoRequest.getUsername() != null) {
            if (userInfoRepository.findFirstByUsername(userInfoRequest.getUsername()).isPresent()) {
                return false;
            }
            if (StringUtil.isValidPassword(userInfoRequest.getPassword())
                    && StringUtil.isValidEmail(userInfoRequest.getEmail())
                    && StringUtil.isValidNickname(userInfoRequest.getNickname())
                    && StringUtil.isValidPhoneNumber(userInfoRequest.getPhoneNumber(),
                    PhoneCode.getPrefixByPhoneCode(PhoneCode.CHINA))
                    && StringUtil.isValidUsername(userInfoRequest.getUsername())) {
                UserInfoEntity userInfo = new UserInfoEntity();
                userInfo.setUsername(userInfoRequest.getUsername());
                // 使用注入的 PasswordEncoder 进行密码编码
                String encodedPassword = passwordEncoder.encode(userInfoRequest.getPassword());
                userInfo.setPassword(encodedPassword);
                userInfo.setEmail(userInfoRequest.getEmail());
                userInfo.setCreateTime(LocalDateTime.now());
                userInfo.setNickname(userInfoRequest.getNickname());
                userInfo.setPhoneNumber(userInfoRequest.getPhoneNumber());
                // userInfo.setLastOperateTime(LocalDateTime.now());
                userInfoRepository.save(userInfo);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean changePassword(UserInfoRequest userInfoRequest) {
        if (userInfoRequest == null || !StringUtil.isValidUsername(userInfoRequest.getUsername())
                || !StringUtil.isValidPassword(userInfoRequest.getPassword())) {
            return false; // 无效的请求
        }

        // 查找用户
        Optional<UserInfoEntity> userEntityOpt = userInfoRepository.findFirstByUsername(userInfoRequest.getUsername());
        if (userEntityOpt.isPresent()) {
            UserInfoEntity userEntity = userEntityOpt.get();

            // 更新密码
            // 使用注入的 PasswordEncoder 进行密码编码
            String encodedPassword = passwordEncoder.encode(userInfoRequest.getPassword());
            userEntity.setPassword(encodedPassword);
            // userEntity.setLastOperateTime(LocalDateTime.now());
            // 保存修改后的用户信息
            userInfoRepository.save(userEntity);

            return true; // 密码修改成功
        }

        return false; // 用户不存在
    }

    @Override
    public boolean updateUserInfo(UserInfoRequest userInfoRequest) {
        if (userInfoRequest == null || !StringUtil.isValidUsername(userInfoRequest.getUsername())){
            return false;
        }
        Optional<UserInfoEntity> userEntityOpt = userInfoRepository.findFirstByUsername(userInfoRequest.getUsername());
        if (userEntityOpt.isPresent()) {
            UserInfoEntity userEntity = userEntityOpt.get();
            // userEntity.setLastOperateTime(LocalDateTime.now());
            if(userInfoRequest.getEmail() != null) {
                userEntity.setEmail(userInfoRequest.getEmail());
            }
            if(userInfoRequest.getNickname() != null) {
                userEntity.setNickname(userInfoRequest.getNickname());
            }
            if(userInfoRequest.getPassword() != null) {
                userEntity.setPassword(passwordEncoder.encode(userInfoRequest.getPassword()));
            }
            if(userInfoRequest.getPhoneNumber() != null) {
                if(StringUtil.isValidPhoneNumber(userInfoRequest.getPhoneNumber()
                        , PhoneCode.getPrefixByPhoneCode(PhoneCode.CHINA))) {
                    userEntity.setPhoneNumber(userInfoRequest.getPhoneNumber());
                } else {
                    throw new BusinessException(ResultCode.PHONE_NUMBER_ERROR);
                }
            }
            if(StringUtil.isValidNickname(userInfoRequest.getNickname())){
                userEntity.setNickname(userInfoRequest.getNickname());
            }
            userInfoRepository.save(userEntity);
            return true;
        }
        return false;
    }

    public AccountDetailDto getAccountDetail(String username) {
        if (username == null) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST, "User ID cannot be null");
        }

        UserInfoEntity userInfoEntity = userInfoRepository.findFirstByUsername(username).orElse(null);
        if (userInfoEntity == null) {
            return null;
        }

        AccountDetailDto accountDetailDto = new AccountDetailDto();
        accountDetailDto.setUsername(username);
        accountDetailDto.setEmail(userInfoEntity.getEmail());
        accountDetailDto.setNickname(userInfoEntity.getNickname());
        accountDetailDto.setCreateMonth(DateUtil.getMonth(userInfoEntity.getCreateTime()));
        accountDetailDto.setCreateDay(DateUtil.getDay(userInfoEntity.getCreateTime()));

        return accountDetailDto;
    }

    @Override
    public Long getIdByUsername(String username) {
        try {
            UserInfoEntity userInfoEntity = userInfoRepository.findFirstByUsername(username).orElse(null);
            Long id = userInfoEntity.getId();
            if(id != null) {
                return userInfoEntity.getId();
            } else {
                throw new BusinessException(ResultCode.USER_NOT_EXIST, "User ID cannot be null");
            }
        } catch (Exception e) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST);
        }
    }

    @Override
    public Long getIdByUsername(){
        try {
            String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            UserInfoEntity userInfoEntity = userInfoRepository.findFirstByUsername(loggedInUsername).orElse(null);
            return userInfoEntity.getId();
        } catch (Exception e) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST);
        }
    }

    @Override
    public UserInfoDto getUserInfoByUsername(String username){
        if (username == null) {
            throw new BusinessException(ResultCode.INVALID_PARAMETER, "User ID cannot be null");
        }
        UserInfoEntity userInfoEntity = userInfoRepository.findFirstByUsername(username).orElse(null);
        try{
            UserInfoDto userInfoDto = new UserInfoDto();
            userInfoDto.setUsername(userInfoEntity.getUsername());
            userInfoDto.setNickname(userInfoEntity.getNickname());
            userInfoDto.setEmail(userInfoEntity.getEmail());
            userInfoDto.setCreateTime(userInfoEntity.getCreateTime());
            userInfoDto.setIp(userInfoEntity.getIp());
            userInfoDto.setLastLogin(userInfoEntity.getLastLogin());
            userInfoDto.setPhoneNumber(userInfoEntity.getPhoneNumber());
            userInfoDto.setLastOperateTime(userInfoEntity.getLastOperateTime());
            return userInfoDto;
        } catch (Exception e) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST, e.getMessage());
        }
    }

}