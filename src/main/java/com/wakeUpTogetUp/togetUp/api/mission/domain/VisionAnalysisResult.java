package com.wakeUpTogetUp.togetUp.api.mission.domain;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class VisionAnalysisResult {

	protected final String targetName;

	public abstract boolean isFail();

	public abstract List<CustomAnalysisEntity> getMatches(int size);

	public abstract String toString();
}
