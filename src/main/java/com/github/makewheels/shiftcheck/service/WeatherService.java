package com.github.makewheels.shiftcheck.service;

import com.alibaba.fastjson.JSON;
import com.github.makewheels.shiftcheck.bean.weather.WeatherResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class WeatherService {
    @Resource
    private RestTemplate restTemplate;

    public RestTemplate getRestTemplate() {
        if (restTemplate == null) {
            restTemplate = new RestTemplate();
        }
        return restTemplate;
    }

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
