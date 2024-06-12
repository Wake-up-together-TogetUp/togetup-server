package com.wakeUpTogetUp.togetUp.api.avatar.repository;

import com.wakeUpTogetUp.togetUp.api.avatar.domain.AvatarSpeech;
import com.wakeUpTogetUp.togetUp.api.avatar.domain.AvatarSpeechCondition;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarSpeechRepository extends JpaRepository<AvatarSpeech, Integer> {

    List<AvatarSpeech> findAllByAvatar_Id(int avatarId);

    Optional<AvatarSpeech> findByAvatarIdAndCondition(Integer avatarId, AvatarSpeechCondition condition);
}