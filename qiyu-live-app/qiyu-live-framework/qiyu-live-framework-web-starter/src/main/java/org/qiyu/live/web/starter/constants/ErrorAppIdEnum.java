package org.qiyu.live.web.starter.constants;
/**
 * 错误应用 ID 枚举类
 * <p>
 * 用于定义系统中不同模块的错误来源标识。
 */
public enum ErrorAppIdEnum {

    /**
     * 奇遇直播 API 错误
     * code: 101，msg: "qiyu-live-api"
     */
    QIYU_API_ERROR(101, "qiyu-live-api");

    /**
     * 错误码
     */
    private int code;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 枚举构造方法
     *
     * @param code 错误码
     * @param msg  错误信息
     */
    ErrorAppIdEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    public int getCode() {
        return code;
    }

    /**
     * 设置错误码
     *
     * @param code 错误码
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 获取错误信息
     *
     * @return 错误信息
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置错误信息
     *
     * @param msg 错误信息
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
