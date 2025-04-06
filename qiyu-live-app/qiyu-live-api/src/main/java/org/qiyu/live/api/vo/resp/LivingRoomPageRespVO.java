package org.qiyu.live.api.vo.resp;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 直播房间分页响应数据对象（VO）
 */
@Setter
@Getter
public class LivingRoomPageRespVO {

    /** 房间信息列表 */
    private List<LivingRoomRespVO> list;

    /** 是否还有下一页 */
    private boolean hasNext;


    @Override
    public String toString() {
        return "LivingRoomPageRespVO{" +
                "list=" + list +
                ", hasNext=" + hasNext +
                '}';
    }
}
