package org.qiyu.live.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
@Setter
@Getter
public class UserDTO implements Serializable {
@Serial
private static final long serialVersionUID = 1L;


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
