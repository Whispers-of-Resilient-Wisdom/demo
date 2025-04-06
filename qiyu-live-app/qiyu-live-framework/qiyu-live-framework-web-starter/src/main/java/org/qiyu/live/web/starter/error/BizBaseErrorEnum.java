package org.qiyu.live.web.starter.error;

/**
 * 错误信息枚举 BizBaseErrorEnum
 * <p>
 * 定义系统的基础错误类型，实现 QiyuBaseError 接口，方便统一处理异常信息。
 */
public enum BizBaseErrorEnum implements QiyuBaseError {

    /**
     * 参数异常：表示请求参数不符合预期。
     */
    PARAM_ERROR(100001, "参数异常"),

    /**
     * 用户 Token 异常：表示用户身份认证失败或 Token 无效。
     */
    TOKEN_ERROR(100002, "用户token异常");

    /**
     * 错误状态码，唯一标识不同类型的错误。
     */
    private final int errorCode;

    /**
     * 错误描述信息，提供更直观的错误原因解释。
     */
    private final String errorMsg;

    /**
     * 构造方法，初始化错误状态码和错误描述。
     *
     * @param errorCode 错误状态码。
     * @param errorMsg  错误描述信息。
     */
    BizBaseErrorEnum(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    /**
     * 获取错误状态码。
     *
     * @return 错误状态码。
     */
    @Override
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * 获取错误描述信息。
     *
     * @return 错误描述信息。
     */
    @Override
    public String getErrorMsg() {
        return errorMsg;
    }
}

