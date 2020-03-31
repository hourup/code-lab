package com.example.tddinspringboot.repository;

import com.example.tddinspringboot.service.domain.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author guang
 * @since 2020/3/30 7:57 下午
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    /**
     * 保存
     *
     * @param user
     * @return
     */
    UserEntity saveDto(UserDTO user);

    /**
     * 查询
     *
     * @param userName
     * @return
     */
    UserEntity query(String userName);

    /**
     * 列表查询
     *
     * @return
     */
    List<UserEntity> list();
}
