package com.wakeUpTogetUp.togetUp.group;

import com.wakeUpTogetUp.togetUp.common.ResponseStatus;
import com.wakeUpTogetUp.togetUp.common.exception.BaseException;
import com.wakeUpTogetUp.togetUp.group.dto.request.GroupReq;
import com.wakeUpTogetUp.togetUp.group.dto.response.GroupRes;
import com.wakeUpTogetUp.togetUp.group.model.Group;
import com.wakeUpTogetUp.togetUp.utils.mappers.GroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public  Integer createGroup(GroupReq dto){
        dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        Group group =dto.toEntity();
        groupRepository.save(group);
        return group.getId();
    }
    public List<GroupRes> getGroup() {

        //모든 그룹 가져오기
        List<Group> groupList= groupRepository.findAll();

        // dto 매핑
        ArrayList<GroupRes> groupResList = new ArrayList<>();
        for(Group group : groupList) {
            groupResList.add(GroupMapper.INSTANCE.toGroupRes(group));
        }

        return groupResList;
    }

    @Transactional
    public GroupRes editGroup(Integer groupId , GroupReq groupReq){
    // 그룹수정 이름, 인트로
        Optional<Group> group = Optional.ofNullable(groupRepository.findById(groupId)
                .orElseThrow(
                        () -> new BaseException(ResponseStatus.INVALID_GROUP_ID)
                ));
        group.get().setName(groupReq.getName());
        group.get().setIntro(groupReq.getIntro());

    //저장
        Group modifiedGroup =groupRepository.save(group.get());


    //반환
        GroupRes groupRes = GroupMapper.INSTANCE.toGroupRes(modifiedGroup);

        return groupRes;
    }

    /**
     *  그룹 삭제
     */
    @Transactional
    public void deleteGroup(Integer groupId) {
        // TODO : 없으면 예외처리

        groupRepository.deleteById(groupId);


    }



}
