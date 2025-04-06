package org.qiyu.live.api.error;


import org.qiyu.live.web.starter.constants.ErrorAppIdEnum;
import org.qiyu.live.web.starter.error.QiyuBaseError;

/**
 * 定义 API 错误码及其对应的错误信息。
 * 该枚举类用于在 API 中统一管理和处理不同的错误类型和错误信息。
 * 每个枚举值表示一个具体的错误类型，包含错误代码和错误消息。
 */
public enum ApiErrorEnum implements QiyuBaseError {

    PHONE_IS_EMPTY(1, "手机号不能为空"),
    PHONE_IN_VALID(2,"手机号格式异常"),
    SMS_CODE_ERROR(3,"验证码格式异常"),
    USER_LOGIN_ERROR(4,"用户登录失败"),
    GIFT_CONFIG_ERROR(5,"礼物信息异常"),
    SEND_GIFT_ERROR(6,"送礼失败"),
    PK_ONLINE_BUSY(7,"目前正有人连线，请稍后再试"),
    NOT_SEND_TO_YOURSELF(8,"不允许送礼给自己"),
    LIVING_ROOM_END(9,"直播间已结束");

    private String errorMsg;
    private int errorCode;

    /**
     * 构造函数，用于创建一个 API 错误枚举实例。
     *
     * @param errorCode 错误代码
     * @param errorMsg 错误信息
     */
    ApiErrorEnum(int errorCode, String errorMsg) {
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    /**
     * 获取错误代码，格式为：`ErrorAppIdEnum.QIYU_API_ERROR` + 错误代码。
     *
     * @return 错误代码，拼接了应用 ID
     */
    @Override
    public int getErrorCode() {
        return Integer.parseInt(ErrorAppIdEnum.QIYU_API_ERROR.getCode() + "" + errorCode);
    }

    /**
     * 获取错误信息。
     *
     * @return 错误信息
     */
    @Override
    public String getErrorMsg() {
        return errorMsg;
    }
}

