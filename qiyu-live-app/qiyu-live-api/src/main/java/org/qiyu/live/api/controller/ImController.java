package org.qiyu.live.api.controller;

import com.qiyu.live.common.interfaces.VO.WebResponseVO;
import jakarta.annotation.Resource;

import org.qiyu.live.api.service.ImService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**

 */
@RestController
@RequestMapping("/live/api/im")
public class ImController {

    @Resource
    private ImService imService;

    @PostMapping("/getImConfig")
    public WebResponseVO getImConfig() {
        return WebResponseVO.success(imService.getImConfig());
    }
}
