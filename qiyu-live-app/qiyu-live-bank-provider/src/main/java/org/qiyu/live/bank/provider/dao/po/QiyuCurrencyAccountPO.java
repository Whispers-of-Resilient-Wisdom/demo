package org.qiyu.live.bank.provider.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 旗鱼平台的虚拟币账户。
 * <p>
 * 该类对应数据库中的 `t_qiyu_currency_account` 表，
 * 用于保存用户在旗鱼平台上的虚拟币账户信息。
 * 包含用户的当前余额、总充值金额、账户状态等信息。
 * </p>
 */
@TableName("t_qiyu_currency_account")
@Setter
@Getter
public class QiyuCurrencyAccountPO {

    @TableId(type = IdType.INPUT)
    private Long userId; // 用户 ID，唯一标识一个用户

    private int currentBalance; // 当前账户余额，表示虚拟币的数量

    private int totalCharged; // 总充值金额，表示用户累计充值的虚拟币数量

    private Integer status; // 账户状态（例如：1：正常，0：冻结）

    private Date createTime; // 账户创建时间

    private Date updateTime; // 账户最后更新时间

    @Override
    public String toString() {
        return "QiyuCurrencyAccount{" +
                "userId=" + userId +
                ", currentBalance=" + currentBalance +
                ", totalCharged=" + totalCharged +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
