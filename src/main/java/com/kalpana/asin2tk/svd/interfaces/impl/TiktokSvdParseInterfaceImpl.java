package com.kalpana.asin2tk.svd.interfaces.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
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
public class TiktokSvdParseInterfaceImpl implements SvdParseInterface {

    /**
     * 请求根路径
     */
    protected static final String BASE_URL = "https://ssstiktok.io";
    /**
     * 模拟浏览器
     */
    protected static final String USER_AGENT = "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Mobile Safari/537.36";

    @Override
    public String getType() {
        return "tiktok.com";
    }

    @Override
    public String parseUrl(String shareUrl) throws Exception {
        HashMap<String, String> headers = MapUtil.newHashMap();
        headers.put("User-Agent", USER_AGENT);
        String body = HttpUtil.createGet(BASE_URL).addHeaders(headers).execute().body();
        Document doc = Jsoup.parse(body);
        Elements formElement = doc.select("form[class=pure-form pure-g hide-after-request]");
        String postUrl = formElement.attr("data-hx-post");
        Elements tokenElment = formElement.select("input[id=token]");
        Elements localeElment = formElement.select("input[id=locale]");
        String token = tokenElment.attr("value");
        String locale = localeElment.attr("value");
        log.info("pre response >>>> postUrl = [{}], token = [{}], locale = [{}]", postUrl, token, locale);
        Connection con = Jsoup.connect(BASE_URL.concat(postUrl))
                .data("id", shareUrl)
                .data("token", token)
                .data("locale", locale)
                .method(Connection.Method.POST);
        con.header("User-Agent", USER_AGENT);
        Connection.Response jresponse = con.ignoreContentType(true).execute();
        String s = jresponse.body();
        JSONObject jsonObject = JSONObject.parseObject(s);
        String no_watermark_link = jsonObject.getString("no_watermark_link");
        log.info("tiktok no_watermark_link url = [{}]", no_watermark_link);
        return no_watermark_link;
    }
}
