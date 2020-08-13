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

}
