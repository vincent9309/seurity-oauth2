package com.happylifeplat.security.unicom.model;

import java.io.Serializable;


public class AccessTokenDto implements Serializable{

    private static final long serialVersionUID = 5711739525478587476L;

    private String grant_type;

    private String client_id;

    private String timestamp;

    private String username;

    private String password;

    private String scope;

    private String sign;

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "AccessTokenDto{" +
                "grant_type='" + grant_type + '\'' +
                ", client_id='" + client_id + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", scope='" + scope + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
