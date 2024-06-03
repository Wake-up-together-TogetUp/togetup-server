package com.wakeUpTogetUp.togetUp.api.dev.util;

import java.util.Comparator;
import org.springframework.stereotype.Component;

@Component
public class VersionComparator implements Comparator<String> {

    @Override
    public int compare(String v1, String v2) {
        String[] v1Parts = v1.split("\\.");
        String[] v2Parts = v2.split("\\.");

        int length = Math.max(v1Parts.length, v2Parts.length);
        for (int i = 0; i < length; i++) {
            int v1Num = i < v1Parts.length ? Integer.parseInt(v1Parts[i]) : 0;
            int v2Num = i < v2Parts.length ? Integer.parseInt(v2Parts[i]) : 0;

            if (v1Num != v2Num) {
                return v1Num - v2Num;
            }
        }

        return 0;
    }
}
