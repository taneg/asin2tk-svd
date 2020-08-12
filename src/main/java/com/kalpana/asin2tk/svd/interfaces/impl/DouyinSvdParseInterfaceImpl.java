package com.kalpana.asin2tk.svd.interfaces.impl;

import java.util.HashMap;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.kalpana.asin2tk.svd.interfaces.SvdParseInterface;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 2tk
 * @version 1.0
 * @date 2020/8/11 16:57
 **/
@Service
@Slf4j
public class DouyinSvdParseInterfaceImpl implements SvdParseInterface {

    /**
     * 请求根路径
     */
    protected static final String BASE_URL = "https://www.iesdouyin.com/web/api/v2/aweme/iteminfo";
    /**
     * 模拟浏览器
     */
    protected static final String USER_AGENT = "Mozilla/5.0 (iPhone; CPU iPhone OS 12_1_4 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/16D57 Version/12.0 Safari/604.1";

    @Override
    public String getType() {
        return "douyin.com";
    }

    @Override
    public String parseUrl(String shareUrl) throws Exception {
        Connection con = Jsoup.connect(shareUrl);
        con.header("User-Agent", USER_AGENT);
        Connection.Response resp = con.method(Connection.Method.GET).execute();
        String jsonStr = Jsoup.connect(BASE_URL)
                .data("item_ids", getItemId(resp.url().toString()))
                .ignoreContentType(true)
                .execute()
                .body();
        JSONObject json = JSONObject.parseObject(jsonStr);
        String videoAddress = json.getJSONArray("item_list").getJSONObject(0).getJSONObject("video").getJSONObject("play_addr").getJSONArray("url_list").get(0).toString();
//        String title = json.getJSONArray("item_list").getJSONObject(0).getJSONObject("share_info").getString("share_title");
        videoAddress = videoAddress.replaceAll("playwm", "play");
        HashMap<String, String> headers = MapUtil.newHashMap();
        headers.put("User-Agent", USER_AGENT);
        return HttpUtil.createGet(videoAddress).addHeaders(headers).execute().header("Location");
    }

    private static String getItemId(String url) {
        int start = url.indexOf("/video/") + 7;
        int end = url.lastIndexOf("/");
        String itemId = url.substring(start, end);
        return itemId;
    }
}
