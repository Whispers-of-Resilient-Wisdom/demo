package org.qiyu.live.gift.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类，启动礼物提供者应用程序。

 */
@SpringBootApplication
@EnableDubbo
public class GiftProviderApplication {

    public static void main(String[] args) {
    SpringApplication.run(GiftProviderApplication.class, args);
    }

}
