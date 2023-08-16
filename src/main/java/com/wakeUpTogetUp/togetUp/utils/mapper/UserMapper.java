package com.wakeUpTogetUp.togetUp.utils.mapper;

import com.wakeUpTogetUp.togetUp.api.users.dto.response.UserInfoRes;
import com.wakeUpTogetUp.togetUp.api.users.dto.response.UserRes;
import com.wakeUpTogetUp.togetUp.api.users.model.User;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserRes toUserRes(User user);
    UserInfoRes toUserInfoRes(User user);


}
