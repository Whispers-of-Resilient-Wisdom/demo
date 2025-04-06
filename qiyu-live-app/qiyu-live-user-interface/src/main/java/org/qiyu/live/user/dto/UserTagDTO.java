package org.qiyu.live.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class UserTagDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7287751480665429862L;
    private Long userId;
    private Long tagInfo01;
    private Long tagInfo02;
    private Long tagInfo03;
    private Date createTime;
    private Date updateTime;

    @Override
    public String toString() {
        return "UserTagDTO{" +
                "createTime=" + createTime +
                ", userId=" + userId +
                ", tagInfo01=" + tagInfo01 +
                ", tagInfo02=" + tagInfo02 +
                ", tagInfo03=" + tagInfo03 +
                ", updateTime=" + updateTime +
                '}';
    }
}
