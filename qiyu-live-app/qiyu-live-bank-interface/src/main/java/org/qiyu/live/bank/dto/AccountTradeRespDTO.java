package org.qiyu.live.bank.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**

 */
@Setter
@Getter
public class AccountTradeRespDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 7722131828825334678L;
    private int code;
    private long userId;
    private boolean isSuccess;
    private String msg;


    public static AccountTradeRespDTO buildFail(long userId, String msg,int code) {
        AccountTradeRespDTO tradeRespDTO = new AccountTradeRespDTO();
        tradeRespDTO.setUserId(userId);
        tradeRespDTO.setCode(code);
        tradeRespDTO.setMsg(msg);
        tradeRespDTO.setSuccess(false);
        return tradeRespDTO;
    }

    public static AccountTradeRespDTO buildSuccess(long userId, String msg) {
        AccountTradeRespDTO tradeRespDTO = new AccountTradeRespDTO();
        tradeRespDTO.setUserId(userId);
        tradeRespDTO.setMsg(msg);
        tradeRespDTO.setSuccess(true);
        return tradeRespDTO;
    }

    @Override
    public String toString() {
        return "AccountTradeRespDTO{" +
                "userId=" + userId +
                ", isSuccess=" + isSuccess +
                ", msg='" + msg + '\'' +
                '}';
    }
}
