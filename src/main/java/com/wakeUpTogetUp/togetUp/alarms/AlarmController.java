package com.wakeUpTogetUp.togetUp.alarms;

import com.wakeUpTogetUp.togetUp.alarms.dto.response.AlarmRes;
import com.wakeUpTogetUp.togetUp.alarms.dto.response.AlarmsRes;
import com.wakeUpTogetUp.togetUp.alarms.dto.request.AlarmReq;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.common.exception.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/app/users")
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService alarmService;
    private final AlarmProvider alarmProvider;

    /**
     * 알람 1개 불러오기
     * @param userId
     * @param alarmId
     * @return
     */
    @GetMapping("{userId}/alarms/{alarmId}")
    public BaseResponse<AlarmRes> GetAlarm(@PathVariable Integer userId, @PathVariable Integer alarmId){
        AlarmRes alarmRes = alarmProvider.getAlarm(alarmId);

        return new BaseResponse<>(ResponseStatus.SUCCESS, alarmRes);
    }

    /**
     * 사용자 알람, 루틴 목록 불러오기
     * @param userId
     * @return
     */
    @GetMapping("{userId}/alarms")
    public BaseResponse<List<AlarmsRes>> GetAlarmsByUserId(@PathVariable Integer userId){
        List<AlarmsRes> alarmsResList = alarmProvider.getAlarmsByUserId(userId);

        return new BaseResponse<>(ResponseStatus.SUCCESS, alarmsResList);
    }

    /**
     * 알람 생성(알람, 알람-루틴 매핑)
     * @param userId
     * @param alarmReq
     * @return
     */
    @PostMapping("/{userId}/alarms")
    public BaseResponse createAlarm(
            @PathVariable("userId") Integer userId,
            @RequestBody @Valid AlarmReq alarmReq
    ){
        //TODO : jwt 정보와 일치하는지 확인하기
        Integer createdAlarmId = alarmService.createAlarm(userId, alarmReq);

        return new BaseResponse(ResponseStatus.SUCCESS, createdAlarmId);
    }

    /**
     * 알람 수정
     * @param userId
     * @param alarmId
     * @param patchAlarmReq
     * @return
     */
    @PatchMapping("{userId}/alarms/{alarmId}")
    public BaseResponse<AlarmRes> updateAlarm(
            @PathVariable Integer userId,
            @PathVariable Integer alarmId,
            @RequestBody @Valid AlarmReq patchAlarmReq
    ) {
        // TODO : JWT

        AlarmRes patchAlarmRes = alarmService.updateAlarm(userId, alarmId, patchAlarmReq);

        return new BaseResponse<>(ResponseStatus.SUCCESS, patchAlarmRes);
    }

//    알람 삭제
}
