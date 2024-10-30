package com.service;

import com.domain.dto.ClickOperationDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public interface ClickOperationService {

    public void updateClickTable(ClickOperationDto clickOperationDto);
}
