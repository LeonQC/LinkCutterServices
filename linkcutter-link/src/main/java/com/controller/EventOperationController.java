package com.controller;

import com.domain.dto.EventOperationDto;
import com.domain.dto.EventTypeGeneratorDto;
import com.domain.dto.LinkOperationDto;
import com.llh.enums.ResultCode;
import com.llh.exception.BusinessException;
import com.llh.utils.Result;
import com.llh.utils.ResultUtil;
import com.service.EventOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/link")
public class EventOperationController {

    @Autowired
    private EventOperationService eventOperationService;

    @PostMapping("/eventGenerator")
    public Result<Boolean> eventGenerator(@RequestBody EventTypeGeneratorDto eventTypeGeneratorDto) {
        return ResultUtil.wrapSuccess(eventOperationService.eventGenerator(eventTypeGeneratorDto), "生成链接成功！");
    }

    @GetMapping("/getEventCode")
    public Result<EventOperationDto> getEventCode(@RequestParam("id") Long id) {
        return ResultUtil.wrapSuccess(eventOperationService.getEventCode(id),"返回链接成功");
    }


}
