package com.github.makewheels.shiftcheck.service;

import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.sms.SmsClient;
import com.baidubce.services.sms.SmsClientConfiguration;
import com.baidubce.services.sms.model.SendMessageV3Request;
import com.baidubce.services.sms.model.SendMessageV3Response;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BaiduSmsService {
    private SmsClient client;

    private SmsClient getClient() {
        if (client == null) {
            String AccessKeyID = System.getenv("shiftcheck_server_baidu_sms_AccessKeyID");
            String AccessKeySecret = System.getenv("shiftcheck_server_baidu_sms_AccessKeySecret");
            SmsClientConfiguration config = new SmsClientConfiguration();
            config.setCredentials(new DefaultBceCredentials(AccessKeyID, AccessKeySecret));
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
    public SendMessageV3Response sendRemindSms(String phoneNumber, Map<String, String> contentVar) {
        SendMessageV3Request request = new SendMessageV3Request();
        request.setMobile(phoneNumber);
        request.setSignatureId("sms-sign-SHpajY29031");
        request.setTemplate("sms-tmpl-gZkbpO74507");
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
