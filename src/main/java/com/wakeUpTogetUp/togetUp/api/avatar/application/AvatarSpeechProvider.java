package com.wakeUpTogetUp.togetUp.api.avatar.application;

import com.wakeUpTogetUp.togetUp.api.avatar.dto.response.AvatarSpeechResponse;
import com.wakeUpTogetUp.togetUp.api.avatar.model.AvatarSpeech;
import com.wakeUpTogetUp.togetUp.api.avatar.repository.AvatarSpeechRepository;
import com.wakeUpTogetUp.togetUp.utils.mapper.EntityDtoMapper;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvatarSpeechProvider {

    private final AvatarSpeechRepository avatarSpeechRepository;
    private final AvatarValidationService avatarValidationService;
    private final UserAvatarValidationService userAvatarValidationService;
    private final Random rand = new Random();

    public AvatarSpeechResponse getUserAvatarSpeech(int userId, int avatarId) {
        userAvatarValidationService.validateUserAvatarActive(userId, avatarId);
        AvatarSpeech avatarSpeech = getAvatarRandomSpeech(avatarId);

        return EntityDtoMapper.INSTANCE.toAvatarSpeechResponse(avatarSpeech);
    }
    private AvatarSpeech getAvatarRandomSpeech(int avatarId) {
        List<AvatarSpeech> avatarSpeeches = getSpeechesOfAvatar(avatarId);
        int randomIndex = rand.nextInt(avatarSpeeches.size());

        return avatarSpeeches.get(randomIndex);
    }

    private List<AvatarSpeech> getSpeechesOfAvatar(int avatarId) {
        avatarValidationService.validateAvatarExist(avatarId);
        return avatarSpeechRepository.findAllByAvatar_Id(avatarId);
    }
}
