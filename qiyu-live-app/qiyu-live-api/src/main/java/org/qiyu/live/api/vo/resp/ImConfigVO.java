package org.qiyu.live.api.vo.resp;

import lombok.Getter;
import lombok.Setter;

/**
 * 返回给客户端使用的IM地址和认证token
 */
@Setter
@Getter
public class ImConfigVO {

    /** IM认证token */
    private String token;

    /** WebSocket IM服务器地址 */
    private String wsImServerAddress;

    /** TCP IM服务器地址 */
    private String tcpImServerAddress;


}
