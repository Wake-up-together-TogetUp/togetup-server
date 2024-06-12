package com.wakeUpTogetUp.togetUp.api.avatar.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
public class LunarDate {
    private int year;
    private int month;
    private int day;
    private int leap;
}
