package com.kalpana.asin2tk.svd.handler;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.kalpana.asin2tk.svd.common.Constants;
import com.kalpana.asin2tk.svd.common.Result;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author JINVOVO
 * @version 1.0
 * @date 2020/12/15 0015 15:47
 **/
@RestControllerAdvice
@Slf4j
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
//        Class<?> declaringClass = methodParameter.getExecutable().getDeclaringClass();
//        if(declaringClass.getName().equals(CustomExceptionHandler.class.getName())){
//            return false;
//        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        Object attribute = request.getAttribute(Constants.RESPONSE_HANDLE_ANNOTAITON);
        return attribute == null ? false : true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if(o instanceof Result){
            return o;
        }
        return Result.ok(o);
    }
}
