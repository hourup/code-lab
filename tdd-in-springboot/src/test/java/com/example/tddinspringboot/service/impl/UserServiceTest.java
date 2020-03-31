package com.example.tddinspringboot.service.impl;

import com.example.tddinspringboot.TddInSpringbootApplication;
import com.example.tddinspringboot.repository.UserEntity;
import com.example.tddinspringboot.repository.UserRepository;
import com.example.tddinspringboot.service.IUserService;
import com.example.tddinspringboot.service.domain.UserDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TddInSpringbootApplication.class)
@AutoConfigureMockMvc
public class UserServiceTest {

    @Autowired
    private IUserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void add() {

        UserDTO dto = new UserDTO();
        dto.setUsername("TDD");
        dto.setNickName("tdd");
        dto.setGender("male");
        dto.setAge(18);

        UserEntity mockUserEntity = new UserEntity();
        mockUserEntity.setId(1);
        mockUserEntity.setUsername("TDD");
        mockUserEntity.setNickName("tdd");
        mockUserEntity.setGender("male");
        mockUserEntity.setAge(18);
        Mockito.when(userRepository.saveDto(Mockito.any(UserDTO.class)))
                .thenReturn(mockUserEntity);

        Optional<UserDTO> saveUser = userService.add(dto);
        Assert.assertTrue(saveUser.isPresent());
        UserDTO userDTO = saveUser.get();
        Assert.assertEquals(userDTO.getUsername(), dto.getUsername());
        Assert.assertNotNull(userDTO.getUserId());

    }

    @Test
    public void query() {

        UserEntity mockUserEntity = new UserEntity();
        mockUserEntity.setId(1);
        mockUserEntity.setUsername("TDD");
        mockUserEntity.setNickName("tdd");
        mockUserEntity.setGender("male");
        mockUserEntity.setAge(18);

        Mockito.when(userRepository.query(Mockito.anyString()))
                .thenReturn(mockUserEntity);

        Optional<UserDTO> userDTOOpt = userService.query("TDD");

        Assert.assertTrue(userDTOOpt.isPresent());
        UserDTO userDTO = userDTOOpt.get();

        Assert.assertEquals(userDTO.getUsername(), "TDD");
        Assert.assertNotNull(userDTO.getUserId());
    }

    @Test
    public void list() {
        UserEntity mockUserEntity = new UserEntity();
        mockUserEntity.setId(1);
        mockUserEntity.setUsername("TDD");
        mockUserEntity.setNickName("tdd");
        mockUserEntity.setGender("male");
        mockUserEntity.setAge(18);

        ArrayList<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(mockUserEntity);

        Mockito.when(userRepository.list())
                .thenReturn(userEntities);

        List<UserDTO> userDTOS = userService.list();

        Assert.assertNotNull(userDTOS);
        Assert.assertEquals(userDTOS.size(), 1);

        Assert.assertEquals(userDTOS.get(0).getUsername(), "TDD");
        Assert.assertNotNull(userDTOS.get(0).getUserId());

    }
}