package com.wakeUpTogetUp.togetUp.api.room;

import com.wakeUpTogetUp.togetUp.api.auth.AuthUser;
import com.wakeUpTogetUp.togetUp.api.mission.MissionLogRepository;
import com.wakeUpTogetUp.togetUp.api.room.dto.request.RoomUserLogReq;
import com.wakeUpTogetUp.togetUp.api.room.dto.request.RoomReq;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomUserLogRes;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomRes;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.common.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "그룹 (room)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/room")
public class RoomController {

    private final RoomService roomService;
    private final MissionLogRepository missionLogRepository;

    @Operation(description = "그룹과 그룹 알람 생성")
    @ResponseBody
    @PostMapping() //
    public BaseResponse<Status> create(@AuthUser Integer userId,  @RequestBody RoomReq roomReq ) {

        roomService.createRoom(userId,roomReq);
        return new BaseResponse(Status.SUCCESS_CREATED);

    }

    @Operation(description = "그룹 리스트 불러오기")
    @GetMapping()
    public BaseResponse<List<RoomRes>> getList(@AuthUser Integer userId ) {


        return new BaseResponse(Status.SUCCESS,roomService.getRoomList(userId));

    }

    @Operation(description = "그룹의 멤버의 미션기록 보기")
    @GetMapping("/user/mission-log")
    public BaseResponse<RoomUserLogRes> getRoomDetailByDate(@AuthUser Integer userId, @RequestBody RoomUserLogReq roomLogReq) {


        return new BaseResponse(Status.SUCCESS,roomService.getRoomUserLogList(userId,roomLogReq));

    }



}
