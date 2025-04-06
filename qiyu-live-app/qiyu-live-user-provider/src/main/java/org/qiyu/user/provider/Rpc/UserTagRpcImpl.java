package org.qiyu.user.provider.Rpc;

import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;
import org.qiyu.live.user.constants.UserTagsEnum;
import org.qiyu.live.user.interfaces.IUserTagRpc;
import org.qiyu.user.provider.service.IUserTagService;
@DubboService(interfaceClass = IUserTagRpc.class ,protocol = "dubbo")
public class UserTagRpcImpl implements IUserTagRpc {
@Resource
private IUserTagService userTagService;
    @Override
    public boolean setTag(Long userId, UserTagsEnum userTagsEnum) {
        return userTagService.setTag(userId,userTagsEnum);
    }

    @Override
    public boolean cancelTag(Long userId, UserTagsEnum userTagsEnum) {
        return userTagService.cancelTag(userId,userTagsEnum);
    }

    @Override
    public boolean containTag(Long userId, UserTagsEnum userTagsEnum) {
       return userTagService.containTag(userId,userTagsEnum);
    }
}
