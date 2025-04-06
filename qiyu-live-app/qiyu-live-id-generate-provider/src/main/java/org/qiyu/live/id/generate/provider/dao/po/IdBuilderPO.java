package org.qiyu.live.id.generate.provider.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
/**
 * IdBuilderPO 实体类，映射到数据库中的 t_id_generate_config 表。
 * 该类用于存储 ID 生成的配置信息，包括初始化值、步长、当前阶段的开始值等。
 */
@TableName("t_id_generate_config")
@Setter
@Getter
public class IdBuilderPO {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * ID 备注描述，用于说明该 ID 配置的用途或其他说明。
     */
    private String remark;

    /**
     * 初始化值，表示 ID 生成的起始值。
     */
    private long initNum;

    /**
     * 步长，表示 ID 在每次生成时的递增幅度。
     */
    private int step;

    /**
     * 是否是有序的 ID，1 表示有序，0 表示无序。
     */
    private int isSeq;

    /**
     * 当前 ID 所在阶段的开始值，表示当前阶段 ID 生成的起始值。
     */
    private long currentStart;

    /**
     * 当前 ID 所在阶段的阈值，表示当前阶段的结束值，超过该值后会切换阶段。
     */
    private long nextThreshold;

    /**
     * 业务代码前缀，用于生成的 ID 前加上该业务相关的前缀。
     */
    private String idPrefix;

    /**
     * 乐观锁版本号，用于支持乐观锁，确保并发情况下数据的一致性。
     */
    private int version;

    /**
     * 创建时间，表示该配置记录的创建时间。
     */
    private Date createTime;

    /**
     * 更新时间，表示该配置记录的最后更新时间。
     */
    private Date updateTime;

    @Override
    public String toString() {
        return "IdBuilderPO{" +
                "id=" + id +
                ", remark='" + remark + '\'' +
                ", initNum=" + initNum +
                ", step=" + step +
                ", isSeq=" + isSeq +
                ", currentStart=" + currentStart +
                ", nextThreshold=" + nextThreshold +
                ", idPrefix='" + idPrefix + '\'' +
                ", version=" + version +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
