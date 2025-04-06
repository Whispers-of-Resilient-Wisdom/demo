package org.qiyu.user.provider;

import jakarta.annotation.Resource;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.qiyu.user.provider.service.IUserPhoneService;
import org.qiyu.user.provider.service.IUserService;
import org.qiyu.user.provider.service.IUserTagService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDubbo
@EnableDiscoveryClient
public class UserProviderApplication   {
    @Resource
    private IUserTagService userTagService;
    @Resource
    private IUserService userService;
    @Resource
    private IUserPhoneService userPhoneService;

    public static void main(String[] args) {
        SpringApplication.run(UserProviderApplication.class, args);
    }

}
