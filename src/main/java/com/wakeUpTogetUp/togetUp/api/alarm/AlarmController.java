package com.wakeUpTogetUp.togetUp.api.alarm;

import com.wakeUpTogetUp.togetUp.api.alarm.dto.request.DeleteAlarmReq;
import com.wakeUpTogetUp.togetUp.api.alarm.dto.request.PatchAlarmReq;
import com.wakeUpTogetUp.togetUp.api.alarm.dto.response.GetAlarmRes;
import com.wakeUpTogetUp.togetUp.api.alarm.dto.request.PostAlarmReq;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.utils.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "알람")
@RestController
@RequestMapping("/app/alarm")
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
    public BaseResponse<GetAlarmRes> GetAlarm(@PathVariable Integer alarmId){
        GetAlarmRes getAlarmRes = alarmProvider.getAlarm(alarmId);

        return new BaseResponse<>(Status.SUCCESS, getAlarmRes);
    }

    /**
     * 사용자 알람 불러오기
     * @param userId
     * @return
     */
    @GetMapping("")
    public BaseResponse<List<GetAlarmRes>> GetAlarmsByUserId(@RequestParam Integer userId){
        List<GetAlarmRes> alarmsResList = alarmProvider.getAlarmsByUserId(userId);

        return new BaseResponse<>(Status.SUCCESS, alarmsResList);
    }

    /**
     * 알람 생성
     * @param postAlarmReq
     * @return
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<GetAlarmRes> createAlarm(
            @RequestBody @Valid PostAlarmReq postAlarmReq
    ){
        Integer userId = postAlarmReq.getUserId();

        if(jwtService.validateByUserId(userId))
            return new BaseResponse(Status.SUCCESS_CREATED, alarmService.createAlarm(userId, postAlarmReq));
        else
            throw new BaseException(Status.JWT_MISMATCH);
    }

    /**
     * 알람 수정
     * @param alarmId
     * @param patchAlarmReq
     * @return
     */
    @PatchMapping("/{alarmId}")
    public BaseResponse<GetAlarmRes> updateAlarm(
            @PathVariable Integer alarmId,
            @RequestBody @Valid PatchAlarmReq patchAlarmReq
    ) {
        Integer userId = patchAlarmReq.getUserId();

        if(jwtService.validateByUserId(userId))
            return new BaseResponse<>(Status.SUCCESS, alarmService.updateAlarm(userId, alarmId, patchAlarmReq));
        else
            throw new BaseException(Status.JWT_MISMATCH);
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

            return new BaseResponse<>(Status.SUCCESS);
        } else
            throw new BaseException(Status.JWT_MISMATCH);
    }

    /**
     * test
     * @return
     */
    @GetMapping("/test")
    public String test(){
        return "success";
    }
}
