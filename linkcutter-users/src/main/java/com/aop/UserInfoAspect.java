package com.aop;

import com.entity.UserInfoEntity;
import com.llh.utils.IpUtil;
import com.repository.UserInfoRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;


@Aspect
@Slf4j
@Component
public class UserInfoAspect {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoAspect.class);

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private HttpServletRequest request;

    @Pointcut("execution(* com.service.impl.*Impl.*(..)) && !execution(* org.springframework.security.core.userdetails.UserDetailsService.*(..))")
    public void servicePointcut() {}

    @Around("servicePointcut()")
    public Object logServiceExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserInfoEntity> userEntityOpt = userInfoRepository.findFirstByUsername(loggedInUsername);
        logger.info("User Info for [{}]: ", loggedInUsername);
        if(userEntityOpt.isPresent()) {
            UserInfoEntity userInfoEntity = userEntityOpt.get();
            String userIp = IpUtil.getClientIp(request);
            userInfoEntity.setLastOperateTime(LocalDateTime.now());
            userInfoEntity.setIp(userIp);
            userInfoRepository.save(userInfoEntity);
            logger.info("Username: {}, Nickname: {}, Email: {}, Last Operation Time: {}, Last IP Address: {}",
                    userInfoEntity.getUsername(),
                    userInfoEntity.getNickname(),
                    userInfoEntity.getEmail(),
                    userInfoEntity.getLastOperateTime(),
                    userInfoEntity.getIp()
            );
        }
        return joinPoint.proceed();
    }

}
