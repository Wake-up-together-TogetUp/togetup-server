package com.wakeUpTogetUp.togetUp.alarms;

import com.wakeUpTogetUp.togetUp.alarms.dto.request.PatchAlarmReq;
import com.wakeUpTogetUp.togetUp.alarms.dto.response.AlarmRes;
import com.wakeUpTogetUp.togetUp.alarms.dto.response.AlarmsRes;
import com.wakeUpTogetUp.togetUp.alarms.dto.request.PostAlarmReq;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.common.ResponseStatus;
import com.wakeUpTogetUp.togetUp.common.exception.BaseException;
import com.wakeUpTogetUp.togetUp.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/app/users")
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService alarmService;
    private final AlarmProvider alarmProvider;
    private final JwtService jwtService;

    /**
     * 알람 1개 불러오기
     * @param userId
     * @param alarmId
     * @return
     */
    @GetMapping("{userId}/alarms/{alarmId}")
    public BaseResponse<AlarmRes> GetAlarm(@PathVariable Integer userId, @PathVariable Integer alarmId){
        AlarmRes alarmRes = alarmProvider.getAlarm(alarmId, userId);

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
     * @param postAlarmReq
     * @return
     */
    @PostMapping("/{userId}/alarms")
    public BaseResponse createAlarm(
            @PathVariable("userId") Integer userId,
            @RequestBody @Valid PostAlarmReq postAlarmReq
    ){
        //TODO : jwt 정보와 일치하는지 확인하기
        if(jwtService.validate(userId)) {
            Integer createdAlarmId = alarmService.createAlarm(userId, postAlarmReq);

            return new BaseResponse(ResponseStatus.SUCCESS, createdAlarmId);
        }
        else {
            throw new BaseException(ResponseStatus.JWT_MISMATCH);
        }
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
            @RequestBody @Valid PatchAlarmReq patchAlarmReq
    ) {
        // TODO : JWT
        if(jwtService.validate(userId)) {
            AlarmRes patchAlarmRes = alarmService.updateAlarm(userId, alarmId, patchAlarmReq);

            return new BaseResponse<>(ResponseStatus.SUCCESS, patchAlarmRes);
        } else {
            throw new BaseException(ResponseStatus.JWT_MISMATCH);
        }
    }

    /**
     * 알람 삭제
     * @param userId
     * @param alarmId
     * @return
     */
    @DeleteMapping("{userId}/alarms/{alarmId}")
    public BaseResponse<Integer> deleteAlarm(
            @PathVariable @Valid Integer userId,
            @PathVariable @Valid Integer alarmId
    ) {
        // TODO : JWT
        if(jwtService.validate(userId)) {
            alarmService.deleteAlarm(alarmId);

            return new BaseResponse<>(ResponseStatus.SUCCESS);
        } else {
            throw new BaseException(ResponseStatus.JWT_MISMATCH);
        }
    }
}
