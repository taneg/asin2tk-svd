package com.kalpana.asin2tk.svd.interfaces.impl;

import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
public class KuaishouSvdParseInterfaceImpl implements SvdParseInterface {

    /**
     * 模拟浏览器
     */
    protected static final String USER_AGENT = "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Mobile Safari/537.36";

    @Override
    public String getType() {
        return "kuaishou.com";
    }

    @Override
    public String parseUrl(String shareUrl) throws Exception {
        HashMap<String, String> headers = MapUtil.newHashMap();
        headers.put("User-Agent", USER_AGENT);
        String redirectUrl = HttpUtil.createGet(shareUrl).addHeaders(headers).execute().header("Location");
        String body = HttpUtil.createGet(redirectUrl).addHeaders(headers).execute().body();
        Document doc = Jsoup.parse(body);
        Elements scriptEle = doc.getElementsByTag("script");
        Element dataEle = null;
        for(Element e : scriptEle){
            if(e.data().contains("window.pageData")){
                dataEle = e;
                break;
            }
        }
        if(dataEle == null){
            log.error("未解析到数据集");
            throw new RuntimeException("未解析到数据集");
        }
        String dataOriJson = dataEle.data().split("window.pageData=")[1];
        JSONObject dataObject = JSONObject.parseObject(dataOriJson);
        return dataObject.getJSONObject("video").getString("srcNoMark");
    }
}
