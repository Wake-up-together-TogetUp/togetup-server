package com.wakeUpTogetUp.togetUp.api.fcmNotification;

import com.wakeUpTogetUp.togetUp.api.fcmNotification.dto.request.PushNotificationReq;
import com.wakeUpTogetUp.togetUp.api.fcmNotification.dto.response.PushNotificationRes;
import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.api.room.RoomRepository;
import com.wakeUpTogetUp.togetUp.api.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final FcmService fcmService;
    private final PushLogService pushLogService;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    // 그룹 채팅 알림 보내기
    @Transactional
    public PushNotificationRes sendMessageToGroup(int groupId, PushNotificationReq request)
            throws ExecutionException, InterruptedException {
        // group 조회
        Room room = roomRepository.findById(groupId)
                .orElseThrow(
                        () -> new BaseException(Status.INVALID_GROUP_ID)
                );
        request.setTopic(room.getTopic());

        // 알림 보내기
        PushNotificationRes pushNotificationRes = fcmService.sendMessageToTopic(request);

        // TODO : 리팩토링?
        // user list 조회
//        List<User> userList = new ArrayList();
//        for(RoomUser mgu : room.getMappingGroupUsers()){
//            userList.add(mgu.getUser());
//            System.out.println(mgu.getUser());
//        }
//
//        // pushLog 생성
//        for(User user : userList)
//            pushLogService.createPushLog(user, request);

        return pushNotificationRes;
    }
}
