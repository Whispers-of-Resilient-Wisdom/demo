package org.qiyu.live.id.generate.provider;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDubbo
@EnableDiscoveryClient
public class IdGenerateApplication  {

    public static void main(String[] args) {
    new SpringApplication(IdGenerateApplication.class).run(args);
    }



}


