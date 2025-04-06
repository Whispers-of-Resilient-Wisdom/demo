package org.qiyu.live.id.generate.provider.service;

public interface IdBuilderService {
    /**
     * 生成有序 id
     *

     */
    Long getSeqId(Integer code);
    /**
     * 生成无序 id
     */
    Long getUnSeqId(Integer code);
}
