package com.wakeUpTogetUp.togetUp.api.mission.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomDetectedTag extends CustomAnalysisEntity {

    private final String name;

    @Builder
    public CustomDetectedTag(String name, double confidence) {
        super(confidence, null);
        this.name = name;
    }

    @Override
    protected boolean isMatchEntity(Object targetPattern) {
        Pattern pattern = (Pattern) targetPattern;
        Matcher matcher = pattern.matcher(name.toLowerCase());

        return matcher.find();
    }
}
