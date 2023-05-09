package com.wakeUpTogetUp.togetUp.mappingGroupUser;


import com.wakeUpTogetUp.togetUp.common.BaseException;
import com.wakeUpTogetUp.togetUp.common.BaseResponseStatus;
import com.wakeUpTogetUp.togetUp.mappingGroupUser.dto.request.MappingGroupUserReq;
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
    public  Integer createGroupUser(Integer userId, Integer groupId,MappingGroupUserReq mappingGroupUserReq){

        //조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_USER_ID)
                );
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.INVALID_GROUP_ID)
                );
        //dto->entitiy
        MappingGroupUser groupUser =mappingGroupUserReq.toEntity(user,group);
        //저장
        mappingGroupUserRepository.save(groupUser);
        return groupUser.getId();
    }
}
