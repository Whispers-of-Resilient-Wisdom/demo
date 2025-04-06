package org.qiyu.live.living.provider.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.qiyu.live.living.provider.dao.po.LivingRoomPO;

/**
 * 直播间映射接口。
 * 继承自 MyBatis-Plus 的 BaseMapper，提供对 t_living_room 表的基本 CRUD 操作。
 * 通过 MyBatis-Plus，能够自动实现常见的增、删、改、查操作，减少手写 SQL 的需求。
 */
@Mapper
public interface LivingRoomMapper extends BaseMapper<LivingRoomPO> {

}

