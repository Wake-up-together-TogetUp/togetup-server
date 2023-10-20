package com.wakeUpTogetUp.togetUp.api.users;

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
    private final AvatarRepository avatarRepository;
    private final UserAvatarRepository userAvatarRepository;

    // 유저 아바타 목록 가져오기
    public List<UserAvatarData> findUserAvatarList(int userId) {
        List<Avatar> avatarList = avatarRepository.findAll();
        List<UserAvatar> userAvatarList = userAvatarRepository.findAllByUser_Id(userId);

        return EntityDtoMapper.INSTANCE.toUserAvatarDataList(avatarList, userAvatarList);
    }

    // 유저 아바타 해금
    @Transactional
    public void unlockAvatar(User user, int avatarId) {
        Avatar avatar = avatarRepository.findById(avatarId)
                .orElseThrow(() -> new BaseException(Status.AVATAR_NOT_FOUND));

        UserAvatar userAvatar = UserAvatar.builder()
                .user(user)
                .avatar(avatar)
                .build();
        userAvatarRepository.save(userAvatar);
    }

    // 유저 아바타 변경
    @Transactional
    public void changeUserAvatar(User user, int avatarId) {
        List<UserAvatar> userAvatarList =
                userAvatarRepository.findAllByUser_IdAndAvatar_Id(user.getId(), avatarId);

        // 보유중인 아바타인지 검사
        userAvatarList.stream().filter(i -> i.getAvatar().getId() == avatarId).findAny()
                .orElseThrow(() -> new BaseException(Status.USER_AVATAR_LOCKED));

        for(UserAvatar userAvatar : userAvatarList) {
            // 해당 아바타이면 활성화, 나머지 비활성화
            userAvatar.setIsActive(userAvatar.getAvatar().getId() == avatarId);

            userAvatarRepository.save(userAvatar);
        }
    }
}
