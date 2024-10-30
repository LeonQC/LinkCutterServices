package com.linkcutter;

import com.properties.SmsCodeProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.linkcutter", "com.config"})
public class LinksApp
{
    public static void main(String[] args) {
        SpringApplication.run(LinksApp.class, args);
    }

}
