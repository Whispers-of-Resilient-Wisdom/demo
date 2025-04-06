package org.qiyu.live.web.starter.context;


import org.qiyu.live.web.starter.constants.RequestConstants;

import java.util.HashMap;
import java.util.Map;
/**
 * 旗鱼直播 用户请求上下文
 * <p>
 * 使用 ThreadLocal 实现每个线程独立的用户上下文信息存储，支持父子线程变量传递，
 * 通常用于保存用户信息、请求 ID 等上下文数据，确保在多线程环境下数据隔离。
 */
public class QiyuRequestContext {

    /**
     * ThreadLocal 存储每个线程的上下文资源，采用 InheritableThreadLocal 支持父子线程传递。
     */
    private static final ThreadLocal<Map<Object, Object>> resources = new InheritableThreadLocalMap<>();

    /**
     * 设置上下文中的键值对。
     * <p>
     * 当 value 为空时，移除对应 key 的数据。
     *
     * @param key   上下文中的键，不能为空。
     * @param value 上下文中的值，若为 null 则移除对应 key。
     */
    public static void set(Object key, Object value) {
        if (key == null) {
            throw new IllegalArgumentException("key can not be null");
        }
        if (value == null) {
            resources.get().remove(key);
        }
        resources.get().put(key, value);
    }

    /**
     * 获取当前线程的用户 ID。
     *
     * @return 用户 ID，若不存在则返回 null。
     */
    public static Long getUserId() {
        Object userId = get(RequestConstants.QIYU_USER_ID);
        return userId == null ? null : (Long) userId;
    }

    /**
     * 根据 key 获取上下文中的值。
     *
     * @param key 上下文中的键，不能为空。
     * @return 上下文中的值，若不存在则返回 null。
     */
    public static Object get(Object key) {
        if (key == null) {
            throw new IllegalArgumentException("key can not be null");
        }
        return resources.get().get(key);
    }

    /**
     * 清理当前线程的上下文信息，防止内存泄漏。
     * <p>
     * 在使用线程池的场景下（如 Tomcat 处理请求），线程可能会被重复使用，
     * 如果不手动清理 ThreadLocal 数据，可能会导致数据混乱和内存泄漏。
     */
    public static void clear() {
        resources.remove();
    }

    /**
     * 自定义的 InheritableThreadLocalMap，用于支持父子线程之间的上下文传递。
     * <p>
     * 示例：父线程 A 设置 userId 后，新建子线程 B 时，B 可以继承 A 的 userId。
     */
    private static final class InheritableThreadLocalMap<T extends Map<Object, Object>> extends InheritableThreadLocal<Map<Object, Object>> {

        /**
         * 初始化 ThreadLocal 的值，默认为空的 HashMap。
         *
         * @return 初始值，一个新的 HashMap。
         */
        @Override
        protected Map<Object, Object> initialValue() {
            return new HashMap<>();
        }

        /**
         * 支持父子线程数据传递，子线程会克隆父线程的上下文数据。
         *
         * @param parentValue 父线程的上下文数据。
         * @return 子线程的上下文数据，为父线程数据的克隆副本。
         */
        @Override
        protected Map<Object, Object> childValue(Map<Object, Object> parentValue) {
            if (parentValue != null) {
                return (Map<Object, Object>) ((HashMap<Object, Object>) parentValue).clone();
            } else {
                return null;
            }
        }
    }
}
