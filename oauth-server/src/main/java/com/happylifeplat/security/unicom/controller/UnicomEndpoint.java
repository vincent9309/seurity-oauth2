package com.happylifeplat.security.unicom.controller;

import com.happylifeplat.security.config.ApolloCouponConfig;
import com.happylifeplat.security.unicom.enums.UnicomApiCode;
import com.happylifeplat.security.unicom.model.AccessTokenDto;
import com.happylifeplat.security.unicom.model.RefreshTokenDto;
import com.happylifeplat.security.unicom.model.Result;
import com.happylifeplat.security.unicom.utils.MD5;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2RequestValidator;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.endpoint.AbstractEndpoint;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestValidator;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.*;

@FrameworkEndpoint
public class UnicomEndpoint extends AbstractEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(UnicomEndpoint.class);

    private OAuth2RequestValidator oAuth2RequestValidator = new DefaultOAuth2RequestValidator();

    private Set<HttpMethod> allowedRequestMethods = new HashSet<HttpMethod>(Arrays.asList(HttpMethod.POST));

    @Autowired
    private ApolloCouponConfig apolloCouponConfig;

    @GetMapping(value = "/unicom/GetAccessToken")
    public ResponseEntity<Result> getUnicomAccessToken(AccessTokenDto accessTokenDto){
        logger.info("access_token request param : " + accessTokenDto.toString());
        String clientId = accessTokenDto.getClient_id();
        if(StringUtils.isEmpty(clientId)){
            return getResponse(Result.error(UnicomApiCode.PARAM_ERROR));
        }
        ClientDetails authenticatedClient = getClientDetailsService().loadClientByClientId(clientId);
        if(!checkSign(accessTokenDto, authenticatedClient)){
            return getResponse(Result.error(UnicomApiCode.PARAM_ERROR));
        }
        if(Objects.isNull(authenticatedClient)){
            return getResponse(Result.error(UnicomApiCode.PARAM_ERROR));
        }
        Map<String, String> parameters = getAccessTokenParams(accessTokenDto, authenticatedClient);
        TokenRequest tokenRequest = getOAuth2RequestFactory().createTokenRequest(parameters, authenticatedClient);

        OAuth2AccessToken token = getTokenGranter().grant(tokenRequest.getGrantType(), tokenRequest);
        if (Objects.isNull(token)) {
            return getResponse(Result.error(UnicomApiCode.PARAM_ERROR));
        }

        return getResponse(token2Result(token));
    }

    @GetMapping(value = "/unicom/RefreshToken")
    public ResponseEntity<Result> getUnicomRefreshToken(RefreshTokenDto refreshTokenDto){
        logger.info("refresh_token request param : " + refreshTokenDto.toString());
        String clientId = refreshTokenDto.getClient_id();
        if(StringUtils.isEmpty(clientId)){
            return getResponse(Result.error(UnicomApiCode.PARAM_ERROR));
        }
        ClientDetails authenticatedClient = getClientDetailsService().loadClientByClientId(clientId);
        if(Objects.isNull(authenticatedClient)){
            return getResponse(Result.error(UnicomApiCode.PARAM_ERROR));
        }
        Map<String, String> parameters = getRefreshTokenParams(refreshTokenDto, authenticatedClient);

        TokenRequest tokenRequest = getOAuth2RequestFactory().createTokenRequest(parameters, authenticatedClient);
        // A refresh token has its own default scopes, so we should ignore any added by the factory here.
        tokenRequest.setScope(OAuth2Utils.parseParameterList(parameters.get(OAuth2Utils.SCOPE)));

        OAuth2AccessToken token = getTokenGranter().grant(tokenRequest.getGrantType(), tokenRequest);
        if (Objects.isNull(token)) {
            return getResponse(Result.error(UnicomApiCode.PARAM_ERROR));
        }

        return getResponse(token2Result(token));
    }

    private boolean checkSign(AccessTokenDto accessTokenDto, ClientDetails authenticatedClient){

        StringBuffer strParam = new StringBuffer();
        strParam.append(apolloCouponConfig.getUnicomClientSecret()).append(accessTokenDto.getTimestamp())
                .append(accessTokenDto.getClient_id()).append(accessTokenDto.getUsername()).append(accessTokenDto.getPassword())
                .append(accessTokenDto.getGrant_type()).append(apolloCouponConfig.getUnicomClientSecret());
        logger.info("strParam : " + strParam +"sign: " + MD5.getHex(strParam.toString()));
        if(Objects.equals(accessTokenDto.getSign(), MD5.getHex(strParam.toString()))){
            return true;
        }
        return false;
    }

    private ResponseEntity<Result> getResponse(Result result) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        return new ResponseEntity<Result>(result, headers, HttpStatus.OK);
    }

    private Result token2Result(OAuth2AccessToken token){
        return Result.success(UnicomApiCode.SUCCESS, token);
    }

    private Map<String, String> getAccessTokenParams(AccessTokenDto accessTokenDto, ClientDetails authenticatedClient){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "password");
        parameters.put("client_id", authenticatedClient.getClientId());
        parameters.put("client_secret", apolloCouponConfig.getUnicomClientSecret());
        parameters.put("username", accessTokenDto.getUsername());
        parameters.put("password", accessTokenDto.getPassword());
        return parameters;
    }

    private Map<String, String> getRefreshTokenParams(RefreshTokenDto refreshTokenDto, ClientDetails authenticatedClient){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "refresh_token");
        parameters.put("client_id", authenticatedClient.getClientId());
        parameters.put("client_secret", apolloCouponConfig.getUnicomClientSecret());
        parameters.put("refresh_token", refreshTokenDto.getRefresh_token());
        return parameters;
    }

    public void setOAuth2RequestValidator(OAuth2RequestValidator oAuth2RequestValidator) {
        this.oAuth2RequestValidator = oAuth2RequestValidator;
    }

    public void setAllowedRequestMethods(Set<HttpMethod> allowedRequestMethods) {
        this.allowedRequestMethods = allowedRequestMethods;
    }
}
