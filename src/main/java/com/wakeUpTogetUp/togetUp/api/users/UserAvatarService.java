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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAvatarService {

    private final UserRepository userRepository;
    private final AvatarRepository avatarRepository;
    private final UserAvatarRepository userAvatarRepository;

    public List<UserAvatarData> findUserAvatarList(int userId) {
        List<Avatar> avatarList = avatarRepository.findAll();
        List<UserAvatar> userAvatarList = userAvatarRepository.findAllByUser_Id(userId);

        return EntityDtoMapper.INSTANCE.toUserAvatarDataList(avatarList, userAvatarList);
    }

    @Transactional
    public void unlockAvatar(int userId, int avatarId) {
        userAvatarRepository.findByUser_IdAndAvatar_Id(userId, avatarId)
                .ifPresent(value -> {
                    throw new BaseException(Status.USER_AVATAR_ALREADY_EXIST);
                });
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(Status.USER_NOT_FOUND));
        Avatar avatar = avatarRepository.findById(avatarId)
                .orElseThrow(() -> new BaseException(Status.AVATAR_NOT_FOUND));

        user.purchaseAvatar(avatar);
        this.createUserAvatar(user, avatarId);
        userRepository.save(user);
    }

    @Transactional
    public void setUserDefaultAvatar(User user) {
        createUserAvatar(user, DEFAULT_AVATAR_ID);
        changeUserAvatar(user.getId(), DEFAULT_AVATAR_ID);
    }

    @Transactional
    public void createUserAvatar(User user, int avatarId) {
        Avatar avatar = avatarRepository.findById(avatarId)
                .orElseThrow(() -> new BaseException(Status.AVATAR_NOT_FOUND));

        UserAvatar userAvatar = UserAvatar.builder()
                .user(user)
                .avatar(avatar)
                .build();
        userAvatarRepository.save(userAvatar);
    }

    @Transactional
    public void changeUserAvatar(int userId, int avatarId) {
        List<UserAvatar> userAvatarList =
                userAvatarRepository.findAllByUser_Id(userId);

        userAvatarList.stream().filter(i -> i.getAvatar().getId() == avatarId).findAny()
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
