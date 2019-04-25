package com.happylifeplat.security.config;

import com.happylifeplat.security.common.WGJPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * <p>Description: 资源服务器配置.</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/4 15:52</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "WGJ_OAUTH_RESOURCE";

    @Autowired
    private TokenStore tokenStore;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new WGJPasswordEncoder();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(true).tokenStore(tokenStore);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .anonymous().disable()
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/swagger**").permitAll()
//                .antMatchers("/resource/**", "/grant_type/**", "/authority/**", "/client/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
        // @formatter:on
    }
}
