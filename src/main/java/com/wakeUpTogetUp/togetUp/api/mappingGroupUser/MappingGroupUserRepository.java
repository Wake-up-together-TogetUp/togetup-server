package com.wakeUpTogetUp.togetUp.api.mappingGroupUser;

import com.wakeUpTogetUp.togetUp.api.mappingGroupUser.model.MappingGroupUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MappingGroupUserRepository extends JpaRepository<MappingGroupUser, Integer> {
    List<MappingGroupUser> findByGroupId(Integer groupId);
    List<MappingGroupUser> findByUserId(Integer UserId);
    MappingGroupUser findByUserIdAndGroupId(Integer userId, Integer groupId);

    Integer deleteByUserIdAndGroupId(Integer userId,Integer groupId);

}
