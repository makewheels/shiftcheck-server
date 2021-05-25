package com.eg.shiftcheck;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eg.shiftcheck.bean.*;
import com.github.makewheels.HttpUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class PushService {
    @Resource
    private AccessTokenService accessTokenService;

    /**
     * {
     * "touser": "OPENID",
     * "template_id": "ZoRFis7-_uUDKMponsvbN0nOsWIKqqt7bUUp0jucarI",
     * "page": "index",
     * "miniprogram_state":"developer",
     * "lang":"zh_CN",
     * "data": {
     * "number01": {
     * "value": "339208499"
     * },
     * "date01": {
     * "value": "2015年01月05日"
     * },
     * "site01": {
     * "value": "TIT创意园"
     * } ,
     * "site02": {
     * "value": "广州市新港中路397号"
     * }
     * }
     * }
     * 订阅消息参数
     */
    private String sendSingleUser(String openId, String banName, String week, String time) {
        String accessToken = accessTokenService.getAccessToken();
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + accessToken;
        Template template = new Template();
        template.setTouser(openId);
        template.setTemplate_id("ZoRFis7-_uUDKMponsvbN0nOsWIKqqt7bUUp0jucarI");
        String clickId = UUID.randomUUID().toString().replace("-", "");
        //TODO 保存clickId到数据库
        template.setPage("pages/index/index?clickId=" + clickId);
        template.setMiniprogram_state("developer");
        Data data = new Data();

        Thing1 thing1 = new Thing1();
        thing1.setValue(banName);
        data.setThing1(thing1);

        Thing2 thing2 = new Thing2();
        thing2.setValue(week);
        data.setThing2(thing2);

        Time3 time3 = new Time3();
        time3.setValue(time);
        data.setTime3(time3);

        Thing4 thing4 = new Thing4();
        thing4.setValue("晴 16-28°C");
        data.setThing4(thing4);

        template.setData(data);
        return HttpUtil.post(url, JSON.toJSONString(template));
    }

    public void pushToWechatMiniProgram(
            List<String> openIds, String banName, String week, String time) {
        for (String openId : openIds) {
            String json = sendSingleUser(openId, banName, week, time);
            JSONObject result = JSONObject.parseObject(json);
            int errcode = result.getIntValue("errcode");
            System.out.println("errcode = " + errcode);
        }
    }

}
