package org.qiyu.live.id.generate.provider.RPC;



import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.qiyu.live.id.generate.interfaces.IdBuilderRpc;
import org.qiyu.live.id.generate.provider.service.IdBuilderService;

/**
 * IdBuilderRpcImpl 实现类，提供 ID 生成服务的远程调用接口实现。
 * 该类使用 Dubbo 提供的 RPC 服务框架，暴露 `IdBuilderRpc` 接口，处理 ID 生成相关请求。
 */
@DubboService(protocol = "dubbo")
public class IdBuilderRpcImpl implements IdBuilderRpc {

    @Resource
    private IdBuilderService idBuilderService;

    /**
     * 根据业务代码生成一个顺序递增的唯一 ID。
     *
     * @param code 业务代码，用于区分不同的 ID 生成规则。
     * @return 返回生成的顺序递增的唯一 ID。
     */
    @Override
    public Long increaseSeqId(int code) {
        // 调用 IdBuilderService 获取顺序递增的 ID
        return idBuilderService.getSeqId(code);
    }

    /**
     * 根据业务代码生成一个非顺序递增的唯一 ID。
     *
     * @param code 业务代码，用于区分不同的 ID 生成规则。
     * @return 返回生成的非顺序递增的唯一 ID。
     */
    @Override
    public Long increaseUnSeqId(int code) {
        // 调用 IdBuilderService 获取非顺序递增的 ID
        return idBuilderService.getUnSeqId(code);
    }

    /**
     * 根据业务代码生成一个顺序递增的唯一 ID，并返回字符串类型的 ID。
     *todo
     * @param code 业务代码，用于区分不同的 ID 生成规则。
     * @return 返回生成的顺序递增的唯一 ID，格式为字符串。
     */
    @Override
    public String increaseSeqStrId(int code) {
        // 目前未实现该功能，返回空字符串

        return "";
    }
}
