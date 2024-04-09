package com.wakeUpTogetUp.togetUp.api.avatar.application;

import com.wakeUpTogetUp.togetUp.api.avatar.repository.AvatarRepository;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvatarValidationService {

    private final AvatarRepository avatarRepository;

    public void validateAvatarExist(int avatarId) {
        if (!avatarRepository.existsById(avatarId)) {
            throw new BaseException(Status.AVATAR_NOT_FOUND);
        }
    }
}
