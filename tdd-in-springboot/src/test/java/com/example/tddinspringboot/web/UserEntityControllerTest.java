package com.example.tddinspringboot.web;

import com.example.tddinspringboot.TddInSpringbootApplication;
import com.example.tddinspringboot.service.IUserService;
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

import java.util.ArrayList;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 测试 Controller 层代码, Mock Service 依赖
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TddInSpringbootApplication.class)
@AutoConfigureMockMvc
public class UserEntityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Mock Service

    @MockBean
    private IUserService userService;

    @Test
    public void test_insert_user() throws Exception {

        UserDTO mockUserDto = new UserDTO();
        mockUserDto.setUserId(1);
        mockUserDto.setUsername("TDD");
        mockUserDto.setNickName("tdd");
        mockUserDto.setGender("male");
        mockUserDto.setAge(18);

        // 对 UserService 对象就行 Mock
        Mockito.when(userService.add(Mockito.any(UserDTO.class)))
                .thenReturn(Optional.of(mockUserDto));

        MvcResult mvcResult = mockMvc.perform(post("/user/add")
                .param("userId", "1")
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

        Mockito.when(userService.query(Mockito.anyString()))
                .thenReturn(Optional.of(mockUserDto));

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

        ArrayList<UserDTO> userDTOS = new ArrayList<>();
        userDTOS.add(mockUserDto);

        Mockito.when(userService.list())
                .thenReturn(userDTOS);

        ResultActions result = mockMvc.perform(get("/user/list").accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data[*].username").isNotEmpty());
    }

}