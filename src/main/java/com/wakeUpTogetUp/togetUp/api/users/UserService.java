package com.wakeUpTogetUp.togetUp.api.users;

import com.wakeUpTogetUp.togetUp.api.auth.service.AuthService;
import com.wakeUpTogetUp.togetUp.api.avatar.AvatarRepository;
import com.wakeUpTogetUp.togetUp.api.avatar.model.Avatar;
import com.wakeUpTogetUp.togetUp.api.room.RoomUserRepository;
import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmToken;
import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmTokenRepository;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import com.wakeUpTogetUp.togetUp.api.users.model.UserAvatar;
import com.wakeUpTogetUp.togetUp.api.users.vo.UserProgressionResult;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final FcmTokenRepository fcmTokenRepository;
    private final RoomUserRepository roomUserRepository;
    private final AvatarRepository avatarRepository;
    private final UserAvatarRepository userAvatarRepository;

    public Integer updateFcmToken(Integer userId, Integer fcmTokenId, String fcmToken) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(Status.USER_NOT_FOUND));
        FcmToken fcmTokenObject;
        if (fcmTokenId == null) {
            fcmTokenObject = FcmToken.builder()
                    .value(fcmToken)
                    .user(user)
                    .build();
        } else {
            fcmTokenObject = fcmTokenRepository.findById(fcmTokenId).orElse(
                    FcmToken.builder()
                            .value(fcmToken)
                            .user(user)
                            .build());
            fcmTokenObject.updateFcmToken(fcmToken);
        }

        return fcmTokenRepository.save(fcmTokenObject).getId();

    }

    public void updateAgreePush(Integer userId, boolean agreePush) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(Status.USER_NOT_FOUND));
        user.setAgreePush(agreePush);
        userRepository.save(user);
    }

    @Transactional
    public void deleteById(Integer userId) {
        userRepository.findById(userId).orElseThrow(() -> new BaseException(Status.USER_NOT_FOUND));
        userRepository.deleteById(userId);

        //roomUser 삭제
        Integer roomUserNumber = roomUserRepository.countByUserId(userId);

        if (roomUserNumber > 0) {
            roomUserRepository.deleteByUserId(userId);
        }

    }

    @Transactional
    public void deleteAppleUser(Integer userId, String authorizationCode) throws IOException {

        authService.disconnectApple(authorizationCode);
        this.deleteById(userId);
    }

    // 경험치, 레벨 정산
    @Transactional
    public UserProgressionResult userProgress(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(Status.USER_NOT_FOUND));

        user.gainExperience(10);
        int threshold = 10 + 16 * (user.getLevel() - 1);

        // 레벨 업 가능하면,
        if (user.checkUserLevelUpAvailable(threshold)) {
            user.levelUp(threshold);
        }
        userRepository.save(user);

        return new UserProgressionResult(user.getLevel(), user.getExperience(), user.getPoint());
    }

    // 유저 아바타 해금
    @Transactional
    public void unlockAvatar(User user, int avatarId) {
        Avatar avatar = avatarRepository.findById(avatarId)
                .orElseThrow(() -> new BaseException(Status.AVATAR_NOT_FOUND));

        UserAvatar userAvatar = UserAvatar.builder()
                .user(user)
                .avatar(avatar)
                .build();
        userAvatarRepository.save(userAvatar);
    }

    // 유저 아바타 변경
    @Transactional
    public void changeUserAvatar(User user, int avatarId) {
        List<UserAvatar> userAvatarList =
                userAvatarRepository.findAllByUser_IdAndAvatar_Id(user.getId(), avatarId);

        // 보유중인 아바타인지 검사
        userAvatarList.stream().filter(i -> i.getAvatar().getId() == avatarId).findAny()
                .orElseThrow(() -> new BaseException(Status.USER_AVATAR_LOCKED));

        for(UserAvatar userAvatar : userAvatarList) {
            if(userAvatar.getAvatar().getId() == avatarId) {
                // 해당 아바타 활성화
                userAvatar.activate();
            } else {
                // 다른 보유 아바타 비활성화
                userAvatar.inactivate();
            }
            userAvatarRepository.save(userAvatar);
        }
    }
}
