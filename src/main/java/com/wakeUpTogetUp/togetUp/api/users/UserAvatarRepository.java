package com.wakeUpTogetUp.togetUp.api.users;

import com.wakeUpTogetUp.togetUp.api.avatar.model.Avatar;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import com.wakeUpTogetUp.togetUp.api.users.model.UserAvatar;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAvatarRepository extends JpaRepository<UserAvatar, Integer> {


    Optional<UserAvatar> findByUserAndAvatar(User user, Avatar avatar);

    Optional<UserAvatar> findByUser_IdAndIsActiveIsTrue(int userId);

    List<UserAvatar> findAllByUser_Id(Integer userId);

}
