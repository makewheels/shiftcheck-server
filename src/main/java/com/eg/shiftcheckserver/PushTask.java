package com.eg.shiftcheckserver;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class PushTask {
    @Resource
    private PushService pushService;

    private final List<String> openIds
            = Arrays.asList(
            "o9K4b0QW0Yz2wosJeEIIk7QJo8Cg", "o9K4b0Y2CtwEzCndp3_snLGZfpuM");
    private final int targetBanzu = 2;

    @Scheduled(fixedRate = 5000)
    private void push() throws ParseException {
        //首先看现在是不是推送时间，先按照晚上19点来
        LocalDateTime now = LocalDateTime.now();
        int hour = now.getHour();
//        if (hour != 19)
//            return;

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
            if (period[i] == targetBanzu) {
                String banName = banList.getString(i);
                DayOfWeek dayOfWeek = now.getDayOfWeek();
                String week = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault());
                String time = end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                pushService.pushToWechatMiniProgram(openIds, banName, week, time);
            }
        }
    }
}
