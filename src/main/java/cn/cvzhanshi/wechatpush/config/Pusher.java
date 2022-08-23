package cn.cvzhanshi.wechatpush.config;


import cn.cvzhanshi.wechatpush.entity.Weather;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

import java.util.Calendar;
import java.util.Map;

/**
 * @author cVzhanshi
 * @create 2022-08-04 21:09
 */
public class Pusher {

    public static void main(String[] args) {
        push();
    }

    private static String appId = "wx95e6db5b80916fef";
    private static String secret = "5f0058f7439220b73ee2812f3e6c6fe9";


    public static void push() {
        //1，配置
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId(appId);
        wxStorage.setSecret(secret);
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        //2,推送消息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser("oUydF6HC3LAMhFO4R5rBmnNnZXvY")
                .templateId("to79e0jzGZUTleLxCMlPRCvsDz7W4UhKg4jhRoa87sU")
                .build();
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (hour < 9) {
            templateMessage.setTemplateId("to79e0jzGZUTleLxCMlPRCvsDz7W4UhKg4jhRoa87sU");
        } else {
            templateMessage.setTemplateId("ChFmirujCkLLYZXgns1YEmR6nMlsk1zVrfplkCfq0Tk");
        }
        //3,如果是正式版发送模版消息，这里需要配置你的信息
        Weather weather = WeatherUtils.getWeather();

        Map<String, String> map = CaiHongPiUtils.getEnsentence();
        templateMessage.addData(new WxMpTemplateData("riqi", weather.getDate() + "  " + weather.getWeek(), "#00BFFF"));
        templateMessage.addData(new WxMpTemplateData("tianqi", weather.getText_now(), "#00FFFF"));
        templateMessage.addData(new WxMpTemplateData("feels_like", weather.getFeels_like(), "#00FFFF"));
        templateMessage.addData(new WxMpTemplateData("low", weather.getLow() + "", "#173177"));
        templateMessage.addData(new WxMpTemplateData("temp", weather.getTemp() + "", "#EE212D"));
        templateMessage.addData(new WxMpTemplateData("high", weather.getHigh() + "", "#FF6347"));
        templateMessage.addData(new WxMpTemplateData("windclass", weather.getWind_class() + "", "#42B857"));
        templateMessage.addData(new WxMpTemplateData("winddir", weather.getWind_dir() + "", "#B95EA3"));
        templateMessage.addData(new WxMpTemplateData("city", weather.getCity() + "", "#B95EA3"));
        templateMessage.addData(new WxMpTemplateData("name", weather.getName() + "", "#B95EA3"));


        templateMessage.addData(new WxMpTemplateData("caihongpi", CaiHongPiUtils.getCaiHongPi(), "#FF69B4"));
        templateMessage.addData(new WxMpTemplateData("lianai", JiNianRiUtils.getLianAi() + "", "#FF1493"));
        templateMessage.addData(new WxMpTemplateData("shengri1", JiNianRiUtils.getBirthday_Liu() + "", "#FFA500"));
        templateMessage.addData(new WxMpTemplateData("shengri2", JiNianRiUtils.getBirthday_Wang() + "", "#FFA500"));
        templateMessage.addData(new WxMpTemplateData("en", map.get("en") + "", "#C71585"));
        templateMessage.addData(new WxMpTemplateData("zh", map.get("zh") + "", "#C71585"));
        String beizhu1 = "小王同志";
        String beizhu2 = "小刘同志";
        String beizhu3 = "";
        int temp = Integer.parseInt(weather.getTemp());
        if (5 < temp && temp < 17) {
            beizhu3 = "今天天气有点冷记得多穿衣服哟，别着凉了！";
        } else if (17 <= temp && temp < 25) {
            beizhu3 = "今天天气较为凉爽，但是需要穿上外套哟！！";
        } else {
            beizhu3 = "今天比较炎热，要注意防晒丫！！";
        }

        if (JiNianRiUtils.getLianAi() % 365 == 0) {
            beizhu2 = "今天是恋爱" + (JiNianRiUtils.getLianAi() / 365) + "周年纪念日！";
        }
        if (JiNianRiUtils.getBirthday_Liu() == 0) {
            beizhu2 = "今天是小刘生日，生日快乐呀！";
        }
        if (JiNianRiUtils.getBirthday_Wang() == 0) {
            beizhu1 = "今天是小王生日，生日快乐呀！";
        }
        templateMessage.addData(new WxMpTemplateData("beizhu2", beizhu2, "#C71585"));
        templateMessage.addData(new WxMpTemplateData("beizhu1", beizhu1, "#C71585"));
        templateMessage.addData(new WxMpTemplateData("beizhu3", beizhu3, "#ff0000"));
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (Exception e) {
            System.out.println("推送失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
