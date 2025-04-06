package org.qiyu.user.provider.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.qiyu.user.provider.dao.po.UserPhonePO;

@Mapper
public interface IUserPhoneMapper extends BaseMapper<UserPhonePO> {
}
