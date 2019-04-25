package com.happylifeplat.security.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/10/25 11:48</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
public class WGJDefaultTokenServices extends DefaultTokenServices {

    @Override
    public synchronized OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
        return super.createAccessToken(authentication);
    }

    @Override
    public synchronized OAuth2AccessToken refreshAccessToken(String refreshTokenValue, TokenRequest tokenRequest) {
        return super.refreshAccessToken(refreshTokenValue, tokenRequest);
    }

}
