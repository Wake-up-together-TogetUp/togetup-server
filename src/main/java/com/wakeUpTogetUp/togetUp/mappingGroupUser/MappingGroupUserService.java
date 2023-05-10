package com.wakeUpTogetUp.togetUp.mappingGroupUser;


import com.wakeUpTogetUp.togetUp.common.exception.BaseException;
import com.wakeUpTogetUp.togetUp.common.ResponseStatus;

import com.wakeUpTogetUp.togetUp.mappingGroupUser.dto.request.MappingGroupUserReq;
import com.wakeUpTogetUp.togetUp.group.GroupRepository;
import com.wakeUpTogetUp.togetUp.group.model.Group;
import com.wakeUpTogetUp.togetUp.mappingGroupUser.dto.response.MappingGroupUserRes;
import com.wakeUpTogetUp.togetUp.mappingGroupUser.model.MappingGroupUser;
import com.wakeUpTogetUp.togetUp.users.UserRepository;
import com.wakeUpTogetUp.togetUp.users.model.User;
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
                .orElseThrow(() -> new BaseException(ResponseStatus.INVALID_USER_ID)
                );
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new BaseException(ResponseStatus.INVALID_GROUP_ID)
                );
        //dto->entitiy
        MappingGroupUser groupUser =mappingGroupUserReq.toEntity(user,group);
        //저장
        mappingGroupUserRepository.save(groupUser);
        return groupUser.getId();
    }

    /**
     * 그룹아이디로 그룹유저 가져오기
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
    /**
     * 유저아이디로 그룹유저 가져오기
     * @param userId
     * @return
     */
    public List<MappingGroupUserRes> getGroupByUserId(Integer userId) {
        // 유저 가져오기
        List<MappingGroupUser> mappingGroupUserList= mappingGroupUserRepository.findByUserId(userId);


        // dto 매핑
        ArrayList<MappingGroupUserRes> mappingGroupUserResList = new ArrayList<>();
        for(MappingGroupUser mappingGroupUser : mappingGroupUserList) {
            mappingGroupUserResList.add(MappingGroupUserMapper.INSTANCE.toMappingGroupUserRes(mappingGroupUser));
        }

        return mappingGroupUserResList;
    }


    /**
     * 그룹유저 수정  : ex) 알림여부, 미션여부
     * @param userId
     * @param groupId
     * @param mappingGroupUserReq
     * @return
     */

    @Transactional
    public MappingGroupUserRes editMappingGroupUser(Integer userId, Integer groupId, MappingGroupUserReq mappingGroupUserReq) {


        // 그룹유저 수정  ex) 알림을 키던가, 개인미션알림 설정
        MappingGroupUser mappingGroupUser= mappingGroupUserRepository.findByUserIdAndGroupId(userId,groupId);
        mappingGroupUser.setIsPersonalNotice(mappingGroupUserReq.getIsPersonalNotice());
        mappingGroupUser.setIsNotice(mappingGroupUserReq.getIsNotice());
        mappingGroupUser.setIsHostUser(mappingGroupUserReq.getIsHostUser());


        //저장
        MappingGroupUser mappingGroupUserModified = mappingGroupUserRepository.save(mappingGroupUser);


        MappingGroupUserRes mappingGroupUserRes = MappingGroupUserMapper.INSTANCE.toMappingGroupUserRes(mappingGroupUserModified);

        // return
        return mappingGroupUserRes;
    }

    /**
     *  그룹 탈퇴
     */
    @Transactional
    public void deleteMappingGroupUser(Integer userId,Integer groupId) {
        // TODO : 없으면 예외처리

        Integer cnt=mappingGroupUserRepository.deleteByUserIdAndGroupId(userId,groupId);

        if(cnt==0)
        {
            new BaseException(ResponseStatus.BAD_REQUEST);
        }
    }

}
