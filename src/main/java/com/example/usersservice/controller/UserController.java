package com.example.usersservice.controller;

import com.example.usersservice.dto.UserCreateDto;
import com.example.usersservice.dto.UserListDto;
import com.example.usersservice.dto.UserUpdateDto;
import com.example.usersservice.exception.UserNotFoundException;
import com.example.usersservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join")
    public ResponseEntity<UserCreateDto> join(@RequestBody UserCreateDto userCreateDto){
        UserCreateDto userJoin = userService.userCreate(userCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userJoin);
    }

    @GetMapping("/list")
    public Page<UserListDto> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "asc") String sort) {
        return userService.getUserList(page, pageSize, sort);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<UserUpdateDto> userUpdate(@PathVariable String userId, @RequestBody UserUpdateDto userUpdateDto) {
        UserUpdateDto userUpdate = userService.updateUser(userId, userUpdateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userUpdate);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
