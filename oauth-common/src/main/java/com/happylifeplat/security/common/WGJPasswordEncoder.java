package com.happylifeplat.security.common;

import com.happylifeplat.security.Crypto;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/1 16:30</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
public class WGJPasswordEncoder implements PasswordEncoder {

    private Crypto crypto = new Crypto();

    @Override
    public String encode(CharSequence charSequence) {
        return crypto.hashPassword(charSequence.toString());
    }

    @Override
    public boolean matches(CharSequence charSequence, String encodedPassword) {
        //client_secret校验  secret明文，需要加密校验
        Boolean result = crypto.verify(charSequence.toString(), encodedPassword);
        //如果是密码模式，第二次password校验 db存的password是密文 直接校验
        if (result == false) {
            result = charSequence.toString().equals(encodedPassword) ? true : false;
        }
        return result;
    }
}
