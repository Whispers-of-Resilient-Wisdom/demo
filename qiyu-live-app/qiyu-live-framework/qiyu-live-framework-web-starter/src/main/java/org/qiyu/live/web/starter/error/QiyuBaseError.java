package org.qiyu.live.web.starter.error;

/**
 * 旗鱼直播自定义错误接口 QiyuBaseError
 * <p>
 * 该接口定义了错误码与错误信息的规范，所有自定义错误枚举类都应实现该接口。
 * 通过多态机制，统一管理项目中的错误类型，提升代码可读性和可维护性。
 */
public interface QiyuBaseError {

    /**
     * 获取错误码
     * <p>
     * 用于标识具体的错误类型，便于客户端和服务端进行错误分类与处理。
     *
     * @return 错误码（int 类型）
     */
    int getErrorCode();

    /**
     * 获取错误信息
     * <p>
     * 提供具体的错误描述信息，方便开发者或用户理解错误原因。
     *
     * @return 错误信息（String 类型）
     */
    String getErrorMsg();
}


