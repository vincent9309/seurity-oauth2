package com.happylifeplat.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/18 17:52</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
public class UserInfo implements UserDetails {

    private static final long serialVersionUID = 8780705329815294485L;

    private  final User user;

    private final Set<GrantedAuthority> authorities;

    public UserInfo(User user, Set<GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getIsLockedOut() ? false : true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getIsLockedOut() ? false : true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getIsLockedOut() ? false : true;
    }

    @Override
    public boolean isEnabled() {
        return user.getIsDisable() ? false : true;
    }

    public String getPhone() {
        return user.getPhone();
    }


    public String getEmail() {
        return user.getEmail();
    }


    public String getImageUrl() {
        return user.getImageUrl();
    }
}
