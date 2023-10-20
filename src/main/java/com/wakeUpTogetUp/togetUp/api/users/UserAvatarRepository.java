package com.wakeUpTogetUp.togetUp.api.users;

import com.wakeUpTogetUp.togetUp.api.users.model.UserAvatar;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAvatarRepository extends JpaRepository<UserAvatar, Integer> {

    UserAvatar findByUser_Id(Integer userId);

    List<UserAvatar> findAllByUser_IdAndAvatar_Id(Integer userId, Integer avatarId);
}
