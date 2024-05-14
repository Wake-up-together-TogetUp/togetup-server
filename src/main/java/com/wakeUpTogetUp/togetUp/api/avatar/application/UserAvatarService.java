package com.wakeUpTogetUp.togetUp.api.avatar.application;

import static com.wakeUpTogetUp.togetUp.common.Constant.DEFAULT_AVATAR_ID;

import com.wakeUpTogetUp.togetUp.api.avatar.application.model.UnlockAvatarResult;
import com.wakeUpTogetUp.togetUp.api.avatar.model.Avatar;
import com.wakeUpTogetUp.togetUp.api.avatar.model.UserAvatar;
import com.wakeUpTogetUp.togetUp.api.avatar.repository.AvatarRepository;
import com.wakeUpTogetUp.togetUp.api.avatar.repository.UserAvatarRepository;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserAvatarService {

    private final AvatarRepository avatarRepository;
    private final UserAvatarRepository userAvatarRepository;
    private final UserAvatarValidationService userAvatarValidationService;

    private void createUserAvatar(User user, Avatar avatar) {
        UserAvatar userAvatar = UserAvatar.builder()
                .user(user)
                .avatar(avatar)
                .build();

        userAvatarRepository.save(userAvatar);
    }

    public void setUserDefaultAvatar(User user) {
        Avatar defaultAvatar = avatarRepository.findById(DEFAULT_AVATAR_ID)
                .orElseThrow(() -> new BaseException(Status.FIND_DEFAULT_AVATAR_FAIL));

        createUserAvatar(user, defaultAvatar);
        changeUserAvatar(user.getId(), DEFAULT_AVATAR_ID);
    }

    public void changeUserAvatar(int userId, int avatarId) {
        List<UserAvatar> userAvatarList = userAvatarRepository.findAllByUser_Id(userId);

        userAvatarList.stream()
                .filter(i -> i.getAvatar().getId() == avatarId)
                .findAny()
                .orElseThrow(() -> new BaseException(Status.USER_AVATAR_LOCKED));

        for (UserAvatar userAvatar : userAvatarList) {
            userAvatar.setIsActive(userAvatar.getAvatar().getId() == avatarId);
        }
    }

    public UnlockAvatarResult attemptToUnlockAvatar(User user) {
        Optional<Avatar> avatarAvailable = avatarRepository.findAvatarByUnlockLevel(user.getLevel());

        if (avatarAvailable.isPresent()) {
            unlockAvatar(user, avatarAvailable.get());
            return new UnlockAvatarResult(true);
        }

        return new UnlockAvatarResult(false);
    }

    private void unlockAvatar(User user, Avatar avatar) {
        if (userAvatarValidationService.isUserAvatarNotExist(user.getId(), avatar.getId())) {
            createUserAvatar(user, avatar);
        }
    }
}
