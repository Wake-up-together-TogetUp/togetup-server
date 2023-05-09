package com.wakeUpTogetUp.togetUp.mappingGroupUser;


import com.wakeUpTogetUp.togetUp.alarms.model.Alarm;
import com.wakeUpTogetUp.togetUp.common.BaseException;
import com.wakeUpTogetUp.togetUp.common.BaseResponseStatus;
import com.wakeUpTogetUp.togetUp.mappingGroupUser.dto.request.MappingGroupUserReq;
import com.wakeUpTogetUp.togetUp.group.GroupRepository;
import com.wakeUpTogetUp.togetUp.group.model.Group;
import com.wakeUpTogetUp.togetUp.mappingGroupUser.dto.response.MappingGroupUserRes;
import com.wakeUpTogetUp.togetUp.mappingGroupUser.model.MappingGroupUser;
import com.wakeUpTogetUp.togetUp.users.UserRepository;
import com.wakeUpTogetUp.togetUp.users.model.User;
import com.wakeUpTogetUp.togetUp.utils.mappers.AlarmMapper;
import com.wakeUpTogetUp.togetUp.utils.mappers.MappingGroupUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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

    /**
     * 그룹아이디로 유저 가져오기
     * @param groupId
     * @return
     */
    public List<MappingGroupUserRes> getUserByGroupId(Integer groupId) {
        // 유저 가져오기
        List<MappingGroupUser> mappingGroupUserList= mappingGroupUserRepository.findByGroupId(groupId);
        System.out.println("매핑"+mappingGroupUserList);

       // MappingGroupUserRes  mappingGroupUserRes= MappingGroupUserMapper.INSTANCE.toMappingGroupUserRes(mappingGroupUser);
        // dto 매핑
        ArrayList<MappingGroupUserRes> mappingGroupUserResList = new ArrayList<>();
        for(MappingGroupUser mappingGroupUser : mappingGroupUserList) {
            mappingGroupUserResList.add(MappingGroupUserMapper.INSTANCE.toMappingGroupUserRes(mappingGroupUser));
        }

        return mappingGroupUserResList;
    }
}
