package com.eg.shiftcheckserver;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PushService {
    @Resource
    private AccessTokenService accessTokenService;

    public void pushToWechatMiniProgram(
            List<String> openIds, String banName, String week, String time) {
        String accessToken = accessTokenService.getAccessToken();

    }
}
