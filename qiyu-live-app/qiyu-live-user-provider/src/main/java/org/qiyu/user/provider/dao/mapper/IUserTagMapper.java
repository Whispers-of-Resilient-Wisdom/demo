package org.qiyu.user.provider.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.qiyu.user.provider.dao.po.UserTagPO;

@Mapper
public interface IUserTagMapper extends BaseMapper<UserTagPO> {

    /**
     * 使用或的思路来设置标签，只能允许第一次设置成功
     *

     */
    @Update("update qiyu_live_user.t_user_tag set ${fieldName}=${fieldName} | #{tag} where user_id=#{userId} and ${fieldName} & #{tag}=0")
    int setTag(Long userId, String fieldName, long tag);

    /**
     * 使用先取反在与的思路来取消标签，只能允许第一次删除成功
     *
     */
    @Update("update qiyu_live_user.t_user_tag set ${fieldName}=${fieldName} &~ #{tag} where user_id=#{userId} and ${fieldName} & #{tag}=#{tag}")
    int cancelTag(Long userId, String fieldName, long tag);
}

