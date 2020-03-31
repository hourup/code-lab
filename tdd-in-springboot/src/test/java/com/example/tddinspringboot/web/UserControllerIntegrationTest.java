package com.example.tddinspringboot.web;

import com.example.tddinspringboot.TddInSpringbootApplication;
import com.example.tddinspringboot.repository.UserEntity;
import com.example.tddinspringboot.repository.UserRepository;
import com.example.tddinspringboot.service.domain.UserDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 集成测试
 *
 * @author guang
 * @since 2020/3/31 3:28 下午
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TddInSpringbootApplication.class)
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    /**
     *  Mock Repository 数据,不依赖数据库
     */
    @PostConstruct
    public void before() {

        // userRepository.save()
        UserEntity mockUserEntity = new UserEntity();
        mockUserEntity.setId(1);
        mockUserEntity.setUsername("TDD");
        mockUserEntity.setNickName("tdd");
        mockUserEntity.setGender("male");
        mockUserEntity.setAge(18);
        Mockito.when(userRepository.save(Mockito.any(UserEntity.class)))
                .thenReturn(mockUserEntity);

        // userRepository.findByUsername()
        Mockito.when(userRepository.findByUsername(Mockito.anyString()))
                .thenReturn(mockUserEntity);

        // userRepository.list()
        ArrayList<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(mockUserEntity);

        Mockito.when(userRepository.list())
                .thenReturn(userEntities);
    }

    @Test
    public void test_insert_user() throws Exception {

        UserDTO mockUserDto = new UserDTO();
        mockUserDto.setUsername("TDD");
        mockUserDto.setNickName("tdd");
        mockUserDto.setGender("male");
        mockUserDto.setAge(18);

        MvcResult mvcResult = mockMvc.perform(post("/user/add")
                .param("id", "1")
                .param("username", "TDD")
                .param("nickName", "tdd")
                .param("gender", "male")
                .param("age", "18")

        ).andExpect(status().isOk())
                .andDo(print())
                .andReturn();

    }

    @Test
    public void test_query_user() throws Exception {

        UserDTO mockUserDto = new UserDTO();
        mockUserDto.setUserId(1);
        mockUserDto.setUsername("TDD");
        mockUserDto.setNickName("tdd");
        mockUserDto.setGender("male");
        mockUserDto.setAge(18);

        ResultActions result = mockMvc.perform(get("/user/{username}", "TDD")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value(1))
                .andExpect(jsonPath("$.data.username").value("TDD"));

    }

    @Test
    public void test_get_user_list() throws Exception {
        UserDTO mockUserDto = new UserDTO();
        mockUserDto.setUserId(1);
        mockUserDto.setUsername("TDD");
        mockUserDto.setNickName("tdd");
        mockUserDto.setGender("male");
        mockUserDto.setAge(18);

        ResultActions result = mockMvc.perform(get("/user/list").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data[*].username").isNotEmpty());
    }
}
