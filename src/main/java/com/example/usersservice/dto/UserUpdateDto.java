package com.example.usersservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto implements Serializable {

    private String password;
    private String nickname;
    private String name;
    private String phoneNumber;
    private String email;
}
