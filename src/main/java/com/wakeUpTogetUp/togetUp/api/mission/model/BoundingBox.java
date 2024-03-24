package com.wakeUpTogetUp.togetUp.api.mission.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoundingBox {
    private int x;

    private int y;

    private int w;

    private int h;
}
