package org.qiyu.live.id.generate.provider.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.qiyu.live.id.generate.provider.dao.po.IdBuilderPO;

import java.util.List;
/**
 * IdBuilderMapper 接口
 * <p>
 * 该接口用于与数据库进行交互，执行与 ID 生成相关的操作。
 * 继承自 `BaseMapper<IdBuilderPO>`，因此可以自动具备基本的 CRUD 操作。
 * </p>
 */
@Mapper
public interface IdBuilderMapper extends BaseMapper<IdBuilderPO> {

    /**
     * 更新 ID 生成配置的相关字段。
     * <p>
     * 该方法会根据指定的 `id` 和 `version` 更新数据库中的 `next_threshold`、`current_start` 和 `version` 字段。
     * </p>
     *
     * @param id      要更新的 ID 配置的 ID
     * @param version 当前版本号，用于确保数据一致性
     * @return 更新操作的影响行数
     */
    @Update("update qiyu_live_common.t_id_generate_config set next_threshold=next_threshold+step ,current_start=current_start+step,version=version+1 " +
            "where id=#{id} and version=#{version}")
    int updateIdAndVersionCount(@Param("id") int id, @Param("version") int version);

    /**
     * 查询所有 ID 生成配置记录。
     * <p>
     * 该方法返回 `qiyu_live_common.t_id_generate_config` 表中的所有记录。
     * </p>
     *
     * @return 包含所有 ID 生成配置的列表
     */
    @Select("select * from qiyu_live_common.t_id_generate_config")
    List<IdBuilderPO> selectAll();
}
