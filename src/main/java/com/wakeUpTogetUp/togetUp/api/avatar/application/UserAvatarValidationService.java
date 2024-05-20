package com.wakeUpTogetUp.togetUp.api.avatar.application;

import com.wakeUpTogetUp.togetUp.api.avatar.domain.UserAvatar;
import com.wakeUpTogetUp.togetUp.api.avatar.repository.UserAvatarRepository;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAvatarValidationService {

    private final AvatarValidationService avatarValidationService;

    private final UserAvatarRepository userAvatarRepository;

    public boolean isUserAvatarNotExist(int userId, int avatarId) {
        return userAvatarRepository.findByUser_IdAndAvatar_Id(userId, avatarId)
                .isEmpty();
    }

    public void validateUserAvatarActive(int userId, int avatarId) {
        avatarValidationService.validateAvatarExist(avatarId);

        UserAvatar userAvatar = userAvatarRepository.findByUser_IdAndAvatar_Id(userId, avatarId)
                .orElseThrow(() -> new BaseException(Status.USER_AVATAR_LOCKED));

        if (!userAvatar.getIsActive()) {
            throw new BaseException(Status.USER_AVATAR_NOT_ACTIVE);
        }
    }
}
