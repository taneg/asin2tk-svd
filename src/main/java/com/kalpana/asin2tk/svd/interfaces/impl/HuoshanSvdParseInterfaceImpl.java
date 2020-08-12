package com.kalpana.asin2tk.svd.interfaces.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.CharsetUtil;
import com.alibaba.fastjson.JSONObject;
import com.kalpana.asin2tk.svd.interfaces.SvdParseInterface;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.HashMap;

/**
 * @author 2tk
 * @version 1.0
 * @date 2020/8/13 0:47
 **/
@Service
@Slf4j
public class HuoshanSvdParseInterfaceImpl implements SvdParseInterface {

    /**
     * 请求根路径
     */
    protected static final String BASE_URL_1 = "https://share.huoshan.com/api/item/info";
    protected static final String BASE_URL_2 = "https://api-hl.huoshan.com/hotsoon/item/video/_playback/";
    /**
     * 模拟浏览器
     */
    protected static final String USER_AGENT = "Mozilla/5.0 (iPhone; CPU iPhone OS 12_1_4 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/16D57 Version/12.0 Safari/604.1";

    @Override
    public String getType() {
        return "huoshan.com";
    }

    @Override
    public String parseUrl(String shareUrl) throws Exception {
        Connection con = Jsoup.connect(shareUrl);
        con.header("User-Agent", USER_AGENT);
        Connection.Response resp = con.method(Connection.Method.GET).execute();
        String jsonStr = Jsoup.connect(BASE_URL_1)
                .data("item_id", getItemId(resp.url().toString()))
                .ignoreContentType(true)
                .execute()
                .body();
        JSONObject json = JSONObject.parseObject(jsonStr);
        String videoAddress = json.getJSONObject("data").getJSONObject("item_info").getString("url");
        HashMap<String, String> headers = MapUtil.newHashMap();
        headers.put("User-Agent", USER_AGENT);
        UrlBuilder builder = UrlBuilder.ofHttp(videoAddress, CharsetUtil.CHARSET_UTF_8);
        return UrlBuilder.ofHttp(BASE_URL_2, CharsetUtil.CHARSET_UTF_8)
                .addQuery("video_id", String.valueOf(builder.getQuery().get("video_id")))
                .build();
    }

    private static String getItemId(String url) throws MalformedURLException {
        UrlBuilder builder = UrlBuilder.ofHttp(url, CharsetUtil.CHARSET_UTF_8);
        return String.valueOf(builder.getQuery().get("item_id"));
    }
}
