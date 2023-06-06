package com.wakeUpTogetUp.togetUp.utils.mapper;

import com.wakeUpTogetUp.togetUp.group.dto.response.GroupRes;
import com.wakeUpTogetUp.togetUp.group.model.Group;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GroupMapper {
   GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

   GroupRes toGroupRes(Group group);
}
