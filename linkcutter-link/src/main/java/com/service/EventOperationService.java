package com.service;

import com.domain.dto.ClickOperationDto;
import com.domain.dto.EventOperationDto;
import com.domain.dto.EventTypeGeneratorDto;
import com.domain.dto.LinkOperationDto;
import org.springframework.stereotype.Service;

@Service
public interface EventOperationService {

    public Boolean eventGenerator(EventTypeGeneratorDto eventTypeGeneratorDto) ;

    public EventOperationDto getEventCode(Long id);

    public ClickOperationDto getEventDetailByShortCode(String shortCode);
}
