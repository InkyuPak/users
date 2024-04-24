package com.example.usersservice.service;

import com.example.usersservice.dto.UserCreateDto;
import com.example.usersservice.dto.UserListDto;
import com.example.usersservice.dto.UserUpdateDto;
import org.springframework.data.domain.Page;

public interface UserService {

    UserCreateDto userCreate(UserCreateDto userCreateDto);
    Page<UserListDto> getUserList(int page, int pageSize, String sort);
    UserUpdateDto updateUser(String userId, UserUpdateDto userUpdateDto);
}
