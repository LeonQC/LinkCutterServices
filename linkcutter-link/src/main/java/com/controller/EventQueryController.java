package com.controller;

import com.domain.dto.LinkPaginationDto;
import com.domain.request.BasePageQueryRequest;
import com.llh.utils.Result;
import com.llh.utils.ResultUtil;
import com.service.EventQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/link")
public class EventQueryController {

    @Autowired
    private EventQueryService eventQueryService;

    @GetMapping("/eventQuery")
    public Result<Page<LinkPaginationDto>> eventQuery(@RequestBody BasePageQueryRequest basePageQueryRequest) {
        return ResultUtil.wrapSuccess(eventQueryService.eventQuery(basePageQueryRequest));
    }

}
