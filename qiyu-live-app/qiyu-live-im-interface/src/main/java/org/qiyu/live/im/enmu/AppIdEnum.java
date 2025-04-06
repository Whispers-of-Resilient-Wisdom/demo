package org.qiyu.live.im.enmu;

import lombok.Getter;
import lombok.Setter;

/**

 */
@Getter
public enum AppIdEnum {

    QIYU_LIVE_BIZ(10001,"旗鱼直播业务");

    int code;
    String desc;

    AppIdEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }




}
