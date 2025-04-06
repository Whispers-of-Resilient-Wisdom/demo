package org.qiyu.user.provider.service;

import org.qiyu.live.user.constants.UserTagsEnum;

public interface IUserTagService {
    /**
     * 设置标签
     *

     */
    boolean setTag(Long userId, UserTagsEnum userTagsEnum);

    /**
     * 取消标签


     */
    boolean cancelTag(Long userId,UserTagsEnum userTagsEnum);

    /**
     * 是否包含某个标签
     *

     */
    boolean containTag(Long userId,UserTagsEnum userTagsEnum);
}
