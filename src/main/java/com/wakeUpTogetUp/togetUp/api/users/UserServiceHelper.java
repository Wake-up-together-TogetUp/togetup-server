package com.wakeUpTogetUp.togetUp.api.users;

import com.wakeUpTogetUp.togetUp.api.users.model.User;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;

public final class UserServiceHelper {
    public static User findExistingMember(UserRepository userRepository, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new BaseException(Status.USER_NOT_FOUND));
        return user;
    }
}
