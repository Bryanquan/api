package com.cn.api.Exception;

import com.cn.api.enums.ResultCode;

public class ApiException extends RuntimeException{
    private ResultCode resultCode;

    public ApiException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.resultCode = resultCode;
    }

    public ApiException(Throwable throwable, ResultCode resultCode) {
        super(resultCode.getMsg(), throwable);
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }
}
