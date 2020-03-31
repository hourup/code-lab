package com.example.tddinspringboot.repository;

import lombok.Data;

/**
 * @author guang
 * @since 2020/3/30 7:57 下午
 */
@Data
public class UserEntity {

    private Integer id;
    private String username;
    private String nickName;
    private Integer age;
    private String gender;
}
