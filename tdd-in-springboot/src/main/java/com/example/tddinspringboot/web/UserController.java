package com.example.tddinspringboot.web;

import com.example.tddinspringboot.service.IUserService;
import com.example.tddinspringboot.service.domain.JsonResult;
import com.example.tddinspringboot.service.domain.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author guang
 * @since 2020/3/30 4:38 下午
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/add")
    public JsonResult add(UserDTO UserDTO) {
        JsonResult JsonResult = new JsonResult();
        Optional<UserDTO> result = userService.add(UserDTO);
        if (result.isPresent()){
            JsonResult.setData(result.get());
        }else {
            throw new RuntimeException("add 异常");
        }
        return JsonResult;
    }

    @GetMapping("/{username}")
    public JsonResult query(@PathVariable("username") String username) {
        JsonResult JsonResult = new JsonResult();
        Optional<UserDTO> result = userService.query(username);
        JsonResult.setData(result.get());
        return JsonResult;
    }

    @GetMapping("/list")
    public JsonResult list() {
        JsonResult JsonResult = new JsonResult();
        List<UserDTO> result = userService.list();
        JsonResult.setData(result);
        return JsonResult;
    }

}
