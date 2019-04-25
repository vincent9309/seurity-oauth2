package com.happylifeplat.security.service;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/22 14:12</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
public interface UserInfoService extends UserDetailsService {

    void setRolePrefix(String rolePrefix);

    void setUsernameBasedPrimaryKey(boolean usernameBasedPrimaryKey);

    void setEnableAuthorities(boolean enableAuthorities);

    void setEnableGroups(boolean enableGroups);
}
