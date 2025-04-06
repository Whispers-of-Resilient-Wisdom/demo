package com.qiyu.live.common.interfaces.utils;

import org.springframework.lang.Nullable;

public class IsEmptyUtils {
    public static boolean isEmpty(@Nullable Object str) {
        return str == null || "".equals(str);
    }
}
