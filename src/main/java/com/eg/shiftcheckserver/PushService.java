package com.eg.shiftcheckserver;

import com.github.makewheels.HttpUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PushService {
    @Resource
    private AccessTokenService accessTokenService;

    public void pushToWechatMiniProgram(
            List<String> openIds, String banName, String week, String time) {
        String accessToken = accessTokenService.getAccessToken();
        Map<String, String> map = new HashMap<>();
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + accessToken;
        HttpUtil.post(url, map);
    }
}
