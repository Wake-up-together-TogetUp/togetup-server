package com.wakeUpTogetUp.togetUp.api.users;

import com.wakeUpTogetUp.togetUp.api.users.model.UserAvatarPurchaseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAvatarPurchaseLogRepository extends JpaRepository<UserAvatarPurchaseLog, Integer> {

}
