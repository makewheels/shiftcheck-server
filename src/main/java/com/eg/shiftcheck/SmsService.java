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

    private Client getClient(String accessKeyId, String accessKeySecret) throws Exception {
        if (client != null)
            return client;
        Config config = new Config()
                .setAccessKeyId(accessKeyId)
                .setAccessKeySecret(accessKeySecret);
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }

    public static void main(String[] args) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ban", "白班");
        jsonObject.put("date", "2021-05-03");
        jsonObject.put("temp", "大庆，18-22");
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers("18603672365")
                .setSignName("英语语法")
                .setTemplateCode("SMS_217406140")
                .setTemplateParam(jsonObject.toJSONString());
        // 复制代码运行请自行打印 API 的返回值
        Client client = new SmsService().getClient("", "");
        SendSmsResponse sendSmsResponse = client.sendSms(sendSmsRequest);
        System.out.println(sendSmsResponse);
    }
}
