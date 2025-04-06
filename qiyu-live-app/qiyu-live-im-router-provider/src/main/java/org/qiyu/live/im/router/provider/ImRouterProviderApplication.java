package org.qiyu.live.im.router.provider;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;


/**

 */
@SpringBootApplication
@EnableDubbo
public class ImRouterProviderApplication{


    public static void main(String[] args) {
   SpringApplication.run(ImRouterProviderApplication.class, args);
    }



}
