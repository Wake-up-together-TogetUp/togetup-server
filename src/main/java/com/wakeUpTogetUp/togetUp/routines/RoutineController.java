package com.wakeUpTogetUp.togetUp.routines;

import com.wakeUpTogetUp.togetUp.common.BaseResponse;
import com.wakeUpTogetUp.togetUp.common.BaseResponseStatus;
import com.wakeUpTogetUp.togetUp.routines.model.GetRoutineRes;
import com.wakeUpTogetUp.togetUp.routines.model.PostRoutineReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/users")
@RequiredArgsConstructor
public class RoutineController {
    private final RoutineService routineService;
    private final RoutineProvider routineProvider;

    // 루틴 생성
    @PostMapping("{userId}/routines")
    public BaseResponse createRoutine(
            @PathVariable("userId") int userId,
            @RequestBody PostRoutineReq postRoutineReq
    ) {
        int createdRoutineId = routineService.createRoutine(userId, postRoutineReq);

        return new BaseResponse(BaseResponseStatus.SUCCESS, createdRoutineId);
    }

    // 루틴 1개 가져오기
    @GetMapping("{userId}/routines/{routineId}")
    public BaseResponse<GetRoutineRes> getRoutine(
            @PathVariable("userId") Integer userId,
            @PathVariable("routineId") Integer routineId
    ) {
        GetRoutineRes getRoutineRes = routineProvider.getRoutine(routineId);

        return new BaseResponse<>(BaseResponseStatus.SUCCESS, getRoutineRes);
    }

//    알람 1개 불러오기
//    알람 수정
//    알람 삭제
}
