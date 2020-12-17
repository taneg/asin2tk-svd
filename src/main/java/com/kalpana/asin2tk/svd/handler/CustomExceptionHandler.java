package com.kalpana.asin2tk.svd.handler;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.alibaba.fastjson.JSON;
import com.kalpana.asin2tk.svd.common.Constants;
import com.kalpana.asin2tk.svd.common.Result;
import com.kalpana.asin2tk.svd.enums.ErrorCodeEnums;

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
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result handlerNotFoundException(NoHandlerFoundException e)
    {
        log.error("请求的资源不可用",e);
        return Result.build(Result.CODE_FAIL, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public Result handlerBadRequestException(HttpClientErrorException.BadRequest e)
    {
        log.error("请求的资源不可用",e);
        return Result.build(Result.CODE_FAIL, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result  httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return Result.build(Result.CODE_FAIL, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Result  httpRequestMethodNotSupportedException(ConstraintViolationException e) {
        return Result.build(Result.CODE_FAIL, e.getMessage());
    }


    @ExceptionHandler({Throwable.class})
//    @ResponseStatus(HttpStatus.OK)
    public Result methodArgumentNotValidHandler(HttpServletRequest request, Throwable throwable) {
        log.info("HttpServletRequest URL={}", request.getRequestURI());
        log.error("", throwable);
        return Result.build(Result.CODE_FAIL, throwable.getMessage());
//        if (throwable instanceof MissingServletRequestParameterException) {
//            return Result.build(CodeEnum.ERROR_PARAM, "参数异常:" + ((MissingServletRequestParameterException)throwable).getParameterName() + "不能为空！", (Object)null);
//        } else if (!(throwable instanceof ConstraintViolationException)) {
//            if (throwable instanceof MethodArgumentNotValidException) {
//                MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException)throwable;
//                Iterator var11 = methodArgumentNotValidException.getBindingResult().getFieldErrors().iterator();
//                if (var11.hasNext()) {
//                    FieldError error = (FieldError)var11.next();
//                    log.error("参数异常:" + error.getField() + error.getDefaultMessage());
//                    return VoUtils.returnResultVOError(CodeEnum.DYNAMIC_CODE.setDynamic(CodeEnum.ERROR_PARAM, error.getDefaultMessage()), (Object)null);
//                } else {
//                    return VoUtils.returnResultVOError(CodeEnum.ERROR_PARAM, (Object)null);
//                }
//            } else if (throwable instanceof BindException) {
//                BindException ex = (BindException)throwable;
//                List<ObjectError> allErrors = ex.getAllErrors();
//                ObjectError error = (ObjectError)allErrors.get(0);
//                String errorMsg = error.getDefaultMessage();
//                if (errorMsg.contains("NumberFormatException")) {
//                    errorMsg = "数字格式错误！";
//                }
//
//                return VoUtils.returnBaseVODynamic(CodeEnum.ERROR_PARAM, errorMsg);
//            } else if (throwable instanceof ServiceException) {
//                ServiceException serviceException = (ServiceException)throwable;
//                return null != serviceException.getCodeEnum() ? VoUtils.returnBaseVOError(serviceException.getCodeEnum()) : VoUtils.returnBaseVODynamicCode(serviceException.getErrorCode(), serviceException.getErrorMsg());
//            } else {
//                return VoUtils.returnResultVODynamic(CodeEnum.ERROR, throwable.getMessage(), (Object)null);
//            }
//        } else {
//            Set<ConstraintViolation<?>> violations = ((ConstraintViolationException)throwable).getConstraintViolations();
//            StringBuilder strBuilder = new StringBuilder();
//            Iterator var5 = violations.iterator();
//
//            while(var5.hasNext()) {
//                ConstraintViolation<?> violation = (ConstraintViolation)var5.next();
//                strBuilder.append(violation.getInvalidValue() + ": " + violation.getMessage() + "\\n");
//            }
//
//            String result = strBuilder.toString();
//            return VoUtils.returnResultVODynamic(CodeEnum.ERROR_PARAM, "参数异常: " + result, (Object)null);
//        }
    }

    /**
     *
     * @param
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public Result  bindException(BindException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        return Result.build(ErrorCodeEnums.MISS_REQUEST_PARAM.getCode(), JSON.toJSONString(allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList())));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result  methodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        return Result.build(ErrorCodeEnums.MISS_REQUEST_PARAM.getCode(), JSON.toJSONString(allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList())));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result  httpMessageNotReadableException(HttpMessageNotReadableException e) {
        return Result.build(ErrorCodeEnums.MISS_REQUEST_BODY);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingPathVariableException.class)
    public Result  missingPathVariableException(MissingPathVariableException e) {
        return Result.build(Result.CODE_FAIL, e.getMessage());
    }

    /**
     * @RequestParam 单个参数
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result  missingServletRequestParameterException(MissingServletRequestParameterException e) {
        return Result.build(Result.CODE_FAIL, e.getMessage());
    }
}
