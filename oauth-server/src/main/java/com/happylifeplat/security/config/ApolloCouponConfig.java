package com.happylifeplat.security.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApolloCouponConfig {

    @ApolloConfig
    private Config config;

    @Value("${unicom.clientSecret}")
    private String unicomClientSecret;


    @ApolloConfigChangeListener("application")
    private void propertiesOnChange(ConfigChangeEvent changeEvent) {
        if (changeEvent == null) {
            return;
        }

        if (changeEvent.isChanged("unicom.clientSecret")) {
            unicomClientSecret = config.getProperty("unicom.clientSecret", unicomClientSecret);
        }

    }

    public String getUnicomClientSecret() {
        return unicomClientSecret;
    }

    public void setUnicomClientSecret(String unicomClientSecret) {
        this.unicomClientSecret = unicomClientSecret;
    }
}
