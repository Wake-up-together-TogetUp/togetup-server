package com.wakeUpTogetUp.togetUp.api.avatar.application;

import com.wakeUpTogetUp.togetUp.api.avatar.domain.Avatar;
import com.wakeUpTogetUp.togetUp.api.avatar.domain.UserAvatar;
import com.wakeUpTogetUp.togetUp.api.avatar.dto.response.UserAvatarResponse;
import com.wakeUpTogetUp.togetUp.api.avatar.repository.AvatarRepository;
import com.wakeUpTogetUp.togetUp.api.avatar.repository.UserAvatarRepository;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.utils.mapper.EntityDtoMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAvatarQueryService {

    private final AvatarRepository avatarRepository;
    private final UserAvatarRepository userAvatarRepository;

    public List<UserAvatarResponse> findUserAvatarList(int userId) {
        List<Avatar> avatarList = avatarRepository.findAll();
        List<UserAvatar> userAvatarList = userAvatarRepository.findAllByUser_Id(userId);

        return EntityDtoMapper.INSTANCE.toUserAvatarDataList(avatarList, userAvatarList);
    }

    public int getUserAvatarId(int userId) {
        return userAvatarRepository.findByUser_IdAndIsActiveIsTrue(userId)
                .orElseThrow(() -> new BaseException(Status.FIND_USER_AVATAR_FAIL))
                .getAvatar()
                .getId();
    }
}
