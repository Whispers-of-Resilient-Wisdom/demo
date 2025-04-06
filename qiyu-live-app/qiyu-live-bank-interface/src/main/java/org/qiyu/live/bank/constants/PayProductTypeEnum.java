package org.qiyu.live.bank.constants;

import lombok.Getter;
import lombok.Setter;

/**

 */

@Getter
public enum PayProductTypeEnum {

    QIYU_COIN(0,"直播间充值-旗鱼虚拟币产品");

    PayProductTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    Integer code;
    String desc;

}
