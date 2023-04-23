package com.wakeUpTogetUp.togetUp.users;

import com.wakeUpTogetUp.togetUp.alarms.model.Alarm;
import com.wakeUpTogetUp.togetUp.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDao {
    private final EntityManager em;

    public Optional<UserEntity> findById(int id) {
        UserEntity user = em.find(UserEntity.class, id);
        return Optional.ofNullable(user);
    }
}
