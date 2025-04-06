package org.qiyu.live.bank.provider.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

//送礼物服务（用户的账户需要有一定的余额）
//通过一个接口，返回可以购买的产品列表
//映射我们的每个虚拟商品
/**
 * 支付产品持久化对象（PO）。
 * <p>
 * 该类对应数据库中的 `t_pay_product` 表，
 * 用于保存虚拟商品的相关信息，用户可以通过它来购买虚拟商品。
 * </p>
 */
@TableName("t_pay_product")
@Setter
@Getter
public class PayProductPO {

    @TableId(type = IdType.AUTO)
    private Long id; // 产品的唯一标识符

    private String name; // 产品名称

    private Integer price; // 产品价格（单位：虚拟币）

    private String extra; // 附加信息（如产品描述、附加参数等）

    private Integer type; // 产品类型（如：1：虚拟商品，2：实物商品等）

    private Integer validStatus; // 产品的有效状态（例如：1：有效，0：无效）

    private Date createTime; // 产品创建时间

    private Date updateTime; // 产品更新时间

    @Override
    public String toString() {
        return "PayProductPO{" +
                "createTime=" + createTime +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", extra='" + extra + '\'' +
                ", type=" + type +
                ", validStatus=" + validStatus +
                ", updateTime=" + updateTime +
                '}';
    }
}
