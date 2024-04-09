package com.wakeUpTogetUp.togetUp.api.users;

import static com.wakeUpTogetUp.togetUp.common.Constant.DEFAULT_AVATAR_ID;

import com.wakeUpTogetUp.togetUp.api.avatar.AvatarRepository;
import com.wakeUpTogetUp.togetUp.api.avatar.model.Avatar;
import com.wakeUpTogetUp.togetUp.api.avatar.dto.response.UserAvatarResponse;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import com.wakeUpTogetUp.togetUp.api.users.model.UserAvatar;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.utils.mapper.EntityDtoMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAvatarService {

    private final AvatarRepository avatarRepository;
    private final UserAvatarRepository userAvatarRepository;

    @Transactional(readOnly = true)
    public List<UserAvatarResponse> findUserAvatarList(int userId) {
        List<Avatar> avatarList = avatarRepository.findAll();
        List<UserAvatar> userAvatarList = userAvatarRepository.findAllByUser_Id(userId);

        return EntityDtoMapper.INSTANCE.toUserAvatarDataList(avatarList, userAvatarList);
    }


    @Transactional
    public boolean unlockAvatarIfAvailableExist(User user) {
        Optional<Avatar> avatarAvailable = avatarRepository.findAvatarByUnlockLevel(user.getLevel());
        avatarAvailable.ifPresent(avatar -> unlockAvatar(user, avatar));

        return avatarAvailable.isPresent();
    }

    private void unlockAvatar(User user, Avatar avatar) {
        if (isUserAvatarNotExist(user, avatar)) {
            createUserAvatar(user, avatar);
        }
    }

    private boolean isUserAvatarNotExist(User user, Avatar avatar) {
        return userAvatarRepository.findByUserAndAvatar(user, avatar)
                .isEmpty();
    }

    private void createUserAvatar(User user, Avatar avatar) {
        UserAvatar userAvatar = UserAvatar.builder()
                .user(user)
                .avatar(avatar)
                .build();

        userAvatarRepository.save(userAvatar);
    }

    @Transactional
    public void setUserDefaultAvatar(User user) {
        Avatar defaultAvatar = avatarRepository.findById(DEFAULT_AVATAR_ID)
                .orElseThrow(() -> new BaseException(Status.FIND_DEFAULT_AVATAR_FAIL));

        createUserAvatar(user, defaultAvatar);
        changeUserAvatar(user.getId(), DEFAULT_AVATAR_ID);
    }

    @Transactional
    public void changeUserAvatar(int userId, int avatarId) {
        List<UserAvatar> userAvatarList =
                userAvatarRepository.findAllByUser_Id(userId);

        userAvatarList.stream()
                .filter(i -> i.getAvatar().getId() == avatarId)
                .findAny()
                .orElseThrow(() -> new BaseException(Status.USER_AVATAR_LOCKED));

        for (UserAvatar userAvatar : userAvatarList) {
            userAvatar.setIsActive(userAvatar.getAvatar().getId() == avatarId);
            userAvatarRepository.save(userAvatar);
        }
    }

    public int getUserAvatarId(int userId) {
        return userAvatarRepository.findByUser_IdAndIsActiveIsTrue(userId)
                .orElseThrow(() -> new BaseException(Status.FIND_USER_AVATAR_FAIL))
                .getAvatar()
                .getId();
    }
}
