package com.kalpana.asin2tk.svd.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.kalpana.asin2tk.svd.common.Result;
import com.kalpana.asin2tk.svd.factory.SvdParseInterfaceFactory;
import com.kalpana.asin2tk.svd.interfaces.SvdParseInterface;
import com.kalpana.asin2tk.svd.utils.UrlUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author kalpana
 * @version 1.0
 * @date 2020/8/10 14:01
 **/
@Slf4j
@RestController
public class SvdController {

    @PostMapping("/parse")
    public Result<String> parseSvdUrl(@RequestBody String jsonstr) {
        try {
            String shareUrl = JSONObject.parseObject(jsonstr).getString("shareUrl");
            shareUrl = UrlUtil.filterUrl(shareUrl);
            SvdParseInterface svdParseInterface = SvdParseInterfaceFactory.getObject(UrlUtil.getDomainWithpoutPrefix(shareUrl));
            return Result.ok(svdParseInterface.parseUrl(shareUrl));
        } catch (Exception e) {
            log.error("解析url异常", e);
            return Result.build(99999, "解析url失败");
        }
    }
}
