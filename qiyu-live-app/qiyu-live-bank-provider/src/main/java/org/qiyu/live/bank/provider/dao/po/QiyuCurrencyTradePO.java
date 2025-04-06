package org.qiyu.live.bank.provider.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
/**
 * 旗鱼平台的虚拟币交易记录
 * <p>
 * 该类对应数据库中的 `t_qiyu_currency_trade` 表，
 * 用于保存用户虚拟币交易的详细信息，
 * 包括交易的用户、交易数量、交易类型、交易状态等。
 * </p>
 */
@TableName("t_qiyu_currency_trade")
@Setter
@Getter
public class QiyuCurrencyTradePO {

    @TableId(type = IdType.AUTO)
    private Long id; // 交易记录ID，自动生成

    private Long userId; // 用户ID，标识进行交易的用户

    private Integer num; // 交易数量，表示交易的虚拟币数量（可以是正数或负数）

    private Integer type; // 交易类型，表示交易的种类（例如：1：充值，2：消费）

    private Integer status; // 交易状态，表示交易是否成功（例如：1：成功，0：失败）

    private Date createTime; // 交易创建时间

    private Date updateTime; // 交易更新时间


    @Override
    public String toString() {
        return "QiyuCurrencyTradePO{" +
                "id=" + id +
                ", userId=" + userId +
                ", num=" + num +
                ", type=" + type +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
