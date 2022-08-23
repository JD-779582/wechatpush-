package cn.cvzhanshi.wechatpush.config;

import cn.cvzhanshi.wechatpush.entity.Weather;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.client.RestTemplate;

import javax.xml.stream.Location;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cVzhanshi
 * @create 2022-08-04 22:02
 */
public class WeatherUtils {
    public static void main(String[] args) {
        System.out.println(getWeather());
    }
    public static Weather getWeather(){
        RestTemplate restTemplate = new RestTemplate();
        Map<String,String> map1 = new HashMap<String,String>();
//        Map<String,String> map2 = new HashMap<String,String>();
        map1.put("district_id","650103"); // 乌鲁木齐沙依巴克区行政代码
        map1.put("data_type","all");//这个是数据类型
        map1.put("ak","QcW0d7EINrnvlSIgmyFDbYNaW20I5a79");
        Weather weather = getWeather(restTemplate, map1);
        return weather;
    }

    public static Weather getWeather(String districtId, String ak){
        RestTemplate restTemplate = new RestTemplate();
        Map<String,String> map1 = new HashMap<String,String>();
        map1.put("district_id", districtId); // 乌鲁木齐沙依巴克区行政代码
        map1.put("data_type","all");//这个是数据类型
        map1.put("ak", ak);
        Weather weather = getWeather(restTemplate, map1);
        return weather;
    }

    private static Weather getWeather(RestTemplate restTemplate, Map<String, String> map1) {
        String res = restTemplate.getForObject(
                "https://api.map.baidu.com/weather/v1/?district_id={district_id}&data_type={data_type}&ak={ak}",
                String.class, map1);
        JSONObject json = JSONObject.parseObject(res);
        JSONArray forecasts = json.getJSONObject("result").getJSONArray("forecasts");
        JSONObject location = json.getJSONObject("result").getJSONObject("location");
        List<Weather> weathers = forecasts.toJavaList(Weather.class);
        JSONObject now = json.getJSONObject("result").getJSONObject("now");
        Weather weather = weathers.get(0);
        weather.setText_now(now.getString("text"));
        weather.setTemp(now.getString("temp"));
        weather.setWind_class(now.getString("wind_class"));
        weather.setWind_dir(now.getString("wind_dir"));
        weather.setFeels_like(now.getString("feels_like"));
        weather.setName(location.getString("name"));
        weather.setCity(location.getString("city"));
        return weather;
    }
}
