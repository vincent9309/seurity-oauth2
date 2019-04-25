package com.happylifeplat.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

/**
 * <p>Description: .</p>
 * <p>Company:  </p>
 * <p>Date: 2017/9/1 10:06</p>
 * <p>Copyright: 2015-2016 happylifeplat.com All Rights Reserved</p>
 *
 * @author vincent
 */
@SpringBootApplication
@ImportResource({"classpath:spring/application-context.xml"})
@EnableApolloConfig({"application","TEST1.apusic.config"})
public class ServerApplication {

    /**
     * 项目启动
     *
     * @param args 启动参数
     */
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
