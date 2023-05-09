package com.wakeUpTogetUp.togetUp.utils.mappers;

import com.wakeUpTogetUp.togetUp.group.dto.response.GroupRes;
import com.wakeUpTogetUp.togetUp.group.model.Group;
import com.wakeUpTogetUp.togetUp.mappingGroupUser.dto.response.MappingGroupUserRes;
import com.wakeUpTogetUp.togetUp.mappingGroupUser.model.MappingGroupUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GroupMapper {
   GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

   GroupRes toGroupRes(Group group);
}
