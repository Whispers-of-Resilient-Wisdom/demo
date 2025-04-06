package org.qiyu.live.bank.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**

 */
@Setter
@Getter
public class PayOrderDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -9209044050847451420L;

    private Long id;
    private String orderId;
    private Integer productId;
    private Integer bizCode;
    private Long userId;
    private Integer source;
    private Integer payChannel;
    private Integer status;
    private Date payTime;

    @Override
    public String toString() {
        return "PayOrderDTO{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", productId=" + productId +
                ", userId=" + userId +
                ", bizCode=" + bizCode +
                ", source=" + source +
                ", payChannel=" + payChannel +
                ", status=" + status +
                ", payTime=" + payTime +
                '}';
    }
}
