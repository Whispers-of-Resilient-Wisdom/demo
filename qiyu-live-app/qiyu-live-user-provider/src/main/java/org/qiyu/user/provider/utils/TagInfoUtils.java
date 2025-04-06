package org.qiyu.user.provider.utils;

public class TagInfoUtils {
    static final String tag_info_01="tag_info_01";
    static final String tag_info_02="tag_info_01";
    static final String TAG_INFO_03="tag_info_03";
    public static boolean containTag(Long tag, Long match){
        return tag != null && match != null && (tag&match)==match;

    }



}
