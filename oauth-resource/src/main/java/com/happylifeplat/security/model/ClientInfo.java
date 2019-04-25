package com.happylifeplat.security.model;

import org.apache.commons.lang3.math.NumberUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/15 14:36</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
public class ClientInfo implements Serializable {

    private static final long serialVersionUID = -6214220701726956015L;

    private static final Integer ACCESS_TOKEN_VALIDITY = 60 * 60 * 12; // default 12 hours.

    private static final Integer REFRESH_TOEKN_VALIDITY = 60 * 60 * 24 * 30; // default 30 days.

    private String name;

    private Set<String> resourceIds = Collections.emptySet();

    private Set<String> scope = Collections.emptySet();

    private Set<String> authorizedGrantTypes = Collections.emptySet();

    private Set<String> webServerRedirectUri;

    private Set<String> authorities = Collections.emptySet();

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String additionalInformation;

    private Set<String> autoapprove;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(Set<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public Set<String> getScope() {
        return scope;
    }

    public void setScope(Set<String> scope) {
        this.scope = scope;
    }

    public Set<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public Set<String> getWebServerRedirectUri() {
        return webServerRedirectUri;
    }

    public void setWebServerRedirectUri(Set<String> webServerRedirectUri) {
        this.webServerRedirectUri = webServerRedirectUri;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity) {
        if (accessTokenValidity != null && accessTokenValidity > NumberUtils.INTEGER_ZERO) {
            this.accessTokenValidity = accessTokenValidity;
        } else {
            this.accessTokenValidity = ACCESS_TOKEN_VALIDITY;
        }
    }

    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        if (refreshTokenValidity != null && refreshTokenValidity > NumberUtils.INTEGER_ZERO) {
            this.refreshTokenValidity = refreshTokenValidity;
        } else {
            this.refreshTokenValidity = REFRESH_TOEKN_VALIDITY;
        }
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public Set<String> getAutoapprove() {
        return autoapprove;
    }

    public void setAutoapprove(Set<String> autoapprove) {
        this.autoapprove = autoapprove;
    }
}
