package com.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public interface LinkRedirectService {

    public void linkRedirect(String shortCode, HttpServletResponse response);
}
