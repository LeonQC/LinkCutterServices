package com.llh.exception;


import com.llh.enums.ErrorCode;
import lombok.Data;

@Data
public class BusinessException extends RuntimeException {
    private ErrorCode resultCode;
    private String messageShow;

    public BusinessException() {
        super();
    }

    public BusinessException(ErrorCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
        this.messageShow = resultCode.getMessage();
    }

    public BusinessException(ErrorCode resultCode, Throwable cause) {
        super(resultCode.getMessage(),cause);
        this.resultCode = resultCode;
        this.messageShow = resultCode.getMessage();
    }

    public BusinessException(ErrorCode resultCode, String errorMessage) {
        super(errorMessage);
        this.resultCode = resultCode;
        this.messageShow = errorMessage;
    }

    public BusinessException(ErrorCode resultCode, String errorMessage, String messageShow) {
        super(errorMessage);
        this.resultCode = resultCode;
        this.messageShow = messageShow;
    }
}

