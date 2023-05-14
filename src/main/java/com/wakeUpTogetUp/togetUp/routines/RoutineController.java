package com.wakeUpTogetUp.togetUp.routines;

import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.common.ResponseStatus;
import com.wakeUpTogetUp.togetUp.common.exception.BaseException;
import com.wakeUpTogetUp.togetUp.routines.dto.response.RoutineRes;
import com.wakeUpTogetUp.togetUp.routines.dto.request.RoutineReq;
import com.wakeUpTogetUp.togetUp.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/app/users")
@RequiredArgsConstructor
public class RoutineController {
    private final RoutineService routineService;
    private final RoutineProvider routineProvider;
    private final JwtService jwtService;

    /**
     * 루틴 1개 가져오기
     * @param userId
     * @param routineId
     * @return
     */
    @GetMapping("{userId}/routines/{routineId}")
    public BaseResponse<RoutineRes> getRoutine(
            @PathVariable("userId") Integer userId,
            @PathVariable("routineId") Integer routineId
    ) {
        RoutineRes routineRes = routineProvider.getRoutine(routineId);

        return new BaseResponse<>(ResponseStatus.SUCCESS, routineRes);
    }

    /**
     * 루틴 목록 가져오기
     * @param userId
     * @return
     */
    @GetMapping("{userId}/routines")
    public BaseResponse<List<RoutineRes>> getRoutinesByUserId(@PathVariable Integer userId) {
        List<RoutineRes> routineResList = routineProvider.getRoutinesByUserId(userId);

        return new BaseResponse<>(ResponseStatus.SUCCESS, routineResList);
    }

    /**
     * 루틴 생성
     * @param userId
     * @param routineReq
     * @return
     */
    @PostMapping("{userId}/routines")
    public BaseResponse createRoutine(
            @PathVariable("userId") Integer userId,
            @RequestBody @Valid RoutineReq routineReq
    ) {
        if(jwtService.validate(userId)) {
            Integer createdRoutineId = routineService.createRoutine(userId, routineReq);

            return new BaseResponse(ResponseStatus.SUCCESS, createdRoutineId);
        } else
            throw new BaseException(ResponseStatus.JWT_MISMATCH);
    }
    
    // 루틴 수정
    @PatchMapping("{userId}/routines/{routineId}")
    public BaseResponse<RoutineRes> updateRoutine(
            @PathVariable Integer userId,
            @PathVariable Integer routineId,
            @RequestBody @Valid RoutineReq patchRoutineReq
    ) {
        if(jwtService.validate(userId)) {
            RoutineRes patchRoutineRes = routineService.updateRoutine(routineId, patchRoutineReq);

            return new BaseResponse<>(ResponseStatus.SUCCESS, patchRoutineRes);
        } else
            throw new BaseException(ResponseStatus.JWT_MISMATCH);
    }


    /**
     * 루틴 삭제
     * @param userId
     * @param routineId
     * @return
     */
    @DeleteMapping("{userId}/routines/{routineId}")
    public BaseResponse<Integer> deleteAlarm(
            @PathVariable @Valid Integer userId,
            @PathVariable @Valid Integer routineId
    ) {
        if(jwtService.validate(userId)) {
            routineService.deleteRoutine(routineId);

            return new BaseResponse<>(ResponseStatus.SUCCESS);
        } else
            throw new BaseException(ResponseStatus.JWT_MISMATCH);
    }
    
    //test
    @GetMapping("/test")
    public String test(){
        return "success";
    }
}
