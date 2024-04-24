package com.example.usersservice.service;

import com.example.usersservice.dto.UserCreateDto;
import com.example.usersservice.dto.UserListDto;
import com.example.usersservice.dto.UserUpdateDto;
import com.example.usersservice.jpa.UserEntity;
import com.example.usersservice.jpa.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void userCreate() {
        UserCreateDto inputDto = new UserCreateDto("userId", "password", "nickname", "name", "phoneNumber", "email");
        UserEntity userEntity = new UserEntity("userId", "password", "nickname", "name", "phoneNumber", "email");
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(modelMapper.map(any(UserEntity.class), eq(UserCreateDto.class))).thenReturn(inputDto);

        UserCreateDto result = userService.userCreate(inputDto);

        assertNotNull(result);
        assertEquals(inputDto.getUserId(), result.getUserId());
    }

    @Test
    void getUserList() {
        List<UserEntity> users = new ArrayList<>();
        Page<UserEntity> page = new PageImpl<>(users);
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt", "name"));
        when(userRepository.findAll(pageRequest)).thenReturn(page);

        Page<UserListDto> result = userService.getUserList(0, 10, "desc");

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
    }

    @Test
    void updateUser() {
        UserUpdateDto updateDto = new UserUpdateDto("password", "nickname", "name123123", "phoneNumber", "email");
        UserEntity userEntity = new UserEntity("userId", "password", "nickname", "name", "phoneNumber", "email");
        when(userRepository.findByUserId("userId")).thenReturn(userEntity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(modelMapper.map(any(UserEntity.class), eq(UserUpdateDto.class))).thenReturn(updateDto);

        UserUpdateDto result = userService.updateUser("userId", updateDto);

        assertNotNull(result);
        assertEquals("name12312", result.getName());
    }
}
