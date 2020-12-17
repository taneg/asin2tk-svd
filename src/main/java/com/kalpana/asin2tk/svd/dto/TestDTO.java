package com.kalpana.asin2tk.svd.dto;

import java.time.LocalDateTime;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * TODO
 *
 * @author JINVOVO
 * @version 1.0
 * @date 2020/12/14 0014 17:19
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestDTO {
//    @NotNull(message = "开始时间不能为空")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

//    @NotBlank(message = "desc 不能为空")
    private String desc;
}
