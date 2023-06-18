package com.wakeUpTogetUp.togetUp.routine;

import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.routine.dto.response.RoutineRes;
import com.wakeUpTogetUp.togetUp.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/routine")
@RequiredArgsConstructor
public class RoutineController {
    private final RoutineService routineService;
    private final RoutineProvider routineProvider;
    private final JwtService jwtService;

    /**
     * 루틴 1개 가져오기
     * @param routineId
     * @return
     */
    @GetMapping("/{routineId}")
    public BaseResponse<RoutineRes> getRoutine(
            @PathVariable("routineId") Integer routineId
    ) {
        return new BaseResponse<>(Status.SUCCESS, routineProvider.getRoutine(routineId));
    }

    /**
     * alarmId로 루틴 목록 가져오기
     * @param alarmId
     * @return
     */
    @GetMapping("")
    public BaseResponse<List<RoutineRes>> getRoutinesByUserId(@RequestParam("alarmId") Integer alarmId) {
        return new BaseResponse<>(Status.SUCCESS, routineProvider.getRoutinesByAlarmId(alarmId));
    }
}
