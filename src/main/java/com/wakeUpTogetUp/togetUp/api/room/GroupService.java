package com.wakeUpTogetUp.togetUp.api.room;

import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.api.room.dto.request.GroupReq;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.GroupRes;
import com.wakeUpTogetUp.togetUp.utils.mapper.GroupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public  Integer createGroup(GroupReq dto){
      //  dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        String topic = UUID.randomUUID().toString();
        Room room =dto.toEntity(topic);
        groupRepository.save(room);

        return room.getId();
    }
    public List<GroupRes> getGroup() {

        //모든 그룹 가져오기
        List<Room> roomList = groupRepository.findAll();

        // dto 매핑
        ArrayList<GroupRes> groupResList = new ArrayList<>();
        for(Room room : roomList) {
            groupResList.add(GroupMapper.INSTANCE.toGroupRes(room));
        }

        return groupResList;
    }

    @Transactional
    public GroupRes editGroup(Integer groupId , GroupReq groupReq){
    // 그룹수정 이름, 인트로
        Optional<Room> group = Optional.ofNullable(groupRepository.findById(groupId)
                .orElseThrow(
                        () -> new BaseException(Status.INVALID_GROUP_ID)
                ));
        group.get().setName(groupReq.getName());
        group.get().setIntro(groupReq.getIntro());

    //저장
        Room modifiedRoom =groupRepository.save(group.get());


    //반환
        GroupRes groupRes = GroupMapper.INSTANCE.toGroupRes(modifiedRoom);

        return groupRes;
    }

    /**
     *  그룹 삭제
     */
    @Transactional
    public void deleteGroup(Integer groupId) {
        // TODO : 없으면 예외처리

        groupRepository.deleteById(groupId);

        // TODO : topic 삭제
    }



}
