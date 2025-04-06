package org.qiyu.live.id.generate.interfaces.Enum;

import lombok.Getter;

/**
 * IdTypeEnum
 * <p>
 * 枚举类，用于定义不同的 ID 生成策略。当前只包含了一个用户 ID 生成策略的枚举项。
 * </p>
 */
@Getter
public enum IdTypeEnum {

    /**
     * 用户 ID 生成策略
     * 这里定义了一个枚举项表示用户 ID 的生成策略
     */
    USER_ID(1, "用户id生成策略");

    // 枚举项的 code，通常用来表示 ID 类型的标识
    int code;

    // 枚举项的描述，通常用于描述 ID 类型的具体含义
    String desc;

    /**
     * 构造方法，设置枚举项的 code 和 desc
     *
     * @param code 枚举项的代码，通常是唯一的标识符
     * @param desc 枚举项的描述，解释该枚举项的含义
     */
    IdTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
