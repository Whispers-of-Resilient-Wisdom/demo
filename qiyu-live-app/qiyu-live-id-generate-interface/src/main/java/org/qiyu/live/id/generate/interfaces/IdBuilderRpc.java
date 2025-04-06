package org.qiyu.live.id.generate.interfaces;

/**
 * 提供唯一 ID 生成服务的 RPC 接口。
 * 该接口定义了三种生成 ID 的方法，分别为区间性递增的唯一 ID、非连续性的唯一 ID 和字符串格式的递增 ID。
 */
public interface IdBuilderRpc {

    /**
     * 根据本地步长度生成唯一 ID，生成的是区间性递增的 ID。
     * 该方法生成的 ID 是递增的，适用于需要顺序编号的场景。
     *
     * @param code 用于区分不同的 ID 生成器的代码，可以是业务的标识符。
     * @return 生成的唯一 ID
     */
    Long increaseSeqId(int code);

    /**
     * 生成非连续性的唯一 ID。
     * 该方法生成的 ID 不保证连续性，适用于分布式环境下生成不重复的 ID。
     *
     * @param code 用于区分不同的 ID 生成器的代码，可以是业务的标识符。
     * @return 生成的非连续性唯一 ID
     */
    Long increaseUnSeqId(int code);

    /**
     * 根据本地步长度生成唯一 ID，生成的是区间性递增的字符串格式 ID。
     * 该方法生成的 ID 是递增的，适用于需要顺序编号的场景，且返回字符串类型的 ID。
     *
     * @param code 用于区分不同的 ID 生成器的代码，可以是业务的标识符。
     * @return 生成的唯一 ID（字符串格式）
     */
    String increaseSeqStrId(int code);
}

