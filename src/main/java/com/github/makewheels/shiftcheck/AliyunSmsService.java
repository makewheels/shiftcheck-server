package com.github.makewheels.shiftcheck;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import org.springframework.stereotype.Service;

@Service
public class AliyunSmsService {
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

    /**
     * 发送提醒短信
     */
    public SendSmsResponse sendRemindSms(String phoneNumber, String templateParamJson) {
        SendSmsRequest request = new SendSmsRequest()
                .setPhoneNumbers(phoneNumber)
                .setSignName("英语语法")
                .setTemplateCode("SMS_217406140")
                .setTemplateParam(templateParamJson);
        Client client = new AliyunSmsService().getClient();
        try {
            return client.sendSms(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ban", "白班");
        jsonObject.put("date", "2021-05-03");
        jsonObject.put("temp", "大庆，18-22");
        AliyunSmsService aliyunSmsService = new AliyunSmsService();
        SendSmsResponse sendSmsResponse = aliyunSmsService.sendRemindSms(
                "15527175535", jsonObject.toJSONString());
        System.out.println(JSONObject.toJSONString(sendSmsResponse));

    }
}
