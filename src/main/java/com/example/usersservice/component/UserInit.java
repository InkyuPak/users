package com.example.usersservice.component;

import com.example.usersservice.dto.UserCreateDto;
import com.example.usersservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserInit {

    UserService userService;

    @Autowired
    public UserInit(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < 100; i++) {
            UserCreateDto newUser = new UserCreateDto();

            newUser.setUserId("user" + i);
            newUser.setPassword("password" + i);
            newUser.setNickname("nickname" + i);
            newUser.setName("Name" + i);
            newUser.setPhoneNumber("0101234567" + i);
            newUser.setEmail("user" + i + "@example.com");

            userService.userCreate(newUser);
        }
    }
}
