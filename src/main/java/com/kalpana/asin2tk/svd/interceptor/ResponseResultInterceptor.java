package com.kalpana.asin2tk.svd.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.kalpana.asin2tk.svd.annotation.ResponseResult;
import com.kalpana.asin2tk.svd.common.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *
 * @author JINVOVO
 * @version 1.0
 * @date 2020/12/15 0015 15:20
 **/
@Component
@Slf4j
public class ResponseResultInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Class<?> clazz = handlerMethod.getBeanType();
            Method method = handlerMethod.getMethod();
            // 方法注解优先级更高
            if(clazz.isAnnotationPresent(ResponseResult.class)) {
                request.setAttribute(Constants.RESPONSE_HANDLE_ANNOTAITON, clazz.getAnnotation(ResponseResult.class));
            }else if(method.isAnnotationPresent(ResponseResult.class)){
                request.setAttribute(Constants.RESPONSE_HANDLE_ANNOTAITON, method.getAnnotation(ResponseResult.class));
            }
        }
        return true;
    }

//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        if(handler instanceof HandlerMethod){
//            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            Class<?> clazz = handlerMethod.getBeanType();
//            Method method = handlerMethod.getMethod();
//            // 方法注解优先级更高
//            if(clazz.isAnnotationPresent(ResponseResult.class)) {
//                request.setAttribute(Constants.RESPONSE_HANDLE_ANNOTAITON, clazz.getAnnotation(ResponseResult.class));
//            }
//            if(method.isAnnotationPresent(ResponseResult.class)){
//                request.setAttribute(Constants.RESPONSE_HANDLE_ANNOTAITON, method.getAnnotation(ResponseResult.class));
//            }
//        }
//    }

//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        if(handler instanceof HandlerMethod){
//            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            Class<?> clazz = handlerMethod.getBeanType();
//            Method method = handlerMethod.getMethod();
//            // 方法注解优先级更高
//            if(clazz.isAnnotationPresent(ResponseResult.class)) {
//                request.setAttribute(Constants.RESPONSE_HANDLE_ANNOTAITON, clazz.getAnnotation(ResponseResult.class));
//            }
//            if(method.isAnnotationPresent(ResponseResult.class)){
//                request.setAttribute(Constants.RESPONSE_HANDLE_ANNOTAITON, method.getAnnotation(ResponseResult.class));
//            }
//        }
//    }
}
