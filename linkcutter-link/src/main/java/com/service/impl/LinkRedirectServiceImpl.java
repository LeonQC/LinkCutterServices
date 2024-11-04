package com.service.impl;

import com.domain.dto.ClickOperationDto;
import com.domain.dto.LinkOperationDto;
import com.service.ClickOperationService;
import com.service.EventOperationService;
import com.service.LinkRedirectService;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LinkRedirectServiceImpl implements LinkRedirectService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private EventOperationService eventOperationService;

    @Autowired
    private ClickOperationService clickOperationService;

    public void linkRedirect(String shortCode, HttpServletResponse response) throws IOException {
        // 使用 Optional 来处理 Redis 返回的可能为空的对象

        Optional.ofNullable(redisTemplate.opsForValue().get(shortCode))
                .filter(LinkOperationDto.class::isInstance) // 检查对象是否为 LinkOperationDto 类型
                .map(LinkOperationDto.class::cast) // 将对象转换为 LinkOperationDto
                .map(LinkOperationDto::getOriginalUrl) // 获取 originalUrl
                .ifPresentOrElse(
                        originalUrl -> {
                            try {
                                response.sendRedirect(originalUrl); // 如果 originalUrl 存在，执行重定向
                                ClickOperationDto clickOperationDto =  eventOperationService.getEventDetailByShortCode(shortCode);
                                clickOperationService.updateClickTable(clickOperationDto);
                            } catch (IOException | java.io.IOException e) {
                                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 捕获重定向错误
                            }
                        },
                        () -> response.setStatus(HttpServletResponse.SC_NOT_FOUND) // 如果 originalUrl 不存在，返回 404
                );
    }
}
