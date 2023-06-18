package com.wakeUpTogetUp.togetUp.fcmNotification;

import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.fcmNotification.dto.request.PushNotificationReq;
import com.wakeUpTogetUp.togetUp.fcmNotification.dto.response.PushNotificationRes;
import com.wakeUpTogetUp.togetUp.group.GroupRepository;
import com.wakeUpTogetUp.togetUp.group.model.Group;
import com.wakeUpTogetUp.togetUp.mappingGroupUser.MappingGroupUserRepository;
import com.wakeUpTogetUp.togetUp.mappingGroupUser.model.MappingGroupUser;
import com.wakeUpTogetUp.togetUp.users.UserRepository;
import com.wakeUpTogetUp.togetUp.users.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final FcmService fcmService;
    private final PushLogService pushLogService;
    private final GroupRepository groupRepository;
    private final MappingGroupUserRepository mappingGroupUserRepository;
    private final UserRepository userRepository;

    // 그룹 채팅 알림 보내기
//    public void sendMessageToGroup(int groupId, PushNotificationReq request) throws ExecutionException, InterruptedException {
//        // group 조회
//        Group group = groupRepository.findById(groupId)
//                .orElseThrow(
//                        () -> new BaseException(Status.INVALID_GROUP_ID)
//                );
//        request.setTopic(group.getTopic());
//
//        // 알림 보내기
//        fcmService.sendMessageToTopic(request);
//
//        // TODO : 리팩토링?
//        // user list 조회
//        List<User> userList = List.of();
//        for(MappingGroupUser mgu : group.getMappingGroupUsers()){
//            userList.add(mgu.getUser());
//        }
//
//        // pushLog 생성
//        for(User user : userList)
//            pushLogService.createPushLog(user, request);
//    }

    // 그룹 채팅 알림 보내기 arrange ver
    @Transactional
    public PushNotificationRes sendMessageToGroup(int groupId, PushNotificationReq request)
            throws ExecutionException, InterruptedException {
        // group 조회
        Group group = groupRepository.findById(groupId)
                .orElseThrow(
                        () -> new BaseException(Status.INVALID_GROUP_ID)
                );
        request.setTopic(group.getTopic());

        // 알림 보내기
        PushNotificationRes pushNotificationRes = fcmService.sendMessageToTopic(request);

        // TODO : 리팩토링?
        // user list 조회
        List<User> userList = new ArrayList();
        for(MappingGroupUser mgu : group.getMappingGroupUsers()){
            userList.add(mgu.getUser());
            System.out.println(mgu.getUser());
        }

        // pushLog 생성
        for(User user : userList)
            pushLogService.createPushLog(user, request);

        return pushNotificationRes;
    }
}
