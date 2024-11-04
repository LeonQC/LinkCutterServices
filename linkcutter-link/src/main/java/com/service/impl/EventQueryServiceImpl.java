package com.service.impl;

import com.domain.dto.LinkPaginationDto;
import com.domain.request.BasePageQueryRequest;
import com.entity.UrlTableEntity;
import com.llh.enums.ResultCode;
import com.llh.exception.BusinessException;
import com.repository.EventTypeRepository;
import com.service.EventQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class EventQueryServiceImpl implements EventQueryService {

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Override
    public Page<LinkPaginationDto> eventQuery(@RequestBody BasePageQueryRequest basePageQueryRequest){
        if(basePageQueryRequest == null){
            return null;
        }
        String username = basePageQueryRequest.getUsername();
        int pageSize = basePageQueryRequest.getPageSize();
        int pageNo = basePageQueryRequest.getPageNo();
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<UrlTableEntity> urlTableEntityPage = eventTypeRepository
                .findAllByUsernameOrderByCreateTimeDesc(username, pageable);
        try{
            return convertToDtoPage(urlTableEntityPage);
        } catch (Exception e) {
            throw new BusinessException(ResultCode.DATA_TRANSFER_ERROR,e.getMessage());
        }
    }

    private Page<LinkPaginationDto> convertToDtoPage(Page<UrlTableEntity> urlTableEntities) {
        return urlTableEntities.map(entity -> new LinkPaginationDto(
                entity.getShortCode(),
                entity.getOriginalUrl(),
                entity.getCreateTime(),
                entity.getTitle(),
                entity.getUsername(),
                entity.getQrCodeId()
        ));
    }

}
