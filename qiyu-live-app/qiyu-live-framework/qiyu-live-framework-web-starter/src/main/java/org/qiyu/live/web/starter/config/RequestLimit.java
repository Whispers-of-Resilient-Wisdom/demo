package org.qiyu.live.web.starter.config;

import java.lang.annotation.*;
/**
 * 自定义注解：RequestLimit
 * <p>
 * 用于限制方法的请求频率，常用于接口防刷场景。
 * 可以设置指定时间范围内允许的请求次数，超出限制时返回自定义提示信息。
 */
@Documented
@Target(ElementType.METHOD)  // 该注解只能用在方法上
@Retention(RetentionPolicy.RUNTIME)  // 运行时保留，便于反射获取注解信息
public @interface RequestLimit {

    /**
     * 允许的最大请求次数
     * <p>
     * 表示在指定时间范围内（second）允许的请求次数上限。
     *
     * @return 允许的请求次数
     */
    int limit();

    /**
     * 时间范围（秒）
     * <p>
     * 指定限制请求的时间范围，单位为秒。
     *
     * @return 时间范围，单位秒
     */
    int second();

    /**
     * 超出请求限制时的提示信息
     * <p>
     * 当请求被限制时，返回该提示信息，默认为“请求过于频繁”。
     *
     * @return 提示信息
     */
    String msg() default "请求过于频繁";
}

