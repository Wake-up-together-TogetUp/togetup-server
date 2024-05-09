package com.wakeUpTogetUp.togetUp.api.alarm.dto.request;

import java.util.List;
import lombok.Getter;

@Getter
public class AlarmsDeactivateReq {

    private List<Integer> alarmIds;
}
