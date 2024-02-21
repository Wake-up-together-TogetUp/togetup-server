package com.wakeUpTogetUp.togetUp.api.avatar;

import com.wakeUpTogetUp.togetUp.api.avatar.model.Avatar;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Integer> {
    Optional<Avatar> findAvatarByUnlockLevel(int level);
}
