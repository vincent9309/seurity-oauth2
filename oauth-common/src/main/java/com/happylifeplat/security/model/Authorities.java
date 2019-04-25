package com.happylifeplat.security.model;

import com.happylifeplat.security.common.EnumView;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/7 15:38</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
public enum Authorities implements EnumView {

    USER("用户"),

    MOBILE("消费者"),

    PROVIDER("供应商"),

    SYSTEM("系统"),

    ADMIN("管理员");

    private String value;

    Authorities(String value) {
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
