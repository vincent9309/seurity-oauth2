package com.happylifeplat.security.service.impl;

import com.happylifeplat.security.model.Authorities;
import com.happylifeplat.security.model.Role;
import com.happylifeplat.security.model.User;
import com.happylifeplat.security.mapper.user.RoleInfoMapper;
import com.happylifeplat.security.mapper.user.UserInfoMapper;
import com.happylifeplat.security.model.UserInfo;
import com.happylifeplat.security.service.UserInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/22 14:13</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
@Service("userDetailsService")
public class UserInfoServiceImpl implements UserInfoService, MessageSourceAware {

    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private String rolePrefix = "ROLE_";

    private boolean usernameBasedPrimaryKey = true;

    private boolean enableAuthorities = true;

    private boolean enableGroups;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RoleInfoMapper roleInfoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userInfoMapper.selectByUsername(username);

        if(user == null){
            logger.warn("用户：{} 不存在", username);
            throw new UsernameNotFoundException("用户："+ username + "不存在！");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        GrantedAuthority defaultRole = new SimpleGrantedAuthority(rolePrefix + Authorities.USER.name());
        authorities.add(defaultRole);

        String userId = user.getId();
        Boolean isOwn = userInfoMapper.isOwn(userId);
        if (isOwn) {
            GrantedAuthority mobileRole = new SimpleGrantedAuthority(rolePrefix + Authorities.MOBILE.name());
            authorities.add(mobileRole);
        }

        Boolean isProvider = userInfoMapper.isProvider(userId);
        if (isProvider) {
            GrantedAuthority providerRole = new SimpleGrantedAuthority(rolePrefix + Authorities.PROVIDER.name());
            authorities.add(providerRole);
        }

        List<Role> roleList = roleInfoMapper.selectByUserId(userId);
        if (CollectionUtils.isNotEmpty(roleList)) {
            roleList.stream().forEach(role -> {
                GrantedAuthority authority = new SimpleGrantedAuthority(rolePrefix + role.getId());
                authorities.add(authority);
            });
        }

        UserInfo userInfo = new UserInfo(user, authorities);
        return userInfo;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        Assert.notNull(messageSource, "messageSource cannot be null");
        this.messages = new MessageSourceAccessor(messageSource);
    }

    public String getRolePrefix() {
        return rolePrefix;
    }

    @Override
    public void setRolePrefix(String rolePrefix) {
        this.rolePrefix = rolePrefix;
    }

    public boolean isUsernameBasedPrimaryKey() {
        return usernameBasedPrimaryKey;
    }

    @Override
    public void setUsernameBasedPrimaryKey(boolean usernameBasedPrimaryKey) {
        this.usernameBasedPrimaryKey = usernameBasedPrimaryKey;
    }

    public boolean isEnableAuthorities() {
        return enableAuthorities;
    }

    @Override
    public void setEnableAuthorities(boolean enableAuthorities) {
        this.enableAuthorities = enableAuthorities;
    }

    public boolean isEnableGroups() {
        return enableGroups;
    }

    @Override
    public void setEnableGroups(boolean enableGroups) {
        this.enableGroups = enableGroups;
    }
}
