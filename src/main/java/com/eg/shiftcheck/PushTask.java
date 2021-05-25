package com.eg.shiftcheck;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.eg.shiftcheck.bean.weather.Data;
import com.eg.shiftcheck.bean.weather.WeatherResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Component
public class PushTask {
    @Resource
    private PushService pushService;
    @Resource
    private WeatherService weatherService;

    private final List<String> openIds = Arrays.asList(
            "o9K4b0QW0Yz2wosJeEIIk7QJo8Cg"
            ,
            "o9K4b0Y2CtwEzCndp3_snLGZfpuM",
            "o9K4b0RYr0Fye0EzSmOMv3Nv2sjc"
    );

    private final List<String> phoneNumbers = Arrays.asList(
            "15527175535",
            "13351190738",
            "13351181909"
    );

    private final int targetBanzu = 2;

    @Scheduled(fixedRate = 1000 * 60 * 60)
    private void push() {
        //首先看现在是不是推送时间，先按照晚上19点来
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
        if (hour != 19) {
            System.out.println("现在不是19点，跳过: " + LocalDateTime.now().toString());
            return;
        }

        String ruleJson = "{\"type\":\"1\",\"showName\":\"五班三倒\",\"idName\":\"wbsd-work" +
                "er\",\"startDate\":\"2016-06-07\",\"restName\":\"休息\",\"banzuList\":[\"一班" +
                "\",\"二班\",\"三班\",\"四班\",\"五班\"],\"banList\":[\"后夜（零点）\",\"白班\"" +
                ",\"前夜\"],\"periodList\":[\"1,3,5\",\"2,4,1\",\"3,5,2\",\"4,1,3\",\"5,2,4" +
                "\"],\"workerPushTime\":[{\"day\":1,\"hour\":19},{\"day\":1,\"hour\":19},{" +
                "\"day\":1,\"hour\":19}]}";
        JSONObject rule = JSONObject.parseObject(ruleJson);
        JSONArray periodList = rule.getJSONArray("periodList");
        JSONArray banList = rule.getJSONArray("banList");
        int size = periodList.size();

        //计算日期相差天数
        LocalDate start = LocalDate.parse(rule.getString("startDate"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate end = LocalDate.now().plusDays(1);
        int days = (int) ChronoUnit.DAYS.between(start, end);

        //求余拿到索引
        int index = days % size;
        //拿到明天一天都是哪个班组上班
        String[] split = periodList.getString(index).split(",");
        int[] period = new int[split.length];
        for (int i = 0; i < split.length; i++)
            period[i] = Integer.parseInt(split[i]);

        //解析明天的班组，看有没有我们 [2, 4, 1]
        for (int i = 0; i < period.length; i++) {
            if (period[i] != targetBanzu)
                continue;
            //过滤到我们要的目标班组
            String banName = banList.getString(i);
            DayOfWeek dayOfWeek = end.getDayOfWeek();
            //显示中文的星期几
            String week = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.SIMPLIFIED_CHINESE);
            String time = end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            //获取天气信息
            WeatherResponse weatherResponse = weatherService.getByCityName("大庆");
            Data data = weatherResponse.getData().get(1);
            String weather = data.getWea() + " " + data.getTem_night() + "-" + data.getTem_day();
            //TODO 度在，短信和小程序推送，统一
            pushService.pushToWechatMiniProgram(openIds, banName, week, time, weather + "°C");
            for (String phoneNumber : phoneNumbers) {
                pushService.sendRemindSms(phoneNumber, banName, time + " " + week, weather);
            }
        }
    }
}
