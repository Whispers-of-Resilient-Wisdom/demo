package org.qiyu.live.core.server;


import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;



/**
 * description: netty启动类
 */
@SpringBootApplication
@EnableDubbo
public class  NettyServerApplication {



    public static void main(String[] args) {
        SpringApplication.run(NettyServerApplication.class, args);
    }
}
