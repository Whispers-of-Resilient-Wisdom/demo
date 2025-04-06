package org.qiyu.live.im.provider;

import jakarta.annotation.Resource;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;

import org.qiyu.live.im.enmu.AppIdEnum;
import org.qiyu.live.im.provider.service.ImOnlineService;
import org.qiyu.live.im.provider.service.ImTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDubbo
@EnableDiscoveryClient
public class ImProviderApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImProviderApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(ImProviderApplication.class, args);
    }

}
