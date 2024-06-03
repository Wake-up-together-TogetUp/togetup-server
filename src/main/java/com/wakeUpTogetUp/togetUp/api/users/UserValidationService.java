package com.wakeUpTogetUp.togetUp.api.users;

import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserValidationService {

    private final UserRepository userRepository;

    public void validateUserExist(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new BaseException(Status.USER_NOT_FOUND);
        }
    }
}
