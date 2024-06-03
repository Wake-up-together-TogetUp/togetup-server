package com.wakeUpTogetUp.togetUp.api.notification;

import com.wakeUpTogetUp.togetUp.api.event.EventPublisher;
import com.wakeUpTogetUp.togetUp.api.notification.vo.NotificationSendEvent;
import com.wakeUpTogetUp.togetUp.api.notification.vo.RoomMissionLogNotificationEvent;
import com.wakeUpTogetUp.togetUp.api.room.RoomRepository;
import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import com.wakeUpTogetUp.togetUp.api.room.model.RoomUser;
import com.wakeUpTogetUp.togetUp.api.users.UserRepository;
import com.wakeUpTogetUp.togetUp.api.users.UserService;
import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmTokenRepository;
import com.wakeUpTogetUp.togetUp.api.users.domain.User;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.infra.fcm.FcmService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final FcmService fcmService;
    private final UserService userService;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final FcmTokenRepository fcmTokenRepository;

    public void sendNotificationToAllUsers(String title, String body) {
        EventPublisher.raise(new NotificationSendEvent(
                fcmTokenRepository.findAllByUser_IdIn(userService.getAgreedNotiUsersIds()),
                title,
                body,
                Map.of(DataKeyType.LINK.getKey(), DataValueType.HOME.toString())
        ));

    }

    public void sendNotificationToUsersInRoom(Integer alarmId, Integer missionCompleteUserId) {
        User user = userRepository.findById(missionCompleteUserId)
                .orElseThrow(() -> new BaseException(Status.USER_NOT_FOUND));
        Room room = roomRepository.findByAlarmId(alarmId)
                .orElseThrow(() -> new BaseException(Status.ROOM_NOT_FOUND));

        List<Integer> userIdsInRoom = room.getRoomUsers().stream()
                .map(RoomUser::getUser)
                .map(User::getId)
                .filter(userId -> userId != user.getId())
                .collect(Collectors.toList());

        if (userIdsInRoom.size() > 0) {
            EventPublisher.raise(new RoomMissionLogNotificationEvent(
                    user,
                    room,
                    fcmTokenRepository.findAllByUser_IdIn(userIdsInRoom)
            ));
        }
    }

}
