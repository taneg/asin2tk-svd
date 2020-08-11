package com.kalpana.asin2tk.svd.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author JINVOVO
 * @version 1.0
 * @date 2020/8/10 14:02
 **/
@Slf4j
public class VideoParseUtil {
    //视频保存目录
    private static final String videoSavePath="d:/短视频/";

    //分享链接（手动修改）
    private static String targetPath = "https://v.douyin.com/J6aJ5Hk/";

    //根路径
    private static String baseURL = "https://ssstiktok.io";
//    private static String targetPath = "奇怪，刚刚和妈妈的衣架子交心攀谈后，怎么感觉头上有一圈星星呢～ https://v.kuaishou.com/6Rq0gB 复制此链接，打开【快手App】直接观看！";

    public static void main(String[] args) throws Exception {
//        parseUrl(targetPath);
        stk("https://vt.tiktok.com/yyGHQ9/");
    }

    /**
     * 方法描述:短视频解析
     *
     * @param url
     * @author tarzan
     * @date 2020年08月04日 11:46:26
     */
    public static void parseUrl(String url) throws Exception {
        if(url.contains("v.kuaishou.com")){
            ksParseUrl(filterUrl(url));
        }
        if (url.contains("v.douyin.com") || url.contains("vt.tiktok.com")){
            douYinParseUrl(filterUrl(url));
        }
    }


    /**
     * 方法描述: 快手解析下载视频
     *
     * @param url
     * @author tarzan
     * @date 2020年08月04日 10:33:40
     */
    public static void ksParseUrl(String url) throws Exception {
        HashMap<String, String> headers = MapUtil.newHashMap();
        headers.put("User-Agent", "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Mobile Safari/537.36");
        String redirectUrl = HttpUtil.createGet(url).addHeaders(headers).execute().header("Location");
        String body= HttpUtil.createGet(redirectUrl).addHeaders(headers).execute().body();
        Document doc= Jsoup.parse(body);
        Elements videoElement = doc.select("video[id=video-player]");
        String videoUrl = videoElement.get(0).attr("src");
        String title = videoElement.get(0).attr("alt");
        log.debug(videoUrl);
        log.debug(title);
        downVideo(videoUrl,title,"快手视频");
    }








    /**
     * @param url https://xxxxx/JHO123
     * @return
     */
    public static void stk(String url) throws Exception {
        HashMap<String, String> headers = MapUtil.newHashMap();
        headers.put("User-Agent",   "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Mobile Safari/537.36");
        String body= HttpUtil.createGet(baseURL).addHeaders(headers).execute().body();
        Document doc= Jsoup.parse(body);
        Elements formElement = doc.select("form[class=pure-form pure-g hide-after-request]");
        String postUrl = formElement.attr("data-hx-post");
        Elements tokenElment = formElement.select("input[id=token]");
        Elements localeElment = formElement.select("input[id=locale]");
        String token = tokenElment.attr("value");
        String locale = localeElment.attr("value");
        Connection con = Jsoup.connect(baseURL.concat(postUrl))
                .data("id", url)
                .data("token", token)
                .data("locale", locale)
                .method(Connection.Method.POST);
//        con.header("Accept", "*");
//        con.header("Accept-Language", "zh-CN,zh;q=0.9");
//        con.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        con.header("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Mobile Safari/537.36");
        Connection.Response jresponse = con.ignoreContentType(true).execute();
        String s = jresponse.body();
        JSONObject jsonObject = JSONObject.parseObject(s);
        JSONObject snaptikData = jsonObject.getJSONObject("snaptik_data");
        String no_watermark_link = jsonObject.getString("no_watermark_link");
        JSONObject data = jsonObject.getJSONObject("data");
//        Connection.Response resp=con.method(Connection.Method.GET).execute();
//        HttpResponse httpResponse = HttpUtil.createGet(no_watermark_link).addHeaders(headers).execute();
//        InputStream inputStream = httpResponse.bodyStream();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        servletRequestAttributes.getResponse().sendRedirect(no_watermark_link);

        log.debug(s);
//        log.debug(title);
    }

    /**
     * 方法描述: 抖音解析下载视频
     *
     * @param url
     * @author tarzan
     * @date 2020年08月04日 10:33:40
     */
    public static void douYinParseUrl(String url) throws Exception {
//        try {
            final  String videoPath="https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids=";
            Connection con= Jsoup.connect(filterUrl(url));
            con.header("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 12_1_4 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/16D57 Version/12.0 Safari/604.1");
            Connection.Response resp=con.method(Connection.Method.GET).execute();
            String videoUrl= videoPath+getItemId(resp.url().toString());
            String jsonStr = Jsoup.connect(videoUrl).ignoreContentType(true).execute().body();
            JSONObject json =JSONObject.parseObject(jsonStr);
            String videoAddress= json.getJSONArray("item_list").getJSONObject(0).getJSONObject("video").getJSONObject("play_addr").getJSONArray("url_list").get(0).toString();
            String title= json.getJSONArray("item_list").getJSONObject(0).getJSONObject("share_info").getString("share_title");
            videoAddress=videoAddress.replaceAll("playwm","play");
            HashMap<String, String> headers = MapUtil.newHashMap();
            headers.put("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 12_1_4 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/16D57 Version/12.0 Safari/604.1");
            String finalVideoAddress = HttpUtil.createGet(videoAddress).addHeaders(headers).execute().header("Location");
            //注:打印获取的链接
            System.out.println("-----抖音去水印链接-----\n"+finalVideoAddress);
            //下载无水印视频到本地
            downVideo(finalVideoAddress,title,"抖音视频");
//        } catch (IOException e) {
//            log.error(e.getMessage());
//        }
    }

    /**
     * 方法描述: 下载无水印视频方法
     *
     * @param httpUrl
     * @param title
     * @author tarzan
     * @date 2020年08月04日 10:34:09
     */
    public static void downVideo(String httpUrl,String title,String source) throws Exception {


        String fileAddress = videoSavePath+"/"+source+"/"+title+".mp4";
        int byteRead;
//        try {
            URL url = new URL(httpUrl);
            //获取链接
            URLConnection conn = url.openConnection();
            //输入流
            InputStream inStream = conn.getInputStream();
            //封装一个保存文件的路径对象
            File fileSavePath = new File(fileAddress);
            //注:如果保存文件夹不存在,那么则创建该文件夹
            File fileParent = fileSavePath.getParentFile();
            if(!fileParent.exists()){
                fileParent.mkdirs();
            }
            //写入文件
//            FileOutputStream fs = new FileOutputStream(fileSavePath);
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            HttpServletResponse response = servletRequestAttributes.getResponse();
            //且仅当此对象抽象路径名表示的文件或目录存在时，返回true
            response.setContentType("video/mpeg4");
            //控制下载文件的名字
            response.addHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(title+".mp4", "UTF-8"));
            ServletOutputStream fs = response.getOutputStream();
            byte[] buffer = new byte[1024];
            while ((byteRead = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteRead);
            }
            inStream.close();
            fs.close();
//            System.out.println("\n-----视频保存路径-----\n"+fileSavePath.getAbsolutePath());
//        } catch (FileNotFoundException e) {
//            log.error(e.getMessage());
//        } catch (IOException e) {
//            log.error(e.getMessage());
//        }
    }


    /**
     * 方法描述: 过滤分享链接的中文汉字
     *
     * @param url
     * @Return {@link String}
     * @author tarzan
     * @date 2020年08月03日 17:36:33
     */
    public static String filterUrl(String url) {
        String regex = "https?://(\\w|-)+(\\.(\\w|-)+)+(/(\\w+(\\?(\\w+=(\\w|%|-)*(\\&\\w+=(\\w|%|-)*)*)?)?)?)+";//匹配网址
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(url);
        if(m.find()){
            return   url.substring(m.start(),m.end());
        }
        return "";
    }

    /**
     * 方法描述: 获取分享视频id
     *
     * @param url
     * @Return {@link String}
     * @author tarzan
     * @date 2020年08月03日 17:36:12
     */
    public static String getItemId(String url){
        int start = url.indexOf("/video/")+7;
        int end = url.lastIndexOf("/");
        String itemId = url.substring(start, end);
        return  itemId;
    }
}
