package com.service;

import com.domain.dto.LinkPaginationDto;
import com.domain.request.BasePageQueryRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public interface EventQueryService {
    public Page<LinkPaginationDto> eventQuery(@RequestBody BasePageQueryRequest basePageQueryRequest);
}
