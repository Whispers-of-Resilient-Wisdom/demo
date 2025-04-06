package org.qiyu.live.living.provider.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * MyBatis 分页配置类。
 * 配置 MyBatis Plus 分页插件，设置分页相关参数，如单页最大记录数、数据库类型等。
 */
@Configuration
public class MybatisPageConfig {

    /**
     * 分页插件 3.5.X 配置。
     * 设置分页插件的相关配置，包括最大单页记录数、数据库类型以及优化查询。
     *
     * @return 分页插件实例
     */
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();

        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInterceptor.setMaxLimit(1000L);

        // 设置数据库类型为 MySQL
        paginationInterceptor.setDbType(DbType.MYSQL);

        // 开启 count 的 join 优化，仅针对部分 left join 查询
        paginationInterceptor.setOptimizeJoin(true);

        return paginationInterceptor;
    }

    /**
     * MyBatis Plus 插件配置。
     * 设置 MyBatis Plus 的拦截器，包括分页插件。
     *
     * @return MybatisPlusInterceptor 实例
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();

        // 设置分页插件作为拦截器
        mybatisPlusInterceptor.setInterceptors(Collections.singletonList(paginationInnerInterceptor()));

        return mybatisPlusInterceptor;
    }
}

