package org.qiyu.live.gift.provider.rpc;

import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.qiyu.live.gift.dto.GiftConfigDTO;
import org.qiyu.live.gift.interfaces.IGiftConfigRpc;
import org.qiyu.live.gift.provider.service.IGiftConfigService;

import java.util.List;

/**
 * 礼物配置远程服务实现类。
 * 提供礼物配置的远程调用接口实现。
 */
@DubboService
public class GiftConfigRpcImpl implements IGiftConfigRpc {

    /** 礼物配置服务 */
    @Resource
    private IGiftConfigService giftConfigService;

    /**
     * 根据礼物ID获取礼物配置信息。
     *
     * @param giftId 礼物ID
     * @return 礼物配置数据传输对象
     */
    @Override
    public GiftConfigDTO getByGiftId(Integer giftId) {
        return giftConfigService.getByGiftId(giftId);
    }

    /**
     * 查询所有礼物配置信息。
     *
     * @return 礼物配置数据传输对象列表
     */
    @Override
    public List<GiftConfigDTO> queryGiftList() {
        return giftConfigService.queryGiftList();
    }

    /**
     * 插入一条礼物配置信息。
     *
     * @param giftConfigDTO 礼物配置数据传输对象
     */
    @Override
    public void insertOne(GiftConfigDTO giftConfigDTO) {
        giftConfigService.insertOne(giftConfigDTO);
    }

    /**
     * 更新一条礼物配置信息。
     *
     * @param giftConfigDTO 礼物配置数据传输对象
     */
    @Override
    public void updateOne(GiftConfigDTO giftConfigDTO) {
        giftConfigService.updateOne(giftConfigDTO);
    }
}

