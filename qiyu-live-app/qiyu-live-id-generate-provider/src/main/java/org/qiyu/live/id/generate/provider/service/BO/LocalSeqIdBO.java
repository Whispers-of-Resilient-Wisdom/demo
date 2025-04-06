package org.qiyu.live.id.generate.provider.service.BO;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicLong;
/**
 * LocalSeqIdBO 是一个用于表示本地序列 ID 生成配置的业务对象。
 * 它包含了诸如 ID、描述、当前值、ID 段的开始和结束位置，以及步长等信息，
 * 用于在本地内存中生成和管理 ID。
 */
@Setter
@Getter

public class LocalSeqIdBO {

    // 用于标识具体的 ID 配置信息，来源于 MySQL 数据库
    private Integer id;

    // 对应的分布式 ID 配置的描述，用于标识这个配置的用途或说明
    private String desc;

    // 当前在本地内存中的 ID 值，使用 AtomicLong 保证线程安全
    private AtomicLong currentValue;

    // 本地内存中记录的 ID 段的开始位置
    private Long currentStart;

    // 本地内存中记录的 ID 段的结束位置（阈值）
    private Long nextThreshold;

    // 每次生成 ID 时的步长
    private Integer step;

    @Override
    public String toString() {
        return "LocalSeqIdBO{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", currentValue=" + currentValue +
                ", currentStart=" + currentStart +
                ", nextThreshold=" + nextThreshold +
                ", step=" + step +
                '}';
    }
}
