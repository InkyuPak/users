package com.example.usersservice.dto;

import com.example.usersservice.jpa.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto implements Serializable {

    private String userId;
    private String password;
    private String nickname;
    private String name;
    private String phoneNumber;
    private String email;
}
