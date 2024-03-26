package com.wakeUpTogetUp.togetUp.api.mission.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.MODULE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoundingBox {
    private int x;

    private int y;

    private int w;

    private int h;
}
