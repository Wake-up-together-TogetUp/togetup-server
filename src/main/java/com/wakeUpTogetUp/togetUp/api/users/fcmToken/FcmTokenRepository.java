package com.wakeUpTogetUp.togetUp.api.users.fcmToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FcmTokenRepository extends JpaRepository<FcmToken, Integer> {
    List<FcmToken> findAllByUser_IdIn(List<Integer> userIds);
    void deleteAllByValueIn(List<String> values);
}
