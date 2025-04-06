package org.qiyu.user.provider.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**

 */
@TableName("t_user")
@Setter
@Getter
public class UserPo {

    @TableId(type = IdType.INPUT)
    private Long userId;
    private String nickName;
    private String trueName;
    private String avatar;
    private Integer sex;
    private Integer workCity;
    private Integer bornCity;
    private Date bornDate;
    private Date createTime;
    private Date updateTime;

    @Override
    public String toString() {
        return "UserPO{" +
                "userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", trueName='" + trueName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", sex=" + sex +
                ", workCity=" + workCity +
                ", bornCity=" + bornCity +
                ", bornDate=" + bornDate +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}