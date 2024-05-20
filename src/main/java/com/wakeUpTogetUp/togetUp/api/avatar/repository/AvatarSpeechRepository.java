package com.wakeUpTogetUp.togetUp.api.avatar.repository;

import com.wakeUpTogetUp.togetUp.api.avatar.domain.AvatarSpeech;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarSpeechRepository extends JpaRepository<AvatarSpeech, Integer> {

    List<AvatarSpeech> findAllByAvatar_Id(int avatarId);
}