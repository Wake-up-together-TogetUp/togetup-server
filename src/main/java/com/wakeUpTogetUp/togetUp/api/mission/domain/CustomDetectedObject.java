package com.wakeUpTogetUp.togetUp.api.mission.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wakeUpTogetUp.togetUp.api.mission.model.BoundingBox;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomDetectedObject extends CustomAnalysisEntity {

    private final String name;
    private final String parent;

    @Builder
    private CustomDetectedObject(String name, String parent, double confidence, BoundingBox box) {
        super(confidence, box);
        this.name = name;
        this.parent = parent;
    }

    @Override
    public boolean isMatchEntity(Object targetPattern) {
        Pattern pattern = (Pattern) targetPattern;
        Matcher matcher = pattern.matcher(concatObjectAndParent().toLowerCase());
        
        return matcher.find();
    }

    public String concatObjectAndParent() {
        return isParentNull() ?  name : (name + " " + parent);
    }

    private boolean isParentNull() {
        return parent == null;
    }
}
