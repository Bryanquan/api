package com.cn.api.util;


import com.cn.api.enums.ResultCode;
import dto.ResultDto;

public class ResultUtils {
    public static ResultDto success(Object data) {
        return new ResultDto(ResultCode.SUCCESS, data);
    }

    public static ResultDto error(ResultCode resultCode, String msg) {
        ResultDto result = new ResultDto(resultCode);
        result.setMessage(msg);
        return result;
    }

    public static ResultDto error(ResultCode resultCode) {
        return new ResultDto(resultCode);
    }

}
