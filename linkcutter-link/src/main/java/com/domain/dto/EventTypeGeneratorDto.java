package com.domain.dto;

import com.constant.OptionalFlagConstant;
import com.enums.EventTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventTypeGeneratorDto implements Serializable {

    private String destinationLink;

    private String title;

    private String custom;

    private Integer eventType;

    private OptionalFlagDto optionalFlag;

    public EventTypeGeneratorDto(String destinationLink, String title, String custom, Integer eventType) {
        this.destinationLink = destinationLink;
        this.title = title;
        this.custom = custom;
        this.eventType = eventType;
//        if(EventTypeEnum.getByCode(eventType).equals(EventTypeEnum.LINK)){
//            optionalFlag.setQrCodeFlag(OptionalFlagConstant.UNABLE);
//            optionalFlag.setPageFlag(OptionalFlagConstant.UNABLE);
//        }
//        else
        if(EventTypeEnum.getByCode(eventType).equals(EventTypeEnum.QR_CODE)) {
            optionalFlag.setPageFlag(OptionalFlagConstant.UNABLE);
        } else if(EventTypeEnum.getByCode(eventType).equals(EventTypeEnum.PAGE)){
            optionalFlag.setQrCodeFlag(OptionalFlagConstant.UNABLE);
            optionalFlag.setTitleFlag(OptionalFlagConstant.UNABLE);
            optionalFlag.setCustomFlag(OptionalFlagConstant.UNABLE);
        }
    }

}
