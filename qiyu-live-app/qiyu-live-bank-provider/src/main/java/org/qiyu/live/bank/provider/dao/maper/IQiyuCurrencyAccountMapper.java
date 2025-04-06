package org.qiyu.live.bank.provider.dao.maper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.qiyu.live.bank.provider.dao.po.QiyuCurrencyAccountPO;

/**
 * 虚拟币账户数据访问层接口。
 * <p>
 * 定义了操作虚拟币账户的特定数据库方法。
 * 主要用于实现虚拟币账户的余额查询、增加和减少操作。
 * </p>
 *
 */
@Mapper
public interface IQiyuCurrencyAccountMapper extends BaseMapper<QiyuCurrencyAccountPO> {

    /**
     * 增加指定用户的虚拟币余额。
     *
     * @param userId 用户ID
     * @param num 要增加的虚拟币数量
     */
    @Update("update qiyu_live_bank.t_qiyu_currency_account set current_balance = current_balance + #{num} where user_id = #{userId}")
    void incr(@Param("userId") long userId, @Param("num") int num);

    /**
     * 查询指定用户的虚拟币余额。
     *
     * @param userId 用户ID
     * @return 用户当前的虚拟币余额
     */
    @Select("select current_balance from qiyu_live_bank.t_qiyu_currency_account where user_id=#{userId} and status = 1 limit 1")
    Integer queryBalance(@Param("userId") long userId);

    /**
     * 减少指定用户的虚拟币余额。
     *
     * @param userId 用户ID
     * @param num 要减少的虚拟币数量
     */
    @Update("update qiyu_live_bank.t_qiyu_currency_account set current_balance = current_balance - #{num} where user_id = #{userId}")
    void decr(@Param("userId") long userId, @Param("num") int num);
}
