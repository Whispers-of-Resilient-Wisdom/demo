package org.qiyu.live.id.generate.provider.service.BO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * LocalUnSeqIdBO 是一个用于表示非连续 ID 生成配置的业务对象。
 * 它与 LocalSeqIdBO 不同，采用链表结构来记录 ID 值，并通过打乱 ID 列表来保证生成的 ID 非连续。
 */
@Setter
@Getter
@ToString
public class LocalUnSeqIdBO {

    // 用于标识该 ID 配置的唯一 ID，来自 MySQL 数据库
    private Integer id;

    // 对应的分布式 ID 配置说明，用于标识该配置的用途或描述
    private String desc;

    // 用链表来记录 ID 值，保证 ID 的非连续性
    private ConcurrentLinkedQueue<Long> idQueue;

    // 本地内存中记录的 ID 段的开始位置
    private Long currentStart;

    // 本地内存中记录的 ID 段的结束位置（阈值）
    private Long nextThreshold;

    // 每次生成 ID 时的步长
    private Integer step;

    /**
     * 设置 ID 队列，并将 ID 列表打乱，以保证 ID 非连续性
     *
     * @param begin 起始 ID 值
     * @param end   结束 ID 值
     */
    public void setRandomIdInQueue(long begin, long end) {
        List<Long> idList = new LinkedList<>();
        for (long j = begin; j < end; j++) {
            idList.add(j);
        }
        // 打乱 ID 列表
        Collections.shuffle(idList);
        ConcurrentLinkedQueue<Long> idQueue = new ConcurrentLinkedQueue<>();
        idQueue.addAll(idList);
        this.setIdQueue(idQueue);
    }

}

