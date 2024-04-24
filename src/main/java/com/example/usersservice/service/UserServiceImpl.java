package com.example.usersservice.service;

import com.example.usersservice.dto.UserCreateDto;
import com.example.usersservice.dto.UserListDto;
import com.example.usersservice.dto.UserUpdateDto;
import com.example.usersservice.exception.UserNotFoundException;
import com.example.usersservice.jpa.UserEntity;
import com.example.usersservice.jpa.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserCreateDto userCreate(UserCreateDto userCreateDto) {

        ModelMapper mapper = new ModelMapper();
        UserEntity userEntity = UserEntity.builder()
                .userId(userCreateDto.getUserId())
                .password(userCreateDto.getPassword())
                .nickName(userCreateDto.getNickname())
                .name(userCreateDto.getName())
                .phoneNumber(userCreateDto.getPhoneNumber())
                .email(userCreateDto.getEmail())
                .build();

        UserEntity saveUser = userRepository.save(userEntity);

        return mapper.map(saveUser, UserCreateDto.class);
    }

    @Override
    public Page<UserListDto> getUserList(int page, int pageSize, String sort) {

        try {
            Sort.Direction direction;
            if (sort.equalsIgnoreCase("asc")) {
                direction = Sort.Direction.ASC;
            } else {
                direction = Sort.Direction.DESC;
            }

            PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(direction, "createdAt", "name"));

            Page<UserEntity> userList = userRepository.findAll(pageRequest);

            List<UserListDto> userListDto = userList.stream()
                    .map(UserListDto::convertToDto)
                    .collect(Collectors.toList());

            return new PageImpl<>(userListDto, pageRequest, userList.getTotalElements());
        } catch (Exception e) {
            /**
             * TODO: 예외 처리
             */
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UserUpdateDto updateUser(String userId, UserUpdateDto userUpdateDto) {

        try {
            ModelMapper mapper = new ModelMapper();
            UserEntity user = userRepository.findByUserId(userId);
            if (user == null) {
                throw new UserNotFoundException("존재 하지 않는 ID 입니다. " + userId);
            }

            user.updateUser(
                    userUpdateDto.getPassword(),
                    userUpdateDto.getNickname(),
                    userUpdateDto.getName(),
                    userUpdateDto.getPhoneNumber(),
                    userUpdateDto.getEmail()
            );

            UserEntity updateUser = userRepository.save(user);

            return mapper.map(updateUser, UserUpdateDto.class);
        } catch (Exception e) {
            /**
             * TODO: 예외 처리
             */
            e.printStackTrace();
            return null;
        }
    }


}
