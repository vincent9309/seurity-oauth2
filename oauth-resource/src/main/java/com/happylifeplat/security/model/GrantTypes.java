package com.happylifeplat.security.model;

import com.happylifeplat.security.common.EnumView;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/25 15:22</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
public enum GrantTypes implements EnumView {

    authorization_code("授权码模式"),

    implicit("简化模式"),

    password("密码模式"),

    client_credentials("客户端模式"),

    refresh_token("更新令牌模式");

    private String value;

    GrantTypes(String value) {
        this.value = value;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
