package com.happylifeplat.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.happylifeplat.security.common.EntityView;

import java.security.Principal;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/1 11:20</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
public class User implements EntityView<String>, Principal {

    private static final long serialVersionUID = -3066758188543025364L;

    @JsonIgnore
    private String id;

    private String name;

    @JsonIgnore
    private String password;

    private String phone;

    private String email;

    private String imageUrl;

    @JsonIgnore
    private Boolean isLockedOut;

    @JsonIgnore
    private Boolean isDisable;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getIsLockedOut() {
        return isLockedOut;
    }

    public void setIsLockedOut(Boolean isLockedOut) {
        this.isLockedOut = isLockedOut;
    }

    public Boolean getIsDisable() {
        return isDisable;
    }

    public void setIsDisable(Boolean isDisable) {
        this.isDisable = isDisable;
    }
}
