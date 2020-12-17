package com.kalpana.asin2tk.svd.utils;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.validator.HibernateValidator;

/**
 * 接口入参数据校验工具类.<br/>
 * 使用hibernate-validator进行校验.<br/>
 */
public class ValidationUtils {


    /**
     * 使用hibernate的注解来进行验证
     */
    private static Validator validator = Validation
            .byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory().getValidator();

    /**
     * 功能描述: <br>
     * 〈注解验证参数〉
     *
     * @param obj
     */
    public static <T> void validate(T obj, Class<?>... groups) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj, groups);
        // 抛出检验异常
        if (constraintViolations.size() > 0) {
            throw new RuntimeException(constraintViolations.iterator().next().getMessage());
        }
    }

}
