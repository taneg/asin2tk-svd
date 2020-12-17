package com.kalpana.asin2tk.svd.controller;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kalpana.asin2tk.svd.annotation.ResponseResult;
import com.kalpana.asin2tk.svd.common.Result;
import com.kalpana.asin2tk.svd.dto.TestDTO;
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
@RequestMapping("/test")
public class TestController {

    @GetMapping("parse0")
    public Result parseSvdUrl0(@RequestParam("createDate") LocalDateTime testDTO) {
        log.info("dto = {}", JSON.toJSONString(testDTO));
        return Result.ok(testDTO);
    }
    @PostMapping("parse0")
    public Result parseSvdUrl_1(@RequestParam("createDate") LocalDateTime testDTO) {
        log.info("dto = {}", JSON.toJSONString(testDTO));
        return Result.ok(testDTO);
    }

    @GetMapping("parse")
    public Result<TestDTO> parseSvdUrl(@Validated TestDTO testDTO) {
        log.info("dto = {}", JSON.toJSONString(testDTO));
        return Result.ok(testDTO);
    }

    @PostMapping("parse")
    public Result<TestDTO> parseSvdUrl2(@Validated TestDTO testDTO) {
        log.info("dto = {}", JSON.toJSONString(testDTO));
        return Result.ok(testDTO);
    }
    @PostMapping("parse1")
    public Result<TestDTO> parseSvdUrl3(@Validated @RequestBody TestDTO testDTO) {
        log.info("dto = {}", JSON.toJSONString(testDTO));
        return Result.ok(testDTO);
    }

    @GetMapping("parse2")
    @ResponseResult
    public TestDTO parseSvdUrl4(@Validated TestDTO testDTO) {
        log.info("dto = {}", JSON.toJSONString(testDTO));
        return testDTO;
    }

    @PostMapping("parse2")
    @ResponseResult
    public void parseSvdUrl5(@Validated TestDTO testDTO) {
        log.info("dto = {}", JSON.toJSONString(testDTO));
    }

    @GetMapping("parse3/{createDate}")
    public Result parseSvdUrl6(@PathVariable("createDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime testDTO) {
        log.info("dto = {}", JSON.toJSONString(testDTO));
        return Result.ok(testDTO);
    }
    @PostMapping("parse3/{createDate}")
    public Result parseSvdUrl7(@PathVariable("createDate") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dateTime) {
        TestDTO testDTO = new TestDTO();
        testDTO.setEndDate(dateTime);
        log.info("dto = {}", JSON.toJSONString(testDTO));
        return Result.ok(testDTO);
    }
}
