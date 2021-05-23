package com.eg.shiftcheck;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    private Client client;

    private Client getClient() {
        if (client == null) {
            String accessKeyId = System.getenv("shiftcheck_server_aliyun_sms_accessKeyId");
            String accessKeySecret = System.getenv("shiftcheck_server_aliyun_sms_accessKeySecret");
            Config config = new Config()
                    .setAccessKeyId(accessKeyId)
                    .setAccessKeySecret(accessKeySecret);
            config.endpoint = "dysmsapi.aliyuncs.com";
            try {
                client = new Client(config);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return client;
    }

    public static void main(String[] args) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ban", "白班");
        jsonObject.put("date", "2021-05-03");
        jsonObject.put("temp", "大庆，18-22");
        SendSmsRequest request = new SendSmsRequest()
                .setPhoneNumbers("15527175535")
                .setSignName("英语语法")
                .setTemplateCode("SMS_217406140")
                .setTemplateParam(jsonObject.toJSONString());
        Client client = new SmsService().getClient();
        SendSmsResponse response = client.sendSms(request);
        System.out.println(JSONObject.toJSONString(response));
    }
}
