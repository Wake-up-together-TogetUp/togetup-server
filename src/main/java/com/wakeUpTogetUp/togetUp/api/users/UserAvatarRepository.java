package com.wakeUpTogetUp.togetUp.api.users;

import com.wakeUpTogetUp.togetUp.api.users.model.UserAvatar;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAvatarRepository extends JpaRepository<UserAvatar, Integer> {


    Optional<UserAvatar> findByUser_IdAndAvatar_Id(int userId, int avatarId);

    Optional<UserAvatar> findByUser_IdAndIsActiveIsTrue(int userId);

    List<UserAvatar> findAllByUser_Id(Integer userId);

}
