package com.kalpana.asin2tk.svd.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 2tk
 * @version 1.0
 * @date 2020/8/12 19:00
 **/
@Slf4j
public class UrlUtil {

    private static Pattern p = Pattern.compile("https?://(\\w|-)+(\\.(\\w|-)+)+(/(\\w+(\\?(\\w+=(\\w|%|-)*(\\&\\w+=(\\w|%|-)*)*)?)?)?)+");

    /**
     * 根据url获取域名
     *
     * @param
     * @return
     */
    public static String getDomainWithpoutPrefix(String url) throws MalformedURLException {
        String[] hostarr = new URL(url).getHost().split("\\.");
        return hostarr[hostarr.length - 2].concat(".").concat(hostarr[hostarr.length - 1]);
    }

    public static String filterUrl(String url) {
        Matcher m = p.matcher(url);
        if (m.find()) {
            return url.substring(m.start(), m.end());
        }
        return null;
    }

    public static String filterUrl2(String url) {
        String regex = "https?://(\\w|-)+(\\.(\\w|-)+)+(/(\\w+(\\?(\\w+=(\\w|%|-)*(\\&\\w+=(\\w|%|-)*)*)?)?)?)+";//匹配网址
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(url);
        if(m.find()){
            return   url.substring(m.start(),m.end());
        }
        return "";
    }

    public static void main(String[] args) {
        String s = filterUrl("高考加油\uD83D\uDCAA%20祝你们都能考一个理想的大学！%20你们是最棒的❤%EF%B8%8F#高考加油%20#快手管理员%20https://v.kuaishou.com/5y1T1s%20复制此链接，打开【快手App】直接观看！");
        System.out.println(s);
    }
}
