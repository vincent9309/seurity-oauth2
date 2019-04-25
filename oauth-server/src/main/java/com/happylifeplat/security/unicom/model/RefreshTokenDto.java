package com.happylifeplat.security.unicom.model;

import java.io.Serializable;

/**
 * Created by vincent on 2018/2/6.
 */
public class RefreshTokenDto implements Serializable{

    private static final long serialVersionUID = 6179434149485744290L;

    private String refresh_token;

    private String  client_id;

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    @Override
    public String toString() {
        return "RefreshTokenDto{" +
                "refresh_token='" + refresh_token + '\'' +
                ", client_id='" + client_id + '\'' +
                '}';
    }
}
