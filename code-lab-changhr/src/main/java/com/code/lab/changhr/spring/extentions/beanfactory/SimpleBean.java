package com.code.lab.changhr.spring.extentions.beanfactory;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author changhr
 * @create 2019-12-09 10:09
 */
@Data
@Accessors(chain = true)
public class SimpleBean {

    private String connectionString;

    private String username;

    private String password;
}
