package org.qiyu.user.provider.config;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "qiyu.rmq.consumer")
@Configuration
@Getter
@Setter
public class RocketMQConsumerProperties {
    //rocketmq 的 nameSever 地址
    private String nameSrv;
    //分组名称
    private String groupName;

    public String toString() {
        return "RocketMQConsumerProperties{" +
                "nameSrv='" + nameSrv + '\'' +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
