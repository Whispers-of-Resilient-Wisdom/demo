package org.qiyu.live.living.provider.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 直播间记录持久化对象（PO）。
 * 对应数据库表 t_living_room_record，记录直播间的历史信息和状态。
 */
@TableName("t_living_room_record")
@Setter
@Getter
public class LivingRoomRecordPO {

    /**
     * 主键 ID，自动递增。
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 主播 ID，关联用户表。
     */
    private Long anchorId;

    /**
     * 直播间类型，可能的值如娱乐、游戏、教育等。
     */
    private Integer type;

    /**
     * 直播间名称。
     */
    private String roomName;

    /**
     * 直播间封面图片链接。
     */
    private String covertImg;

    /**
     * 直播间状态：
     * 0 - 未开始，1 - 直播中，2 - 已结束。
     */
    private Integer status;

    /**
     * 观看人数统计。
     */
    private Integer watchNum;

    /**
     * 点赞数统计。
     */
    private Integer goodNum;

    /**
     * 直播开始时间。
     */
    private Date startTime;

    /**
     * 直播结束时间。
     */
    private Date endTime;

    /**
     * 直播记录信息最后更新时间。
     */
    private Date updateTime;



    @Override
    public String toString() {
        return "LivingRoomRecordPO{" +
                "id=" + id +
                ", anchorId=" + anchorId +
                ", type=" + type +
                ", roomName='" + roomName + '\'' +
                ", covertImg='" + covertImg + '\'' +
                ", status=" + status +
                ", watchNum=" + watchNum +
                ", goodNum=" + goodNum +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
