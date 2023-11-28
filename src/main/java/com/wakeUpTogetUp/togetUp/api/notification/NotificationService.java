package com.wakeUpTogetUp.togetUp.api.notification;

import com.wakeUpTogetUp.togetUp.api.notification.dto.request.PushNotificationReq;
import com.wakeUpTogetUp.togetUp.api.notification.dto.response.PushNotificationRes;
import com.wakeUpTogetUp.togetUp.api.notification.vo.NotificationSendVo;
import com.wakeUpTogetUp.togetUp.api.notification.vo.RoomMissionLogNotificationVo;
import com.wakeUpTogetUp.togetUp.api.room.RoomService;
import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import com.wakeUpTogetUp.togetUp.api.room.model.RoomUser;
import com.wakeUpTogetUp.togetUp.api.users.UserService;
import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmTokenRepository;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.api.room.RoomRepository;
import com.wakeUpTogetUp.togetUp.api.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final FcmService fcmService;
    private final UserService userService;
    private final RoomService roomService;
    //private final PushLogService pushLogService;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final FcmTokenRepository fcmTokenRepository;

    public void sendNotificationToAllUsers(String title, String body) {
        fcmService.sendMessageToTokens(
                new NotificationSendVo(
                        fcmTokenRepository.findAllByUser_IdIn(userService.getAgreedNotiUsersIds()),
                        title,
                        body,
                        Map.of(DataKeyType.LINK.getKey(), DataValueType.HOME.toString())
                )
        );
    }

    public void sendNotificationToUsersInRoom(Room room, User user) {
        List<Integer> userIdsInRoom = room.getRoomUsers().stream()
                .map(RoomUser::getUser)
                .map(User::getId)
                .filter(userId -> userId != user.getId())
                .collect(Collectors.toList());

        if (userIdsInRoom.size() > 0)
            fcmService.sendMessageToTokens(
                    new RoomMissionLogNotificationVo(
                            user,
                            room,
                            fcmTokenRepository.findAllByUser_IdIn(userIdsInRoom)

                    )
            );
    }


    //=======================================================
    // 그룹 채팅 알림 보내기
//    @Transactional
//    public PushNotificationRes sendMessageToGroup(int groupId, PushNotificationReq request)
//            throws ExecutionException, InterruptedException {
//        // group 조회
//        Room room = roomRepository.findById(groupId)
//                .orElseThrow(
//                        () -> new BaseException(Status.INVALID_GROUP_ID)
//                );
//        request.setTopic(room.getTopic());
//
//        // 알림 보내기
//        PushNotificationRes pushNotificationRes = fcmService.sendMessageToTopic(request);
//
//        // TODO : 리팩토링?
//        // user list 조회
////        List<User> userList = new ArrayList();
////        for(RoomUser mgu : room.getMappingGroupUsers()){
////            userList.add(mgu.getUser());
////            System.out.println(mgu.getUser());
////        }
////
////        // pushLog 생성
////        for(User user : userList)
////            pushLogService.createPushLog(user, request);
//
//        return pushNotificationRes;
//    }
}
