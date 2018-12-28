package com.cn.api.Exception;


import com.cn.api.enums.ResultCode;

public class FdfsException extends ApiException{

    public FdfsException(ResultCode resultCode) {
        super(resultCode);
    }

    public FdfsException(Throwable throwable, ResultCode resultCode) {
        super(throwable, resultCode);
    }
}
