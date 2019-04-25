package com.happylifeplat.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/7/20 11:08</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
@SpringBootApplication
@ImportResource({"classpath:spring/application-context.xml"})
@EnableApolloConfig({"application","TEST1.apusic.config"})
public class ResourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourceApplication.class, args);
    }

}
