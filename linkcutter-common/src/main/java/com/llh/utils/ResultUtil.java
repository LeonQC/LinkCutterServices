package com.llh.utils;

import com.llh.enums.ErrorCode;

public class ResultUtil {
    public static <T>Result<T> wrapSuccess(T data) {
        return Result.success(data);
    }

    public static <T>Result<T> wrapSuccess() {
        return Result.success();
    }

    public static <T> Result<T> wrapFailure(ErrorCode resultCode) {
        return new Result<>(resultCode);
    }

    public static <T> Result<T> wrapFailure(Integer resultCode, String message) {
        return new Result<>(resultCode, message);
    }

    public static <T> Result<T> wrapFailure(ErrorCode resultCode, T data) {
        Result<T> result = new Result<>(resultCode);
        result.setData(data);
        return result;
    }

    public static <T>Result<T> wrapSuccess(T data, String msg) {
        return Result.success(data, msg);
    }
}
