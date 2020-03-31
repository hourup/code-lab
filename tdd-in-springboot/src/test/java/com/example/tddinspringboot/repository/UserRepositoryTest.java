package com.example.tddinspringboot.repository;

import com.example.tddinspringboot.TddInSpringbootApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Repository 层 是否需要进行单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TddInSpringbootApplication.class)
@AutoConfigureMockMvc
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveDto() {

        UserEntity mockUserEntity = new UserEntity();
        mockUserEntity.setUsername("TDD");
        mockUserEntity.setNickName("tdd");
        mockUserEntity.setGender("male");
        mockUserEntity.setAge(18);

        UserEntity userEntity = userRepository.save(mockUserEntity);

        Assert.assertEquals(userEntity.getUsername(), "TDD");
    }

    @Test
    public void query() {
        UserEntity userEntity = userRepository.findByUsername("TDD");

        Assert.assertEquals(userEntity.getUsername(), "TDD");
    }

    @Test
    public void list() {
        List<UserEntity> userEntities = userRepository.list();

        Assert.assertTrue(userEntities != null);
    }
}