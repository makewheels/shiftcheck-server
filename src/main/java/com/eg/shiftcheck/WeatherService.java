package com.eg.shiftcheck;

import com.alibaba.fastjson.JSON;
import com.eg.shiftcheck.bean.weather.WeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class WeatherService {
    @Resource
    private RestTemplate restTemplate;

    /**
     * 根据城市名获取天气
     *
     * @param cityName 城市中文名
     * @return
     */
    public WeatherResponse getByCityName(String cityName) {
        WeatherResponse weatherResponse = restTemplate.getForObject(
                "https://www.tianqiapi.com/free/week?appid=37764671&appsecret=dd47qYbv&city=" + cityName,
                WeatherResponse.class);
        System.out.println("WeatherService.getByCityName");
        System.out.println(JSON.toJSONString(weatherResponse));
        return weatherResponse;
    }

}