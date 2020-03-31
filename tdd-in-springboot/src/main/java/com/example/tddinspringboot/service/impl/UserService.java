package com.example.tddinspringboot.service.impl;

import com.example.tddinspringboot.repository.UserEntity;
import com.example.tddinspringboot.repository.UserRepository;
import com.example.tddinspringboot.service.IUserService;
import com.example.tddinspringboot.service.domain.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author guang
 * @since 2020/3/30 5:30 下午
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<UserDTO> add(UserDTO userDTO) {
        UserEntity user = userRepository.saveDto(userDTO);
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);
        dto.setUserId(user.getId());
        return Optional.of(dto);
    }

    @Override
    public Optional<UserDTO> query(String username) {
        UserEntity user = userRepository.query(username);
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);
        dto.setUserId(user.getId());
        return Optional.of(dto);
    }

    @Override
    public List<UserDTO> list() {
        List<UserEntity> userEntities = userRepository.list();

        List<UserDTO> userDTOS = userEntities.stream()
                .map(userEntity -> {
                    UserDTO dto = new UserDTO();
                    BeanUtils.copyProperties(userEntity, dto);
                    dto.setUserId(userEntity.getId());
                    return dto;
                }).collect(Collectors.toList());

        return userDTOS;
    }
}
