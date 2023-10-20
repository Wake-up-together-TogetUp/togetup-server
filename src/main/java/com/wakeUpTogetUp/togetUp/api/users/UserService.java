package com.wakeUpTogetUp.togetUp.api.users;

import com.wakeUpTogetUp.togetUp.api.auth.service.AuthService;
import com.wakeUpTogetUp.togetUp.api.avatar.AvatarTheme;
import com.wakeUpTogetUp.togetUp.api.avatar.vo.UserAvatarData;
import com.wakeUpTogetUp.togetUp.api.room.RoomUserRepository;
import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmToken;
import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmTokenRepository;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import com.wakeUpTogetUp.togetUp.api.users.vo.UserProgressionResult;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.utils.mapper.EntityDtoMapper;
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
}
