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

    private Coord coord;

    private int w;

    private int h;

    public int getX() {
        return coord.getX();
    }

    public int getY() {
        return coord.getY();
    }
}
