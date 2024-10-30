package com.linkcutter;

import com.properties.SmsCodeProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.repository")
@ComponentScan(basePackages = {"com.service", "com.controller", "com.llh", "com.config", "com.filter", "com.repository", "com.aop"}) // 确保能扫描到 com.service.impl 包
@EntityScan(basePackages = "com.entity")
@EnableAspectJAutoProxy
@EnableConfigurationProperties(SmsCodeProperties.class)
public class UsersApp {
    public static void main(String[] args) {
        SpringApplication.run(UsersApp.class, args);
    }

}
