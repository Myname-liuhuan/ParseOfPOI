package com.example.weixin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.utils.HttpUtil;

/**
 * 微信公众号操作类
 *
 * @author huanliu
 */
public class WxOperation {

    private static String APPID;
    private static String SECRET;

    private String getTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";
    private String createMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";


    public WxOperation(String appid, String secret){
        APPID = appid;
        SECRET = secret;
    }

    /**
     * 微信创建菜单
     * @param menuStr
     * @param access_token
     * @return
     */
    public boolean createMenu(String menuStr, String access_token) {
        try{
            if (menuStr == null || access_token == null){
                return false;
            }
            String localUrl = createMenuUrl.replaceAll("ACCESS_TOKEN", access_token);
            String res = HttpUtil.sendPost(localUrl,menuStr);
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


    /**
     * 刷新token并返回
     * 返回的样式
     * {"access_token":"40_zWYu9lK4VnfKnyPC1LAM9jEj73Uj8Ma4Vm2T","expires_in":7200}
     * @return
     */
    public String flushAccessToken() {
        getTokenUrl = getTokenUrl.replaceAll("APPID",APPID).replaceAll("SECRET",SECRET);
        String s = HttpUtil.sendGet(getTokenUrl, null);
        return s;
    }


}
