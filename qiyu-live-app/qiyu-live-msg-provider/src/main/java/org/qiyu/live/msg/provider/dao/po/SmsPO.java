package org.qiyu.live.msg.provider.dao.po;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 记录短信相关信息
 *
 */
@Setter
@Getter
@TableName("t_sms")
public class SmsPO {

    @TableId(type = IdType.AUTO)
    private Long id;
    private Integer code;
    private String phone;
    private Date sendTime;
    private Date updateTime;


    @Override
    public String toString() {
        return "SmsPO{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", phone='" + phone + '\'' +
                ", sendTime=" + sendTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
