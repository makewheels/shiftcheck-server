package com.eg.shiftcheck;

import com.alibaba.fastjson.JSONObject;
import com.eg.shiftcheck.bean.*;
import com.github.makewheels.HttpUtil;
import org.apache.commons.codec.language.DaitchMokotoffSoundex;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PushService {
    @Resource
    private AccessTokenService accessTokenService;

    private void sendSingleUser(String openId, String banName, String week, String time) {
        String accessToken = accessTokenService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + accessToken;
        Template template = new Template();
        template.setTouser(openId);
        template.setTemplate_id("ZoRFis7-_uUDKMponsvbN0nOsWIKqqt7bUUp0jucarI");
        template.setPage("index?clickId=" + UUID.randomUUID().toString().replace("-", ""));
        template.setMiniprogram_state("developer");
        Data data = new Data();

        Thing1 thing1 = new Thing1();
        thing1.setValue("");
        data.setThing1(thing1);

        Thing2 thing2 = new Thing2();
        thing2.setValue("");
        data.setThing2(thing2);


        Time3 time3 = new Time3();
        time3.setValue("");
        data.setTime3(time3);

        Thing4 thing4 = new Thing4();
        thing4.setValue("");
        data.setThing4(thing4);

        template.setData(data);
        HttpUtil.post(url, "");
    }

    public void pushToWechatMiniProgram(
            List<String> openIds, String banName, String week, String time) {
        for (String openId : openIds) {
            sendSingleUser(openId, banName, week, time);
        }

        /**
         * {
         *   "touser": "OPENID",
         *   "template_id": "ZoRFis7-_uUDKMponsvbN0nOsWIKqqt7bUUp0jucarI",
         *   "page": "index",
         *   "miniprogram_state":"developer",
         *   "lang":"zh_CN",
         *   "data": {
         *       "number01": {
         *           "value": "339208499"
         *       },
         *       "date01": {
         *           "value": "2015年01月05日"
         *       },
         *       "site01": {
         *           "value": "TIT创意园"
         *       } ,
         *       "site02": {
         *           "value": "广州市新港中路397号"
         *       }
         *   }
         * }
         * 订阅消息参数
         */
    }

}
