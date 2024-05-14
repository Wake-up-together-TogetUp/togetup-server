package com.wakeUpTogetUp.togetUp.api.users;

import com.wakeUpTogetUp.togetUp.api.room.dto.response.UserProfileData;
import com.wakeUpTogetUp.togetUp.api.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<User> findById(Integer integer);

    Optional<User> findBySocialId(String userId);

    List<User> findAllByAgreePushIsTrue();

    @Query("SELECT new com.wakeUpTogetUp.togetUp.api.room.dto.response.UserProfileData(" +
            "u.id, u.name, a.theme, u.level) " +
            "FROM User u " +
            "JOIN u.roomUsers ru " +
            "JOIN u.userAvatar ua " +
            "JOIN ua.avatar a " +
            "WHERE ru.room.id = :roomId " +
            "AND ua.isActive = true " +
            "ORDER BY " +
            "CASE WHEN u.id = :userId THEN 0 ELSE 1 END, " +
            "u.name")
    List<UserProfileData> findUserProfileDataByRoomIdOrderedByUserIdAndName(@Param("userId") Integer userId, @Param("roomId") Integer roomId);

}
