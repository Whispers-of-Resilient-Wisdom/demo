package org.qiyu.live.msg.provider.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 发送消息的内容
 *

 */
@Setter
@Getter
public class MessageDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -8982006120358366161L;
    private Long userId;

    private Integer roomId;
    //发送人名称
    private String senderName;
    //发送人头像
    private String senderAvtar;
    /**
     * 消息类型
     */
    private Integer type;
    /**
     * 消息内容
     */
    private String content;
    private Date createTime;
    private Date updateTime;


    @Override
    public String toString() {
        return "MessageDTO{" +
                "userId=" + userId +
                ", roomId=" + roomId +
                ", senderAvtar='" + senderAvtar + '\'' +
                ", type=" + type +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }


}
