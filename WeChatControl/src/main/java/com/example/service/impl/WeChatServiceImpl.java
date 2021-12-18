package com.example.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.service.WeChatService;
import com.example.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 微信业务类
 * @author huanliu
 */
@Service
public class WeChatServiceImpl implements WeChatService {

    @Value("${wechat.params.appid}")
    private String appid;

    @Value("${wechat.params.secret}")
    private String secret;
    /**
     * 先定义链接，在这里因为appID和secret还没有被注入，所以只能先声明，在调用的时候再去替换
     */
    private String getTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";
    private String createMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 默认的菜单栏,要注意java中的json字符串就是双引号，没法像js中那样随心所欲的写单引号或者不写
     */
    private String defaultMenu = "{\"button\":[{\"type\":\"click\",\"name\":\"今日歌曲\",\"key\":\"V1001_TODAY_MUSIC\"},{\"name\":\"菜单\",\"sub_button\":[{\"type\":\"view\",\"name\":\"搜索\",\"url\":\"http://www.baidu.com/\"}]}]}";

    @Override
    public boolean createMenu(String menuStr) {
        try{
            if (menuStr == null){
                menuStr = defaultMenu;
            }
            JSONObject menuJSON = JSON.parseObject(menuStr);
            String access_token = (String) redisTemplate.opsForValue().get("access_token");
            createMenuUrl = createMenuUrl.replaceAll("ACCESS_TOKEN", access_token);
            String res = HttpUtil.sendPost(createMenuUrl,menuStr);
            System.out.println(res);
            JSONObject resObj = JSON.parseObject(res);
            //当返回值为0的时候表示成功，其他表示失败
            if(resObj.getInteger("errcode") == 0){
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean flushAccessToken() {
        getTokenUrl = getTokenUrl.replaceAll("APPID",appid).replaceAll("SECRET",secret);
        String s = HttpUtil.sendGet(getTokenUrl, null);
        /**
         * 返回的样式
         * {"access_token":"40_zWYu9lK4VXnfKnyPC1LAM9jEj73Uj8Ma4Vm2T","expires_in":7200}
         * 将access_token解析出来后存到redis
         */
        JSONObject jObj = JSON.parseObject(s);
        redisTemplate.opsForValue().set("access_token",jObj.getString("access_token"));
        return true;
    }
}
