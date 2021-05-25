package com.eg.shiftcheck.bean.weather;

import java.util.List;

@lombok.Data
public class WeatherResponse {
    private String cityid;
    private String city;
    private String update_time;
    private List<Data> data;
}
