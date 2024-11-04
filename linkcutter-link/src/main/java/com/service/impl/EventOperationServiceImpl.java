package com.service.impl;

import com.constant.*;
import com.domain.dto.*;
import com.entity.UrlTableEntity;
import com.enums.EventTypeEnum;
import com.llh.enums.ResultCode;
import com.llh.exception.BusinessException;
import com.llh.utils.StringUtil;
import com.repository.EventTypeRepository;
import com.service.EventOperationService;
import com.service.QRCodeGeneratorService;
import com.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;


@Service
public class EventOperationServiceImpl implements EventOperationService {
    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    @Qualifier("QRCodeGeneratorServiceImpl")
    private QRCodeGeneratorService qrCodeGeneratorService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public ClickOperationDto getEventDetailByShortCode(String shortCode){
        if(shortCode == null || StringUtil.isBlank(shortCode)){
            return null;
        }
        Optional<UrlTableEntity> optionalUrlTableEntity = eventTypeRepository.findByShortCode(shortCode);
        ClickOperationDto operationDto = new ClickOperationDto();
        if(optionalUrlTableEntity.isPresent()){
            try{
                UrlTableEntity urlTableEntity = optionalUrlTableEntity.get();

                operationDto.setOperatorId(urlTableEntity.getUserId());
                operationDto.setOperatorName(urlTableEntity.getUsername());
                operationDto.setEventType(urlTableEntity.getEventType());
                operationDto.setEventId(urlTableEntity.getId());

            } catch (Exception e){
                throw new BusinessException(ResultCode.INVALID_PARAMETER, e.getMessage());
            }
        }
        return operationDto;
    }

    public EventOperationDto getEventCode(Long id){
        EventOperationDto eventOperationDto = new EventOperationDto();
        try {
            Optional<UrlTableEntity> optionalUrlTableEntity = eventTypeRepository.findById(id);

            if (optionalUrlTableEntity.isPresent()) {
                UrlTableEntity urlTableEntity = optionalUrlTableEntity.get();
                eventOperationDto.setOriginalUrl(urlTableEntity.getOriginalUrl());
                eventOperationDto.setCreateTime(urlTableEntity.getCreateTime());
                eventOperationDto.setQrCodeId(urlTableEntity.getQrCodeId());
                eventOperationDto.setTitle(urlTableEntity.getTitle());
                eventOperationDto.setShortCode(urlTableEntity.getShortCode());
                return eventOperationDto;
            } else {
                throw new BusinessException(ResultCode.RESOURCE_NOT_FOUND, "Data not found for id: " + id);
            }
        } catch (BusinessException e) {
            throw new BusinessException(ResultCode.INVALID_PARAMETER, e.getMessage());
        }
    }

    public Boolean eventGenerator(EventTypeGeneratorDto eventTypeGeneratorDto) {
        if (eventTypeGeneratorDto == null) {
            return false;
        }
        String sourceLink = eventTypeGeneratorDto.getDestinationLink();
        if (!StringUtil.isValidURL(sourceLink)) {
            throw new BusinessException(ResultCode.URL_FORMAT_ERROR);
        }
        try {
            String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getName();
            OptionalFlagDto optionalFlagDto = eventTypeGeneratorDto.getOptionalFlag();
            Integer eventType = eventTypeGeneratorDto.getEventType();
            UrlTableEntity urlTableEntity = new UrlTableEntity();
            urlTableEntity.setEventType(eventType);
            urlTableEntity.setUsername(loggedInUsername);

            urlTableEntity.setUserId(Long.valueOf(userInfoService.getIdByUsername(loggedInUsername)));

            urlTableEntity.setCreateTime(LocalDateTime.now());
            urlTableEntity.setExpireTime(LocalDateTime.now().plusDays(1));
            urlTableEntity.setOriginalUrl(sourceLink);

            LinkOperationDto linkOperationDto = new LinkOperationDto();

            if (optionalFlagDto != null) {  //检查 optionalFlagDto 是否为空
                if (optionalFlagDto.getTitleFlag().equals(OptionalFlagConstant.ENABLE)) {
                    urlTableEntity.setTitle(eventTypeGeneratorDto.getTitle());
                }
                if (optionalFlagDto.getCustomFlag().equals(OptionalFlagConstant.ENABLE)) {
                    urlTableEntity.setBackHalfCode(eventTypeGeneratorDto.getCustom());
                }
                if (optionalFlagDto.getQrCodeFlag().equals(OptionalFlagConstant.ENABLE)) {
                    String qrCode = qrCodeGeneratorService.QRCodeGenerator(sourceLink, QRCodeConstant.WIDTH, QRCodeConstant.HEIGHT);
                    //生成随机qrcode的id
                    String qrCodeId = NanoIdUtils.randomNanoId(DefaultConstant.DEFAULT_NUMBER_GENERATOR,
                            DefaultConstant.DEFAULT_ALPHABET, QRCodeConstant.QR_CODE_ID_LENGTH);
                    //qrcode存入的路径
                    String qrCodeFilePath = LinkConstant.FILE_PATH +
                            LinkConstant.QR_CODE_HEADER + qrCodeId + "." +
                            FileFormatConstant.PNG;
                    urlTableEntity.setQrCodeId(qrCodeId);

                    linkOperationDto.setQrCodeId(qrCodeId);
                    QRCodeGeneratorServiceImpl.saveQRCodeImage(qrCode, qrCodeFilePath);
                }
            }

            //如果是生成link以及qrcode的时候，才会用到short url
            if (!EventTypeEnum.getByCode(eventType).equals(EventTypeEnum.PAGE)) {
                String shortCode = linkConverter(sourceLink);
                urlTableEntity.setShortCode(shortCode);
                urlTableEntity.setShortenedUrl(LinkConstant.HTTP_HEADER + LinkConstant.SHORT_CODE_HEADER + "/" + shortCode);
                //封装redis用的dto
                linkOperationDto.setOperateTime(LocalDateTime.now());
                linkOperationDto.setOriginalUrl(sourceLink);
                linkOperationDto.setOperatorName(loggedInUsername);
                linkOperationDto.setUserId(userInfoService.getIdByUsername(loggedInUsername));
                saveShortenedUrl(shortCode, linkOperationDto);
            }

            eventTypeRepository.save(urlTableEntity);
            return true;

        } catch (Exception e) {
            throw new BusinessException(ResultCode.INVALID_PARAMETER, e);  // 将异常传递出去
        }
    }

    private String linkConverter(String sourceLink){
        long timestamp = System.currentTimeMillis();
        return hashUrl(sourceLink + timestamp);
    }

    private String hashUrl(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            String encodedHash = Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
            return encodedHash.substring(0, LinkConstant.SHORTENED_CODE_LENGTH);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating hash for short link", e);
        }
    }

    private void saveShortenedUrl(String shortCode, LinkOperationDto linkOperationDto) {
        redisTemplate.opsForValue().set(shortCode, linkOperationDto);
    }

    private LinkOperationDto getShortenedUrlInfo(String shortCode) {
        try{
            return (LinkOperationDto) redisTemplate.opsForValue().get(shortCode);
        } catch (Exception e) {
            throw new BusinessException(ResultCode.INVALID_PARAMETER, e);
        }
    }
}
