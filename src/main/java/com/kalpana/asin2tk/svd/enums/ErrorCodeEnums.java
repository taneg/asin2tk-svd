package com.kalpana.asin2tk.svd.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * TODO
 *
 * @author JINVOVO
 * @version 1.0
 * @date 2020/12/16 0016 11:13
 **/
@Getter
@AllArgsConstructor
public enum ErrorCodeEnums {

    MISS_REQUEST_BODY(400001, "缺少请求体"),
    MISS_REQUEST_PARAM(400002, "缺少请求参数"),

    ;

    private final int code;
    private final String msg;

}
