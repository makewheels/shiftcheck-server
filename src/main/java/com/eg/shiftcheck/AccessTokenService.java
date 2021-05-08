package com.eg.shiftcheck;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.makewheels.HttpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenService {
    @Value("${appid}")
    private String appid;

    private long expireTime;
    private String accessToken;

    public String getAccessToken() {
        //如果没有，重新获取
        if (accessToken == null)
            requestForAccessToken();
        //如果过期，重新获取
        if (System.currentTimeMillis() >= expireTime)
            requestForAccessToken();
        return accessToken;
    }

    public void requestForAccessToken() {
        String json = HttpUtil.get("https://api.weixin.qq.com/cgi-bin/token?"
                + "grant_type=client_credential&appid=" + appid
                + "&secret= ");
        JSONObject jsonObject = JSON.parseObject(json);
        expireTime = System.currentTimeMillis() + jsonObject.getIntValue("expires_in") * 1000L;
        accessToken = jsonObject.getString("access_token");
    }
}
