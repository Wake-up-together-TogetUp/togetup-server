package com.wakeUpTogetUp.togetUp.alarms;

import com.wakeUpTogetUp.togetUp.alarms.dto.request.DeleteAlarmReq;
import com.wakeUpTogetUp.togetUp.alarms.dto.request.PatchAlarmReq;
import com.wakeUpTogetUp.togetUp.alarms.dto.response.AlarmRes;
import com.wakeUpTogetUp.togetUp.alarms.dto.response.AlarmsRes;
import com.wakeUpTogetUp.togetUp.alarms.dto.request.PostAlarmReq;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.common.ResponseStatus;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/app/alarms")
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService alarmService;
    private final AlarmProvider alarmProvider;
    private final JwtService jwtService;

    /**
     * 알람 1개 불러오기
     * @param alarmId
     * @return
     */
    @GetMapping("/{alarmId}")
    public BaseResponse<AlarmRes> GetAlarm(@PathVariable Integer alarmId){
        AlarmRes alarmRes = alarmProvider.getAlarm(alarmId);

        return new BaseResponse<>(ResponseStatus.SUCCESS, alarmRes);
    }

    /**
     * 사용자 알람, 루틴 목록 불러오기
     * @param userId
     * @return
     */
    @GetMapping("")
    public BaseResponse<List<AlarmsRes>> GetAlarmsByUserId(@RequestParam Integer userId){
        List<AlarmsRes> alarmsResList = alarmProvider.getAlarmsByUserId(userId);

        return new BaseResponse<>(ResponseStatus.SUCCESS, alarmsResList);
    }

    /**
     * 알람 생성(알람, 알람-루틴 매핑)
     * @param postAlarmReq
     * @return
     */
    @PostMapping("")
    public BaseResponse createAlarm(
            @RequestBody @Valid PostAlarmReq postAlarmReq
    ){
        Integer userId = postAlarmReq.getUserId();

        if(jwtService.validateByUserId(userId)) {
            Integer createdAlarmId = alarmService.createAlarm(userId, postAlarmReq);

            return new BaseResponse(ResponseStatus.SUCCESS, createdAlarmId);
        }
        else {
            throw new BaseException(ResponseStatus.JWT_MISMATCH);
        }
    }

    /**
     * 알람 수정
     * @param alarmId
     * @param patchAlarmReq
     * @return
     */
    @PatchMapping("/{alarmId}")
    public BaseResponse<AlarmRes> updateAlarm(
            @PathVariable Integer alarmId,
            @RequestBody @Valid PatchAlarmReq patchAlarmReq
    ) {
        Integer userId = patchAlarmReq.getUserId();

        if(jwtService.validateByUserId(userId)) {
            AlarmRes patchAlarmRes = alarmService.updateAlarm(userId, alarmId, patchAlarmReq);

            return new BaseResponse<>(ResponseStatus.SUCCESS, patchAlarmRes);
        } else {
            throw new BaseException(ResponseStatus.JWT_MISMATCH);
        }
    }

    /**
     * 알람 삭제
     * @param alarmId
     * @return
     */
    @DeleteMapping("/{alarmId}")
    public BaseResponse<Integer> deleteAlarm(
            @PathVariable Integer alarmId,
            @RequestBody @Valid DeleteAlarmReq deleteAlarmReq
            ) {
        Integer userId = deleteAlarmReq.getUserId();

        if(jwtService.validateByUserId(userId)) {
            alarmService.deleteAlarm(alarmId);

            return new BaseResponse<>(ResponseStatus.SUCCESS);
        } else {
            throw new BaseException(ResponseStatus.JWT_MISMATCH);
        }
    }
}
