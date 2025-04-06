package org.qiyu.user.provider.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@TableName("t_user_tag")
@Setter
@Getter
public class UserTagPO {

    @TableId(type = IdType.INPUT)
    private Long userId;
    @TableField(value = "tag_info_01")
    private Long tagInfo01;
    @TableField(value = "tag_info_02")
    private Long tagInfo02;
    @TableField(value = "tag_info_03")
    private Long tagInfo03;
    private Date createTime;
    private Date updateTime;

    @Override
    public String toString() {
        return "UserTagPO{" +
                "userId=" + userId +
                ", tagInfo01=" + tagInfo01 +
                ", tagInfo02=" + tagInfo02 +
                ", tagInfo03=" + tagInfo03 +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
