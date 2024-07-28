package com.wakeUpTogetUp.togetUp.api.notification;

import com.wakeUpTogetUp.togetUp.api.event.EventPublisher;
import com.wakeUpTogetUp.togetUp.api.notification.dto.response.NotificationListRes;
import com.wakeUpTogetUp.togetUp.api.notification.entity.Notification;
import com.wakeUpTogetUp.togetUp.api.notification.vo.AvatarUnlockedNotificationEvent;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final UserService userService;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final FcmTokenRepository fcmTokenRepository;
    private final NotificationRepository notificationRepository;

    public void sendNotificationToAllUsers(String title, String body) {
        EventPublisher.raise(new NotificationSendEvent(
                fcmTokenRepository.findAllByUser_IdIn(userService.getAgreedNotiUsersIds()),
                title,
                body,
                Map.of(NotificationDataKeyType.LINK.getKey(), NotificationDataValueType.HOME.toString())
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

    public void sendNotificationToAvatarUnlockedUser(Integer userId) {

        EventPublisher.raise(new AvatarUnlockedNotificationEvent(fcmTokenRepository.findAllByUser_Id(userId)));
    }

    public NotificationListRes getList(Integer userId) {
        List<Notification> notificationList = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return NotificationListRes.from(notificationList);
    }

    @Transactional
    public void updateIsReadToTrue(Integer userId, Integer notificationId) {
        Notification notification = notificationRepository.findByUserIdAndId(userId, notificationId);
        notification.readNotification();
    }

    @Transactional
    public void delete(Integer userId, Integer notificationId) {
        notificationRepository.deleteByUserIdAndId(userId, notificationId);
    }

}
