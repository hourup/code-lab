package com.example.tddinspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author guang
 * @since 2020/3/30 7:57 下午
 */
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    /**
     * 查询
     *
     * @param username
     * @return
     */
    UserEntity findByUsername(String username);

    /**
     * 列表查询
     *
     * @return
     */
    @Query(value = "select * from user", nativeQuery = true)
    List<UserEntity> list();
}
