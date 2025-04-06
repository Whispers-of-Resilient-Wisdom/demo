package org.qiyu.live.bank.provider.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 支付订单持久化对象（PO）。
 * <p>
 * 该类对应数据库中的 `t_pay_order` 表，用于保存支付订单的相关信息。
 * </p>
 */
@Setter
@Getter
@TableName("t_pay_order")
public class PayOrderPO {

    @TableId(type = IdType.AUTO)
    private Long id; // 订单的唯一标识符

    private String orderId; // 订单编号

    private Integer productId; // 产品ID

    private Long userId; // 用户ID

    private Integer source; // 订单来源（例如：1：web，2：mobile）

    private Integer payChannel; // 支付渠道（例如：1：支付宝，2：微信支付）

    private Integer status; // 订单状态（例如：0：未支付，1：已支付）

    private Date payTime; // 支付时间

    private Date createTime; // 创建时间

    private Date updateTime; // 更新时间


    @Override
    public String toString() {
        return "PayOrderPO{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", productId=" + productId +
                ", userId=" + userId +
                ", source=" + source +
                ", payChannel=" + payChannel +
                ", status=" + status +
                ", payTime=" + payTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
