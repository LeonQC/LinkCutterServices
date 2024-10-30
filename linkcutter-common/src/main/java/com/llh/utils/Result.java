package com.llh.utils;

import com.llh.enums.ErrorCode;
import com.llh.enums.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result<T> {

    private Integer code;

    private String msg;

    private T data;

    public static Result success() {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMessage());
        return result;
    }

    public static Result success(Object data) {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    public static Result success(Object data, String msg) {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public Result(ErrorCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMessage();
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.msg = message;
    }

    public Result(Integer code, String message, String messageShow, T data) {
        this.code = code;
        this.msg = message;
        this.data = data;
    }


    public boolean isSuccess(){
        return ResultCode.SUCCESS.getCode().equals(this.code);
    }
}
