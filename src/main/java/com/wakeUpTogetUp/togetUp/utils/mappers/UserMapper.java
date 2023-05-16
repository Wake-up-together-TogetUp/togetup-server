package com.wakeUpTogetUp.togetUp.utils.mappers;

import com.wakeUpTogetUp.togetUp.users.dto.response.UserInfoRes;
import com.wakeUpTogetUp.togetUp.users.dto.response.UserRes;
import com.wakeUpTogetUp.togetUp.users.model.User;

import com.wakeUpTogetUp.togetUp.users.dto.response.UserRes;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserRes toUserRes(User user);
    UserInfoRes toUserInfoRes(User user);

    UserRes toUserRes(Optional<User> user);
}
