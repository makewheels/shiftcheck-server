package com.eg.shiftcheckserver;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class PushTask {

    @Scheduled(fixedRate = 5000)
    private void push() {
        String ruleJson = "{\"type\":\"1\",\"showName\":\"五班三倒\",\"idName\":\"wbsd-worker\",\"startDate\":\"2016-06-07\",\"restName\":\"休息\",\"banzuList\":[\"一班\",\"二班\",\"三班\",\"四班\",\"五班\"],\"banList\":[\"后夜（零点）\",\"白班\",\"前夜\"],\"periodList\":[\"1,3,5\",\"2,4,1\",\"3,5,2\",\"4,1,3\",\"5,2,4\"],\"workerPushTime\":[{\"day\":1,\"hour\":19},{\"day\":1,\"hour\":19},{\"day\":1,\"hour\":19}]}";
        JSONObject rule = JSONObject.parseObject(ruleJson);
        JSONArray periodList = rule.getJSONArray("periodList");
        System.out.println(periodList);
    }
}
