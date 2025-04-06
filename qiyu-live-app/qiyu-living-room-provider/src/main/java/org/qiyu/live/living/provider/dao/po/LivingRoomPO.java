package org.qiyu.live.living.provider.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
/**
 * 直播间持久化对象（PO）。
 * 对应数据库表 t_living_room，封装直播间的基本信息和状态。
 */
@TableName("t_living_room")
@Setter
@Getter
public class LivingRoomPO {

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
     * 直播间信息最后更新时间。
     */
    private Date updateTime;

    // Getters and Setters

    @Override
    public String toString() {
        return "LivingRoomPO{" +
                "id=" + id +
                ", anchorId=" + anchorId +
                ", type=" + type +
                ", roomName='" + roomName + '\'' +
                ", covertImg='" + covertImg + '\'' +
                ", status=" + status +
                ", watchNum=" + watchNum +
                ", goodNum=" + goodNum +
                ", startTime=" + startTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
