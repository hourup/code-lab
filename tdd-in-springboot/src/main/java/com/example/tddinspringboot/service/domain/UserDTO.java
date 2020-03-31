package com.example.tddinspringboot.service.domain;

import lombok.Data;

/**
 * @author guang
 * @since 2020/3/30 5:06 下午
 */
@Data
public class UserDTO {

    private Integer userId;
    private String username;
    private String nickName;
    private Integer age;
    private String gender;


}
