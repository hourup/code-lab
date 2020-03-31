package com.example.tddinspringboot.service.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author guang
 * @since 2020/3/30 5:24 下午
 */
@Data
public class JsonResult implements Serializable {
    private int code;
    private String message;
    private Object data;
}
