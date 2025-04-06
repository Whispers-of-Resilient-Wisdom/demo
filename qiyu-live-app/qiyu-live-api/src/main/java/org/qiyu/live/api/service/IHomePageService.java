package org.qiyu.live.api.service;

import org.qiyu.live.api.vo.HomePageVO;


/**

 */
public interface IHomePageService {


    /**
     * 初始化页面获取的信息
     *
     * @param userId 用户Id
     * @return 首页展示信息对象（VO）
     */
    HomePageVO initPage(Long userId);


}
