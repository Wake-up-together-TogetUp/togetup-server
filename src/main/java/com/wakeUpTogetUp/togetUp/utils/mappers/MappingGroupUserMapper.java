package com.wakeUpTogetUp.togetUp.utils.mappers;


import com.wakeUpTogetUp.togetUp.mappingGroupUser.dto.response.MappingGroupUserRes;
import com.wakeUpTogetUp.togetUp.mappingGroupUser.model.MappingGroupUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MappingGroupUserMapper {
    MappingGroupUserMapper INSTANCE = Mappers.getMapper(MappingGroupUserMapper.class);

    @Mapping(target = "groupId", expression = "java(mappingGroupUser.getGroup().getId())")
    @Mapping(target = "userId", expression = "java(mappingGroupUser.getUser().getId())")
    MappingGroupUserRes toMappingGroupUserRes(MappingGroupUser mappingGroupUser);
}
