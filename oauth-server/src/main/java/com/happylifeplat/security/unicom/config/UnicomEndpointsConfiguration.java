package com.happylifeplat.security.unicom.config;

import com.happylifeplat.security.unicom.controller.UnicomEndpoint;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.OAuth2RequestValidator;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.oauth2.provider.endpoint.TokenKeyEndpoint;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Configuration
@Import(UnicomEndpointsConfiguration.TokenKeyEndpointRegistrar.class)
public class UnicomEndpointsConfiguration {

    private AuthorizationServerEndpointsConfigurer endpoints = new AuthorizationServerEndpointsConfigurer();

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private List<AuthorizationServerConfigurer> configurers = Collections.emptyList();

    @Bean
    public UnicomEndpoint unicomEndpoint() throws Exception {
        UnicomEndpoint unicomEndpoint = new UnicomEndpoint();
        unicomEndpoint.setClientDetailsService(clientDetailsService);
        unicomEndpoint.setProviderExceptionHandler(exceptionTranslator());
        unicomEndpoint.setTokenGranter(tokenGranter());
        unicomEndpoint.setOAuth2RequestFactory(oauth2RequestFactory());
        unicomEndpoint.setOAuth2RequestValidator(oauth2RequestValidator());
        unicomEndpoint.setAllowedRequestMethods(allowedTokenEndpointRequestMethods());
        return unicomEndpoint;
    }

    @PostConstruct
    public void init() {
        for (AuthorizationServerConfigurer configurer : configurers) {
            try {
                configurer.configure(endpoints);
            } catch (Exception e) {
                throw new IllegalStateException("Cannot configure enpdoints", e);
            }
        }
        endpoints.setClientDetailsService(clientDetailsService);
    }

    @Component
    protected static class TokenKeyEndpointRegistrar implements BeanDefinitionRegistryPostProcessor {

        private BeanDefinitionRegistry registry;

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            String[] names = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(beanFactory,
                    JwtAccessTokenConverter.class, false, false);
            if (names.length > 0) {
                BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(TokenKeyEndpoint.class);
                builder.addConstructorArgReference(names[0]);
                registry.registerBeanDefinition(TokenKeyEndpoint.class.getName(), builder.getBeanDefinition());
            }
        }

        @Override
        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
            this.registry = registry;
        }

    }

    @Bean
    public AuthorizationServerTokenServices defaultAuthorizationServerTokenServices() {
        return endpoints.getDefaultAuthorizationServerTokenServices();
    }

    public AuthorizationServerEndpointsConfigurer getEndpointsConfigurer() {
        if (!endpoints.isTokenServicesOverride()) {
            endpoints.tokenServices(defaultAuthorizationServerTokenServices());
        }
        return endpoints;
    }

    private Set<HttpMethod> allowedTokenEndpointRequestMethods() {
        return getEndpointsConfigurer().getAllowedTokenEndpointRequestMethods();
    }

    private OAuth2RequestFactory oauth2RequestFactory() throws Exception {
        return getEndpointsConfigurer().getOAuth2RequestFactory();
    }

    private UserApprovalHandler userApprovalHandler() throws Exception {
        return getEndpointsConfigurer().getUserApprovalHandler();
    }

    private OAuth2RequestValidator oauth2RequestValidator() throws Exception {
        return getEndpointsConfigurer().getOAuth2RequestValidator();
    }

    private AuthorizationCodeServices authorizationCodeServices() throws Exception {
        return getEndpointsConfigurer().getAuthorizationCodeServices();
    }

    private WebResponseExceptionTranslator exceptionTranslator() {
        return getEndpointsConfigurer().getExceptionTranslator();
    }

    private TokenGranter tokenGranter() throws Exception {
        return getEndpointsConfigurer().getTokenGranter();
    }
}
