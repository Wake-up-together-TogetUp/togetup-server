package com.wakeUpTogetUp.togetUp.api.dev;

import com.wakeUpTogetUp.togetUp.api.dev.dto.response.AppStoreUrlRes;
import com.wakeUpTogetUp.togetUp.api.dev.model.AppVersionHistory;
import com.wakeUpTogetUp.togetUp.api.dev.util.VersionComparator;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppVersionProvider {

    private final AppVersionHistoryRepository appVersionHistoryRepository;
    private final VersionComparator versionComparator;

    public AppStoreUrlRes getAppVersionCheckResult(String currentAppVersion) {
        if (checkAppVersionInvalid(currentAppVersion)) {
            throw new BaseException(Status.APP_VERSION_HIGHER_THAN_LATEST_EXCEPTION);
        }

        if (checkAppVersionLatest(currentAppVersion)) {
            return AppStoreUrlRes.builder()
                    .isLatest(true)
                    .build();
        } else {
            AppVersionHistory appVersionHistory = getLatestAppVersion();

            return AppStoreUrlRes.builder()
                    .isLatest(false)
                    .url(appVersionHistory.getAppStoreUrl())
                    .build();
        }
    }

    private AppVersionHistory getLatestAppVersion() {
        return appVersionHistoryRepository.findTopByOrderByCreatedAtDesc()
                .orElseThrow(() -> new BaseException(Status.GET_LATEST_APP_VERSION_FAIL));
    }

    private boolean checkAppVersionInvalid(String currentAppVersion) {
        String latestVersion = getLatestAppVersion().getVersion();

        return versionComparator.compare(currentAppVersion, latestVersion) > 0;
    }

    private boolean checkAppVersionLatest(String currentAppVersion) {
        String latestVersion = getLatestAppVersion().getVersion();

        return currentAppVersion.equals(latestVersion);
    }
}
