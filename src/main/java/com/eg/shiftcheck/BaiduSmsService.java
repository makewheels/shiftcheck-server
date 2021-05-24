package com.eg.shiftcheck;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.sms.SmsClient;
import com.baidubce.services.sms.SmsClientConfiguration;
import com.baidubce.services.sms.model.SendMessageV3Request;
import com.baidubce.services.sms.model.SendMessageV3Response;
import org.bouncycastle.pqc.math.linearalgebra.RandUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class BaiduSmsService {
    private final String ACCESS_KEY_ID = "5ab8934a11de44739b6a201d2e59e806";
    private final String SECRET_ACCESS_KEY = "06e72cddf15243179812d21eeec89c5e";

    private SmsClient client;

    private SmsClient getClient() {
        if (client == null) {
            SmsClientConfiguration config = new SmsClientConfiguration();
            config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY));
            config.setEndpoint("http://smsv3.bj.baidubce.com");
            client = new SmsClient(config);
        }
        return client;
    }

    /**
     * 发送提醒短信
     *
     * @param phoneNumber
     * @param contentVar
     * @return
     */
    private SendMessageV3Response sendRemindSms(String phoneNumber, Map<String, String> contentVar) {
        SendMessageV3Request request = new SendMessageV3Request();
        request.setMobile(phoneNumber);
        request.setSignatureId("sms-sign-QeEHQe10478");
        request.setTemplate("sms-tmpl-MuhSyL98050");
        request.setContentVar(contentVar);
        return getClient().sendMessage(request);
    }

    public static void main(String[] args) {
        BaiduSmsService baiduSmsService = new BaiduSmsService();
        Map<String, String> map = new HashMap<>();
        map.put("ban", "白班");
        map.put("date", "2021-05-03");
        map.put("temp", "大庆，18-22");
        SendMessageV3Response sendMessageV3Response = baiduSmsService.sendRemindSms("15527175535", map);
        System.out.println(sendMessageV3Response);
    }
}
