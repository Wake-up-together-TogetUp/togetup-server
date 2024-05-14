package com.wakeUpTogetUp.togetUp.api.users;

import static com.wakeUpTogetUp.togetUp.api.users.UserServiceHelper.findExistingUser;

import com.wakeUpTogetUp.togetUp.api.auth.LoginType;
import com.wakeUpTogetUp.togetUp.api.auth.dto.response.SocialUserRes;
import com.wakeUpTogetUp.togetUp.api.avatar.application.UserAvatarService;
import com.wakeUpTogetUp.togetUp.api.room.RoomUserRepository;
import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmToken;
import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmTokenRepository;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
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

    private final UserAvatarService userAvatarService;
    private final UserRepository userRepository;
    private final FcmTokenRepository fcmTokenRepository;
    private final RoomUserRepository roomUserRepository;

    public User getSignedUser(SocialUserRes socialUserRes, LoginType loginType) {
        User savedUser = userRepository.findBySocialId(socialUserRes.getId()).orElse(null);
        if (Objects.nonNull(savedUser)) {
            return savedUser;
        }

        User user = userRepository.save(
                User.builder()
                        .socialId(socialUserRes.getId())
                        .loginType(loginType)
                        .name(socialUserRes.getName())
                        .email(socialUserRes.getEmail())
                        .level(DEFAULT_USER_LEVEL)
                        .expPoint(DEFAULT_USER_EXP_POINT)
                        .build());
        userAvatarService.setUserDefaultAvatar(user);

        return user;
    }

    public void registerFcmToken(Integer userId, String fcmTokenValue) {
        User user = findExistingUser(userRepository, userId);

        if (!fcmTokenRepository.existsByValue(fcmTokenValue)) {
            saveFcmToken(user, fcmTokenValue);
        }
    }

    private void saveFcmToken(User user, String fcmTokenValue) {
        FcmToken fcmToken = FcmToken.builder()
                .value(fcmTokenValue)
                .user(user)
                .build();
        fcmTokenRepository.save(fcmToken);
    }

    @Transactional
    public void deleteFcmTokens(List<String> fcmTokenValues) {
        fcmTokenRepository.deleteByValueIn(fcmTokenValues);
    }

    public void updateAgreePush(Integer userId, boolean agreePush) {
        User user = findExistingUser(userRepository, userId);
        user.changeAgreePush(agreePush);
        userRepository.save(user);
    }

    @Transactional
    public void deleteById(Integer userId) {
        userRepository.findById(userId).orElseThrow(() -> new BaseException(Status.USER_NOT_FOUND));
        userRepository.deleteById(userId);

        Integer roomUserNumber = roomUserRepository.countByUserId(userId);

        if (roomUserNumber > 0) {
            roomUserRepository.deleteByUserId(userId);
        }
    }

    public List<Integer> getAgreedNotiUsersIds() {
        return userRepository.findAllByAgreePushIsTrue().stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }
}
