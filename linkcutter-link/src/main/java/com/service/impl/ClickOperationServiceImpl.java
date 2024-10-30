package com.service.impl;

import com.domain.dto.ClickOperationDto;
import com.entity.ClickTableEntity;
import com.entity.UrlTableEntity;
import com.llh.enums.ResultCode;
import com.llh.exception.BusinessException;
import com.repository.ClickTableRepository;
import com.service.ClickOperationService;
import com.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ClickOperationServiceImpl implements ClickOperationService {

    @Autowired
    private ClickTableRepository clickTableRepository;

    @Autowired
    private UserInfoService userInfoService;

    public void updateClickTable(ClickOperationDto clickOperationDto){
        if(clickOperationDto == null){
            throw new BusinessException(ResultCode.INVALID_PARAMETER, "ClickOperationDto为空");
        }
        Long eventId = clickOperationDto.getEventId();
        if(eventId == null){
            throw new BusinessException(ResultCode.INVALID_PARAMETER, "EventId为空");
        }
        Optional<ClickTableEntity> optionalClickTableEntity = clickTableRepository.findByEventId(eventId);

        try {
            String operatorName = clickOperationDto.getOperatorName();
            String IpAddress = userInfoService.getUserInfoByUsername(operatorName).getIp();
            //如果有对应的eventId就更新
            if (optionalClickTableEntity.isPresent()) {
                ClickTableEntity clickTableEntity = optionalClickTableEntity.get();
                clickTableEntity.setIpAddress(IpAddress);
                clickTableEntity.setClickCount(clickTableEntity.getClickCount() + 1);
                clickTableEntity.setClickTime(LocalDateTime.now());
                clickTableRepository.save(clickTableEntity);
            } else { //没有的话就新建
                ClickTableEntity clickTableEntity = new ClickTableEntity();
                clickTableEntity.setOperatorId(clickOperationDto.getOperatorId());
                clickTableEntity.setOperatorName(operatorName);
                clickTableEntity.setIpAddress(IpAddress);
                clickTableEntity.setEventType(clickOperationDto.getEventType());
                clickTableEntity.setEventId(clickOperationDto.getEventId());

                clickTableEntity.setClickCount(clickTableEntity.getClickCount() + 1);
                clickTableEntity.setClickTime(LocalDateTime.now());
                clickTableRepository.save(clickTableEntity);
            }
        }
        catch (Exception e) {
            throw new BusinessException(ResultCode.INVALID_PARAMETER, e.getMessage());
        }
    }

}
