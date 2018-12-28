package com.cn.api.enums;

public enum ResultCode {
    SUCCESS(0, "请求成功"),
    TIMEOUT(1, "session超时"),

    COMM_ARGVLIDATEFIALED(10000, "参数校验失败"),
    COMM_PARAMETER_ERROR(10001, "参数错误"),
    COMM_NO_PERMISSION(10002, "您没有足够的权限执行该操作"),
    COMM_WEAK_NET_WORK(10003, "系统异常，请稍后重试"),

    HUMAN_PASSWORD_NULL(20000, "用户名或密码不能为空"),
    HUMAN_PASSWORD_ERROR(20001, "用户名或密码错误"),
    HUMAN_LOCKED(20002, "用户已经被锁定不能登录，请与管理员联系！"),


    FILE_IS_NULL(30000, "文件为空"),
    FILE_UPLOAD_FAILED(30001, "文件上传失败"),
    FILE_DOWNLOAD_FAILED(30002, "文件下载失败"),
    FILE_NOT_EXIST(30003, "文件不存在"),
    FILE_TYPE_ERROR(30004, "文件类型错误"),
    FILE_DOWNLOAD_PATH_ERROR(30005, "文件下载路径错误"),
    FILE_PATH_NULL(30006, "下载路径为空"),
    FILE_DOWNLOAD_SUCCESS(30007, "文件下载成功"),
    FILE_DELETE_FAILED(30008, "文件删除失败"),
    FILE_DELETE_SUCCESS(30009, "文件删除成功"),
    FILE_DELETE_PATH_ERROR(30010, "文件删除路径错误"),
    FILE_OUT_SIZE(30011,"文件内存过大");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
