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
public class UserListDto implements Serializable {

    private String userId;
    private String password;
    private String nickname;
    private String name;
    private String phoneNumber;
    private String email;


    public static UserListDto convertToDto(UserEntity userEntity) {
        UserListDto userListDto = new UserListDto();
        userListDto.setUserId(userEntity.getUserId());
        userListDto.setPassword(userEntity.getPassword());
        userListDto.setNickname(userEntity.getNickName());
        userListDto.setName(userEntity.getName());
        userListDto.setPhoneNumber(userEntity.getPhoneNumber());
        userListDto.setEmail(userEntity.getEmail());
        return userListDto;
    }
}
