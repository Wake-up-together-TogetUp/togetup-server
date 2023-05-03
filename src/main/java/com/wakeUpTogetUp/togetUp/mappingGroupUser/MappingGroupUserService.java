package com.wakeUpTogetUp.togetUp.mappingGroupUser;


import com.wakeUpTogetUp.togetUp.group.GroupDto;
import com.wakeUpTogetUp.togetUp.group.GroupRepository;
import com.wakeUpTogetUp.togetUp.group.model.Group;
import com.wakeUpTogetUp.togetUp.mappingGroupUser.model.MappingGroupUser;
import com.wakeUpTogetUp.togetUp.users.UserRepository;
import com.wakeUpTogetUp.togetUp.users.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MappingGroupUserService {
    private final MappingGroupUserRepository mappingGroupUserRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    @Transactional
    public  Integer createGroupUser(GroupUserDto dto){


//        User user = userRepository.findById(dto.getUser());
//        Group group = groupRepository.findById(dto.getGroup());
        MappingGroupUser groupUser =dto.toEntity();
//        groupUser.setUser(user);
//        groupUser.setGroup(group);
        mappingGroupUserRepository.save(groupUser);
        return groupUser.getId();
    }
}
