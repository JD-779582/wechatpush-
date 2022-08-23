package cn.cvzhanshi.wechatpush.config;

import cn.cvzhanshi.wechatpush.entity.Weather;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cVzhanshi
 * @create 2022-08-04 22:02
 */
public class WeatherUtils1 {
    public static void main(String[] args) {
        System.out.println(getWeather1());
    }
    public static Weather getWeather1(){
        RestTemplate restTemplate1 = new RestTemplate();
        Map<String,String> map2 = new HashMap<String,String>();
//        Map<String,String> map2 = new HashMap<String,String>();
        map2.put("district_id","652301"); // 昌吉市行政代码
        map2.put("data_type","all");//这个是数据类型
        map2.put("ak","QcW0d7EINrnvlSIgmyFDbYNaW20I5a79");
        String res = restTemplate1.getForObject(
                "https://api.map.baidu.com/weather/v1/?district_id={district_id}&data_type={data_type}&ak={ak}",
                String.class,map2);
        JSONObject json = JSONObject.parseObject(res);
        JSONArray forecasts = json.getJSONObject("result").getJSONArray("forecasts");
        JSONObject location = json.getJSONObject("result").getJSONObject("location");
        List<Weather> weathers = forecasts.toJavaList(Weather.class);
        JSONObject now = json.getJSONObject("result").getJSONObject("now");
        Weather weather1 = weathers.get(0);
        weather1.setText_now(now.getString("text"));
        weather1.setTemp(now.getString("temp"));
        weather1.setWind_class(now.getString("wind_class"));
        weather1.setWind_dir(now.getString("wind_dir"));
        weather1.setFeels_like(now.getString("feels_like"));
        weather1.setName(location.getString("name"));
        weather1.setCity(location.getString("city"));
        return weather1;
    }
}
