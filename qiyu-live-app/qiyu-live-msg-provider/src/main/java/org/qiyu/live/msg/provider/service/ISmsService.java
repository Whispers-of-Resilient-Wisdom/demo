package org.qiyu.live.msg.provider.service;

import org.qiyu.live.msg.provider.DTO.MsgCheckDTO;
import org.qiyu.live.msg.provider.Enums.MsgSendResultEnum;


public interface ISmsService {

    /**
     * 发送短信接口
     *

     */
    MsgSendResultEnum sendMessage(String phone);

    /**
     * 校验登录验证码
     *

     */
    MsgCheckDTO checkLoginCode(String phone, Integer code);

    /**
     * 插入一条短信记录
     */
    void insertOne(String phone, Integer code);
}