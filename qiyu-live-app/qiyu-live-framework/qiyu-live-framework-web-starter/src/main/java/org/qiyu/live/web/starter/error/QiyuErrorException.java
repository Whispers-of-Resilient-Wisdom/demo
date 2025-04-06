package org.qiyu.live.web.starter.error;

/**
 * 旗鱼直播自定义异常 QiyuErrorException
 * <p>
 * 该异常类继承自 RuntimeException，主要用于封装业务逻辑中的错误信息。
 * 支持直接传递错误码和错误信息，或者通过 QiyuBaseError 枚举类来构造异常。
 */
public class QiyuErrorException extends RuntimeException {

    // 错误码，标识具体的错误类型
    private int errorCode;

    // 错误信息，描述具体的错误原因
    private String errorMsg;

    /**
     * 构造方法：使用错误码和错误信息初始化异常
     *
     * @param errorCode 错误码
     * @param errorMsg  错误信息
     */
    public QiyuErrorException(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    /**
     * 构造方法：通过 QiyuBaseError 枚举类初始化异常
     *
     * @param qiyuBaseError 自定义错误枚举，包含错误码和错误信息
     */
    public QiyuErrorException(QiyuBaseError qiyuBaseError) {
        this.errorCode = qiyuBaseError.getErrorCode();
        this.errorMsg = qiyuBaseError.getErrorMsg();
    }

    /**
     * 获取错误码
     *
     * @return 错误码（int 类型）
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * 设置错误码
     *
     * @param errorCode 错误码（int 类型）
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 获取错误信息
     *
     * @return 错误信息（String 类型）
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * 设置错误信息
     *
     * @param errorMsg 错误信息（String 类型）
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}

