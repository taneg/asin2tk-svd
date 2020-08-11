package com.kalpana.asin2tk.svd.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kalpana.asin2tk.svd.utils.VideoParseUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author JINVOVO
 * @version 1.0
 * @date 2020/8/10 14:01
 **/
@Slf4j
@RestController
public class SvdController {

    @GetMapping("/parse")
    public void parseSvdUrl(@RequestParam(name = "shareUrl") String shareUrl){
        try {
            VideoParseUtil.stk(shareUrl);
//            VideoParseUtil.parseUrl(shareUrl);
        }catch (Exception e){
            log.error("解析url异常", e);
        }
    }
}
