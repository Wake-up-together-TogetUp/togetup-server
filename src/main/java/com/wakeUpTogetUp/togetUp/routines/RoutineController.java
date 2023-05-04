package com.wakeUpTogetUp.togetUp.routines;

import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.common.exception.ResponseStatus;
import com.wakeUpTogetUp.togetUp.routines.dto.response.RoutineRes;
import com.wakeUpTogetUp.togetUp.routines.dto.request.RoutineReq;
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

    // 루틴 1개 가져오기
    @GetMapping("{userId}/routines/{routineId}")
    public BaseResponse<RoutineRes> getRoutine(
            @PathVariable("userId") Integer userId,
            @PathVariable("routineId") Integer routineId
    ) {
        RoutineRes routineRes = routineProvider.getRoutine(routineId);

        return new BaseResponse<>(ResponseStatus.SUCCESS, routineRes);
    }

    // 루틴 목록 가져오기
    @GetMapping("{userId}/routines")
    public BaseResponse<List<RoutineRes>> getRoutinesByUserId(@PathVariable Integer userId) {
        List<RoutineRes> routineResList = routineProvider.getRoutinesByUserId(userId);

        return new BaseResponse<>(ResponseStatus.SUCCESS, routineResList);
    }

    // 루틴 생성
    @PostMapping("{userId}/routines")
    public BaseResponse createRoutine(
            @PathVariable("userId") Integer userId,
            @RequestBody @Valid RoutineReq routineReq
    ) {
        Integer createdRoutineId = routineService.createRoutine(userId, routineReq);

        return new BaseResponse(ResponseStatus.SUCCESS, createdRoutineId);
    }
    
    // 루틴 수정


    // 루틴 삭제
    
    
    //test
    @GetMapping("/test")
    public String test(){
        return "success";
    }
}
