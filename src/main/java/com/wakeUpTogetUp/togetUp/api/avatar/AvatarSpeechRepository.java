package com.wakeUpTogetUp.togetUp.api.avatar;

import com.wakeUpTogetUp.togetUp.api.avatar.model.AvatarSpeech;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarSpeechRepository extends JpaRepository<AvatarSpeech, Integer> {

    List<AvatarSpeech> findAllByAvatar_Id(int avatarId);
}