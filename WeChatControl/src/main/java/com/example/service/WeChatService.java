package com.example.service;

import java.util.Map;

/**
 * @author huanliu
 */
public interface WeChatService {

    /**
     * 创建微信菜单
     * @param menuStr 创建菜单格式的字符串
     * @return
     */
    boolean createMenu(String menuStr);

    /**
     * 刷新access_token
     * @return
     */
    boolean flushAccessToken();
}
