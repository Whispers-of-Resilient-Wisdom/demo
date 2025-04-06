package org.qiyu.live.user.dto;


import lombok.Getter;
import lombok.Setter;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
@Getter
@Setter
public class UserPhoneDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4502843195713255060L;

    private Long id;
    private Long userId;
    private String phone;
    private Integer status;
    private Date createTime;
    private Date updateTime;

    @Override
    public String toString() {
        return "UserPhoneDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
