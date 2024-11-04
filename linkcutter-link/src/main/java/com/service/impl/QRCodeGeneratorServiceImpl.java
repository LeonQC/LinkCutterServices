package com.service.impl;

import com.constant.FileFormatConstant;
import com.constant.QRCodeConstant;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.llh.enums.ResultCode;
import com.llh.exception.BusinessException;
import com.service.QRCodeGeneratorService;
import io.jsonwebtoken.io.IOException;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;

import java.util.HashMap;
import java.util.Map;

@Service
public class QRCodeGeneratorServiceImpl implements QRCodeGeneratorService {

    @Override
    public String QRCodeGenerator(String sourceLink, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        BitMatrix bitMatrix = qrCodeWriter.encode(sourceLink, BarcodeFormat.QR_CODE, width, height, hints);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? QRCodeConstant.BLACK_COLOR : QRCodeConstant.WHITE_COLOR);
            }
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, FileFormatConstant.PNG, baos);
        } catch (java.io.IOException e) {
            throw new BusinessException(ResultCode.FILE_WRITE_ERROR, e.getMessage());
        }
        byte[] qrBytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(qrBytes);
    }

    public static void saveQRCodeImage(String base64QRCode, String filePath) throws IOException {
        byte[] qrBytes = Base64.getDecoder().decode(base64QRCode);
        ByteArrayInputStream bis = new ByteArrayInputStream(qrBytes);
        BufferedImage qrImage = null;
        try {
            qrImage = ImageIO.read(bis);
        } catch (java.io.IOException e) {
            throw new BusinessException(ResultCode.FILE_READ_ERROR, e.getMessage());
        }
        //保存BufferedImage为PNG文件
        try {
            ImageIO.write(qrImage, FileFormatConstant.PNG, new File(filePath)); // filePath应该是带文件名的完整路径
        } catch (java.io.IOException e) {
            throw new BusinessException(ResultCode.FILE_WRITE_ERROR, e.getMessage());
        }
    }
}
