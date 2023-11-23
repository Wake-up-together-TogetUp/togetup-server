package com.wakeUpTogetUp.togetUp.api.users.fcmToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FcmTokenRepository extends JpaRepository<FcmToken, Integer> {

}
