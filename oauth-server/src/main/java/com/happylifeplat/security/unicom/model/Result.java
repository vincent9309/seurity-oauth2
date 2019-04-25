package com.happylifeplat.security.unicom.model;


import com.happylifeplat.security.unicom.enums.UnicomApiCode;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.io.Serializable;
import java.util.Date;

public class Result implements Serializable{

    private static final long serialVersionUID = -4350776766324383045L;

    private String success;

    private String result_message;

    private String access_token;

    private String refresh_token;

    private String time;

    public Result() {
    }

    public Result(String success, String result_message) {
        this.success = success;
        this.result_message = result_message;
    }

    public Result(String success, String result_message, String access_token, String refresh_token, String time) {
        this.success = success;
        this.result_message = result_message;
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.time = time;
    }

    public static Result error(UnicomApiCode unicomApiCode){
        return new Result(unicomApiCode.getSuccess(), unicomApiCode.getResult_message());
    }

    public static Result success(UnicomApiCode unicomApiCode, OAuth2AccessToken token){
        return new Result(unicomApiCode.getSuccess(), unicomApiCode.getResult_message(), token.getValue(),
                token.getRefreshToken().getValue(), String.valueOf(System.currentTimeMillis() / 1000));
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getResult_message() {
        return result_message;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
