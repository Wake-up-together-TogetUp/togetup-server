package com.wakeUpTogetUp.togetUp.utils.mapper;

import com.wakeUpTogetUp.togetUp.api.group.dto.response.GroupRes;
import com.wakeUpTogetUp.togetUp.api.group.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GroupMapper {
   GroupMapper INSTANCE = Mappers.getMapper(GroupMapper.class);

   GroupRes toGroupRes(Room room);
}
