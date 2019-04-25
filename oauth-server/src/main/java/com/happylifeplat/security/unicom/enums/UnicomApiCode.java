package com.happylifeplat.security.unicom.enums;


public enum UnicomApiCode {

    PARAM_ERROR("false", "参数错误"),
    SUCCESS("true", "成功");

    private final String success;
    private final String result_message;

    UnicomApiCode(String success, String result_message) {
        this.success = success;
        this.result_message = result_message;
    }

    public String getSuccess() {
        return success;
    }

    public String getResult_message() {
        return result_message;
    }
}
