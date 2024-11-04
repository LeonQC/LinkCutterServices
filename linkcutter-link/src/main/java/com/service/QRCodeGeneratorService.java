package com.service;

import com.google.zxing.WriterException;
import io.jsonwebtoken.io.IOException;
import org.springframework.stereotype.Service;

@Service
public interface QRCodeGeneratorService {

    public String QRCodeGenerator(String sourceLink, int width, int height) throws WriterException, IOException;

}
