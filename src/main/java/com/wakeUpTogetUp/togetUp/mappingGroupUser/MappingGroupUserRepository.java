package com.wakeUpTogetUp.togetUp.mappingGroupUser;

import com.wakeUpTogetUp.togetUp.mappingGroupUser.model.MappingGroupUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MappingGroupUserRepository extends JpaRepository<MappingGroupUser, Integer> {
    List<MappingGroupUser> findByGroupId(Integer groupId);
}
