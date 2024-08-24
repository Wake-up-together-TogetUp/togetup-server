package com.wakeUpTogetUp.togetUp.api.avatar.application;

import com.wakeUpTogetUp.togetUp.api.avatar.application.strategy.MakeSpeechStrategy;
import com.wakeUpTogetUp.togetUp.api.avatar.domain.Avatar;
import com.wakeUpTogetUp.togetUp.api.avatar.domain.AvatarSpeech;
import com.wakeUpTogetUp.togetUp.api.avatar.domain.AvatarSpeechCondition;
import com.wakeUpTogetUp.togetUp.api.avatar.domain.AvatarTheme;
import com.wakeUpTogetUp.togetUp.api.avatar.dto.response.AvatarSpeechResponse;
import com.wakeUpTogetUp.togetUp.api.avatar.repository.AvatarRepository;
import com.wakeUpTogetUp.togetUp.api.avatar.repository.AvatarSpeechRepository;
import com.wakeUpTogetUp.togetUp.api.avatar.util.KoreanLunarCalendar;
import com.wakeUpTogetUp.togetUp.api.avatar.util.LunarDate;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.utils.mapper.EntityDtoMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvatarSpeechProvider {

    private final AvatarValidationService avatarValidationService;
    private final UserAvatarValidationService userAvatarValidationService;

    private final AvatarRepository avatarRepository;
    private final AvatarSpeechRepository avatarSpeechRepository;

    private final MakeSpeechStrategyFactory makeSpeechStrategyFactory;

    private final Random random = new Random();

    public AvatarSpeechResponse getUserAvatarSpeech(int userId, int avatarId) {
        userAvatarValidationService.validateUserAvatarActive(userId, avatarId);
        String speech = makeSpeechByCondition(selectSpeechByAvatar(avatarId));

        return EntityDtoMapper.INSTANCE.toAvatarSpeechResponse(speech);
    }

    private String makeSpeechByCondition(AvatarSpeech avatarSpeech) {
        MakeSpeechStrategy strategy = makeSpeechStrategyFactory.getStrategy(avatarSpeech.getCondition());
        return strategy.makeSpeech(avatarSpeech);
    }

    private AvatarSpeech selectSpeechByAvatar(int avatarId) {
        Avatar avatar = avatarRepository.findById(avatarId)
                .orElseThrow(() -> new BaseException(Status.AVATAR_NOT_FOUND));

        if (!AvatarTheme.isExist(avatar.getTheme())) {
            return getRandomOneByAvatar(avatarId);
        }

        switch (AvatarTheme.valueOf(avatar.getTheme())) {
            case ASTRONAUT_BEAR:
                LocalDate today = LocalDate.now();
                return getByAvatarAndLunarCondition(avatarId, today);

            default:
                return getRandomOneByAvatar(avatarId);
        }
    }

    private AvatarSpeech getByAvatarAndLunarCondition(int avatarId, LocalDate today) {
        Map<Integer, AvatarSpeechCondition> lunarConditions = Map.of(
                1, AvatarSpeechCondition.NEW_MOON,
                15, AvatarSpeechCondition.HALF_MOON,
                30, AvatarSpeechCondition.FULL_MOON
        );

        LunarDate lunarDate = KoreanLunarCalendar.convert(today);
        int lunarDay = lunarDate.getDay();
        AvatarSpeechCondition condition = lunarConditions.get(lunarDay);

        return condition != null ?
                getByAvatarAndCondition(avatarId, condition) :
                getRandomOneByAvatar(avatarId);
    }

    private AvatarSpeech getByAvatarAndCondition(int avatarId, AvatarSpeechCondition condition) {
        return avatarSpeechRepository
                .findByAvatarIdAndCondition(avatarId, condition)
                .orElseThrow(() -> new BaseException(Status.GET_AVATAR_SPEECH_BY_CONDITION_FAIL));
    }

    private AvatarSpeech getRandomOneByAvatar(int avatarId) {
        List<AvatarSpeech> avatarSpeeches = getAllByAvatar(avatarId);
        int randomIndex = random.nextInt(avatarSpeeches.size());

        return avatarSpeeches.get(randomIndex);
    }

    private List<AvatarSpeech> getAllByAvatar(int avatarId) {
        avatarValidationService.validateAvatarExist(avatarId);
        return avatarSpeechRepository.findAllByAvatar_Id(avatarId);
    }
}
