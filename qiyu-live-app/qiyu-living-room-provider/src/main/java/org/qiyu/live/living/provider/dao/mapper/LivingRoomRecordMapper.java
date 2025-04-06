package org.qiyu.live.living.provider.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.qiyu.live.living.provider.dao.po.LivingRoomRecordPO;

/**
 * 直播间记录映射接口。
 * 继承自 MyBatis-Plus 的 BaseMapper，提供对 t_living_room_record 表的基本 CRUD 操作。
 * 通过 MyBatis-Plus，可以省去大量的 SQL 编写工作，直接使用默认的 CRUD 方法。
 */
@Mapper
public interface LivingRoomRecordMapper extends BaseMapper<LivingRoomRecordPO> {

}

