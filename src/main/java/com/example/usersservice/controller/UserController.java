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


    /**
     * 회원가입
     * @param userCreateDto 사용자 정보를 담고 있는 데이터 전송 객체
     * @return 생성된 회원 데이터와 HTTP 상태 코드를 포함한 ResponseEntity
     */
    @PostMapping("/join")
    public ResponseEntity<UserCreateDto> join(@RequestBody UserCreateDto userCreateDto){
        UserCreateDto userJoin = userService.userCreate(userCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userJoin);
    }

    /**
     * 회원조회 - 페이징 처리
     * @param page 페이지 번호 (default = 0)
     * @param pageSize 페이지당 사용자 수입니다 (default = 10)
     * @param sort 목록 정렬 (default = asc)
     * @return 페이지별 회원 목록
     */
    @GetMapping("/list")
    public Page<UserListDto> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "asc") String sort) {
        return userService.getUserList(page, pageSize, sort);
    }

    /**
     * 기존 회원 정보 수정
     * @param userId 업데이트할 userId
     * @param userUpdateDto 업데이트된 회원 정보를 담고 있는 데이터 전송 객체
     * @return 업데이트된 사용자 데이터와 HTTP 상태 코드를 포함한 ResponseEntity
     */
    @PostMapping("/{userId}")
    public ResponseEntity<UserUpdateDto> userUpdate(@PathVariable String userId, @RequestBody UserUpdateDto userUpdateDto) {
        UserUpdateDto userUpdate = userService.updateUser(userId, userUpdateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userUpdate);
    }

    /**
     * UserNotFoundException을 처리하는 예외 핸들러
     * @param ex UserNotFoundException 객체
     * @return 오류 메시지와 HTTP 상태 코드를 포함한 ResponseEntity
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
