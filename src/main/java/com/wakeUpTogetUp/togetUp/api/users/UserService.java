package com.wakeUpTogetUp.togetUp.api.users;

import com.wakeUpTogetUp.togetUp.api.auth.LoginType;
import com.wakeUpTogetUp.togetUp.api.auth.dto.response.SocialUserRes;
import com.wakeUpTogetUp.togetUp.api.room.RoomUserRepository;
import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmToken;
import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmTokenRepository;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import com.wakeUpTogetUp.togetUp.api.users.vo.UserStat;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {

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
        return userRepository.save(
                User.builder()
                        .socialId(socialUserRes.getId())
                        .loginType(loginType)
                        .name(socialUserRes.getName())
                        .email(socialUserRes.getEmail())
                        .build());
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

    public UserStat userProgress(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(Status.USER_NOT_FOUND));

        user.gainExperience(10);
        int threshold = 10 + 16 * (user.getLevel() - 1);

        if (user.checkUserLevelUpAvailable(threshold)) {
            user.levelUp(threshold);
        }
        userRepository.save(user);

        return new UserStat(user.getLevel(), user.getExperience(), user.getPoint());
    }

    public UserStat getUserStat(User user) {
        return UserStat.builder()
                .level(user.getLevel())
                .experience(user.getExperience())
                .point(user.getPoint())
                .build();
    }
}
