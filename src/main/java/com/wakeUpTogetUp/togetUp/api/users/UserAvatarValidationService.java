package com.wakeUpTogetUp.togetUp.api.users;

import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAvatarValidationService {

    private final UserAvatarRepository userAvatarRepository;

    public void validateUserHasAvatar(int userId, int avatarId) {
        if (isUserAvatarNotExist(userId, avatarId)) {
            throw new BaseException(Status.USER_AVATAR_LOCKED);
        }
    }

    public boolean isUserAvatarNotExist(int userId, int avatarId) {
        return userAvatarRepository.findByUser_IdAndAvatar_Id(userId, avatarId)
                .isEmpty();
    }
}
