package com.example.tddinspringboot.service;

import com.example.tddinspringboot.service.domain.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author guang
 * @since 2020/3/30 5:25 下午
 */
@Service
public interface IUserService {

    /**
     * 添加用户
     *
     * @param UserDTO
     * @return
     */
    Optional<UserDTO> add(UserDTO UserDTO);

    /**
     * 查询用户
     *
     * @param username
     * @return
     */
    Optional<UserDTO> query(String username);

    /**
     * 用户列表
     *
     * @return
     */
    List<UserDTO> list();

}
