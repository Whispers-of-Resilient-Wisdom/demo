package org.qiyu.live.bank.dto;


import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 *

 */
@Setter
@Getter
public class PayProductDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 8737032096039095954L;
    private Long id;
    private String name;
    private Integer price;
    private String extra;
    private Integer type;
    private Integer validStatus;
    private Date createTime;
    private Date updateTime;

    @Override
    public String toString() {
        return "PayProductDTO{" +
                "id=" + id +
                ", price=" + price +
                ", extra='" + extra + '\'' +
                ", type=" + type +
                ", validStatus=" + validStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
