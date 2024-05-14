package com.wakeUpTogetUp.togetUp.api.users;

import org.springframework.stereotype.Service;

import com.wakeUpTogetUp.togetUp.api.users.domain.User;
import com.wakeUpTogetUp.togetUp.api.users.domain.model.UserProgressResult;

@Service
public class UserProgressService {

    private static final int EXP_GAINED_PER_MISSION = 10;

    public UserProgressResult progress(User user) {
        return user.progress(EXP_GAINED_PER_MISSION);
    }
}
