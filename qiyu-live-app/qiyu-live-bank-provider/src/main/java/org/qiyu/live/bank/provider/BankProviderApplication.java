package org.qiyu.live.bank.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**

 */
@SpringBootApplication
@EnableDubbo
public class BankProviderApplication  {


    public static void main(String[] args) {
   SpringApplication.run(BankProviderApplication.class, args);
    }
}
