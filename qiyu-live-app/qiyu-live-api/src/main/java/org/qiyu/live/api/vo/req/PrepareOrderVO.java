package org.qiyu.live.api.vo.req;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PrepareOrderVO {

    private Long userId;
    private Integer roomId;


    @Override
    public String toString() {
        return "PrepareOrderVO{" +
                "roomId=" + roomId +
                ", userId=" + userId +
                '}';
    }

}
