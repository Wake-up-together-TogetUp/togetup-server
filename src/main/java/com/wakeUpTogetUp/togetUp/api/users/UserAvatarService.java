package com.wakeUpTogetUp.togetUp.api.users;

import static com.wakeUpTogetUp.togetUp.common.Constant.DEFAULT_AVATAR_ID;

import com.wakeUpTogetUp.togetUp.api.avatar.AvatarRepository;
import com.wakeUpTogetUp.togetUp.api.avatar.model.Avatar;
import com.wakeUpTogetUp.togetUp.api.avatar.vo.UserAvatarData;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import com.wakeUpTogetUp.togetUp.api.users.model.UserAvatar;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.utils.mapper.EntityDtoMapper;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAvatarService {

    private final AvatarRepository avatarRepository;
    private final UserAvatarRepository userAvatarRepository;

    public List<UserAvatarData> findUserAvatarList(int userId) {
        List<Avatar> avatarList = avatarRepository.findAll();
        List<UserAvatar> userAvatarList = userAvatarRepository.findAllByUser_Id(userId);

        return EntityDtoMapper.INSTANCE.toUserAvatarDataList(avatarList, userAvatarList);
    }

    @Transactional
    public void unlockAvatarIfAvailable(User user) {
        Avatar avatarAvailable = avatarRepository.findAvatarByUnlockLevel(user.getLevel())
                .orElse(null);

        if (Objects.nonNull(avatarAvailable)) {
            unlockAvatar(user, avatarAvailable);
        }
    }

    private void unlockAvatar(User user, Avatar avatar) {
        userAvatarRepository.findByUserAndAvatar(user, avatar)
                .ifPresent(value -> {
                    throw new BaseException(Status.USER_AVATAR_ALREADY_EXIST);
                });

        createUserAvatar(user, avatar);
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
            // 해당 아바타이면 활성화, 나머지 비활성화
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
