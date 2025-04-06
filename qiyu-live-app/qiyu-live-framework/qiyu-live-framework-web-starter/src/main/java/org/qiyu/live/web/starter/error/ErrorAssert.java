package org.qiyu.live.web.starter.error;


/**
 * 错误断言工具类 ErrorAssert
 * <p>
 * 提供一系列静态方法，用于在代码逻辑中进行参数校验和业务条件判断，
 * 若校验失败，则抛出自定义的 QiyuErrorException 异常。
 */
public class ErrorAssert {

    /**
     * 判断对象是否为空（obj != null）
     * <p>
     * 如果对象为 null，抛出 QiyuErrorException 异常，并携带自定义的错误信息。
     *
     * @param obj           待校验的对象
     * @param qiyuBaseError 错误信息枚举，实现 QiyuBaseError 接口，封装错误码和错误信息
     * @throws QiyuErrorException 若对象为 null，则抛出异常
     */
    public static void isNotNull(Object obj, QiyuBaseError qiyuBaseError) {
        if (obj == null) {
            throw new QiyuErrorException(qiyuBaseError);
        }
    }

    /**
     * 判断字符串是否为空白（str != null && str.trim().length() > 0）
     * <p>
     * 若字符串为 null、空字符串或仅由空格组成，抛出 QiyuErrorException 异常。
     *
     * @param str           待校验的字符串
     * @param qiyuBaseError 错误信息枚举，实现 QiyuBaseError 接口，封装错误码和错误信息
     * @throws QiyuErrorException 若字符串为空白，则抛出异常
     */
    public static void isNotBlank(String str, QiyuBaseError qiyuBaseError) {
        if (str == null || str.trim().length() == 0) {
            throw new QiyuErrorException(qiyuBaseError);
        }
    }

    /**
     * 判断布尔值是否为 true（flag == true）
     * <p>
     * 若布尔值为 false，抛出 QiyuErrorException 异常。
     *
     * @param flag          条件判断结果，期望为 true
     * @param qiyuBaseError 错误信息枚举，实现 QiyuBaseError 接口，封装错误码和错误信息
     * @throws QiyuErrorException 若 flag 为 false，则抛出异常
     */
    public static void isTure(boolean flag, QiyuBaseError qiyuBaseError) {
        if (!flag) {
            throw new QiyuErrorException(qiyuBaseError);
        }
    }

    /**
     * 判断布尔值是否为 true（flag == true），支持自定义异常信息。
     * <p>
     * 若布尔值为 false，抛出传入的 QiyuErrorException 异常。
     *
     * @param flag               条件判断结果，期望为 true
     * @param qiyuErrorException 自定义异常对象，携带特定的错误信息
     * @throws QiyuErrorException 若 flag 为 false，则抛出传入的异常
     */
    public static void isTure(boolean flag, QiyuErrorException qiyuErrorException) {
        if (!flag) {
            throw qiyuErrorException;
        }
    }
}
