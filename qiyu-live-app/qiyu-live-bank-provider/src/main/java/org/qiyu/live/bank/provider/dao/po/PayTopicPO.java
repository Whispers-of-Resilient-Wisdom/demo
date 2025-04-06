package org.qiyu.live.bank.provider.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 支付话题持久化对象（PO）。
 * <p>
 * 该类对应数据库中的 `t_pay_topic` 表，用于保存与支付相关的话题信息。
 * 例如，可以用于保存支付业务的具体信息和状态。
 * </p>
 */
@TableName("t_pay_topic")
@Setter
@Getter
public class PayTopicPO {

    @TableId(type = IdType.AUTO)
    private Long id; // 唯一标识话题的 ID

    private String topic; // 话题名称（例如：支付成功、支付失败等）

    private Integer bizCode; // 业务编码，用于标识具体的业务类型

    private Integer status; // 话题的状态（例如：1：启用，0：禁用）

    private String remark; // 备注信息，可以存储一些额外说明

    private Date createTime; // 话题创建时间

    private Date updateTime; // 话题更新时间


    @Override
    public String toString() {
        return "PayTopic{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", status='" + status + '\'' +
                ", bizCode=" + bizCode +
                ", remark='" + remark + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
