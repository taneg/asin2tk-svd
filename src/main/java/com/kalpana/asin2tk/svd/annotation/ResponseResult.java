package com.kalpana.asin2tk.svd.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO
 *
 * @author JINVOVO
 * @version 1.0
 * @date 2020/12/15 0015 15:03
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface ResponseResult {
}
