package com.wakeUpTogetUp.togetUp.api.dev;

import com.wakeUpTogetUp.togetUp.api.dev.model.AppVersionHistory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppVersionHistoryRepository extends JpaRepository<AppVersionHistory, String> {

    Optional<AppVersionHistory> findTopByOrderByCreatedAtDesc();
}
