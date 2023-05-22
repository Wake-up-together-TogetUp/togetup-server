package com.wakeUpTogetUp.togetUp.routines;

import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.routines.dto.request.DeleteRoutineReq;
import com.wakeUpTogetUp.togetUp.routines.dto.request.PatchRoutineReq;
import com.wakeUpTogetUp.togetUp.routines.dto.response.RoutineRes;
import com.wakeUpTogetUp.togetUp.routines.dto.request.PostRoutineReq;
import com.wakeUpTogetUp.togetUp.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
     * 루틴 목록 가져오기
     * @param userId
     * @return
     */
    @GetMapping("")
    public BaseResponse<List<RoutineRes>> getRoutinesByUserId(@RequestParam("userId") Integer userId) {
        return new BaseResponse<>(Status.SUCCESS, routineProvider.getRoutinesByUserId(userId));
    }

    /**
     * 루틴 생성
     * @param postRoutineReq
     * @return
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse createRoutine(
            @RequestBody @Valid PostRoutineReq postRoutineReq
    ) {
        Integer userId = postRoutineReq.getUserId();

        if(jwtService.validateByUserId(userId)) {
            return new BaseResponse(Status.SUCCESS, routineService.createRoutine(userId, postRoutineReq));
        } else
            throw new BaseException(Status.JWT_MISMATCH);
    }

    /**
     * 루틴 수정
     * @param routineId
     * @param patchRoutineReq
     * @return
     */
    @PatchMapping("/{routineId}")
    public BaseResponse<RoutineRes> updateRoutine(
            @PathVariable Integer routineId,
            @RequestBody @Valid PatchRoutineReq patchRoutineReq
    ) {
        Integer userId = patchRoutineReq.getUserId();

        if(jwtService.validateByUserId(userId)) {
            return new BaseResponse<>(Status.SUCCESS, routineService.updateRoutine(routineId, patchRoutineReq));
        } else
            throw new BaseException(Status.JWT_MISMATCH);
    }


    /**
     * 루틴 삭제
     * @param deleteRoutineReq
     * @param routineId
     * @return
     */
    @DeleteMapping("/{routineId}")
    public BaseResponse<Integer> deleteAlarm(
            @RequestBody @Valid DeleteRoutineReq deleteRoutineReq,
            @PathVariable Integer routineId
    ) {
        Integer userId = deleteRoutineReq.getUserId();

        if(jwtService.validateByUserId(userId)) {
            routineService.deleteRoutine(routineId);

            return new BaseResponse<>(Status.SUCCESS);
        } else
            throw new BaseException(Status.JWT_MISMATCH);
    }
}
