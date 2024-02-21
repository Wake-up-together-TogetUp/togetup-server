package com.wakeUpTogetUp.togetUp.api.users;

import com.wakeUpTogetUp.togetUp.api.auth.LoginType;
import com.wakeUpTogetUp.togetUp.api.auth.dto.response.SocialUserRes;
import com.wakeUpTogetUp.togetUp.api.room.RoomUserRepository;
import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmToken;
import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmTokenRepository;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import com.wakeUpTogetUp.togetUp.api.users.vo.UserProgressResult;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final int DEFAULT_USER_LEVEL = 1;
    private static final int DEFAULT_USER_EXP_POINT = 0;
    private static final int DEFAULT_USER_COIN = 0;

    private final UserAvatarService userAvatarService;
    private final UserRepository userRepository;
    private final FcmTokenRepository fcmTokenRepository;
    private final RoomUserRepository roomUserRepository;

    public User getSignedUser(SocialUserRes socialUserRes, LoginType loginType) {
        User savedUser = userRepository.findBySocialId(socialUserRes.getId()).orElse(null);
        //유저가 있으면 아이디 반환
        if (Objects.nonNull(savedUser)) {
            return savedUser;
        }

        // 유저 저장
        User user = userRepository.save(
                User.builder()
                        .socialId(socialUserRes.getId())
                        .loginType(loginType)
                        .name(socialUserRes.getName())
                        .email(socialUserRes.getEmail())
                        .level(DEFAULT_USER_LEVEL)
                        .expPoint(DEFAULT_USER_EXP_POINT)
                        .coin(DEFAULT_USER_COIN)
                        .build());
        userAvatarService.setUserDefaultAvatar(user);

        return user;
    }

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

    public UserProgressResult userProgress(User user) {
        UserProgressResult result = user.progress();
        userRepository.save(user);

        return result;
    }

    public List<Integer> getAgreedNotiUsersIds() {
        return userRepository.findAllByAgreePushIsTrue().stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }
}
