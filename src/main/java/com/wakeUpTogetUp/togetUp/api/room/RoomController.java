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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary= "그룹과 그룹 알람 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "생성 되었습니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 입니다.")})
    @ResponseBody
    @PostMapping() //
    public BaseResponse<Status> create(@Parameter(hidden = true) @AuthUser Integer userId, @RequestBody RoomReq roomReq ) {

        roomService.createRoom(userId,roomReq);
        return new BaseResponse(Status.SUCCESS_CREATED);

    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
                    content = @Content(schema = @Schema(implementation = RoomRes.class))
            )})
    @Operation(summary = "그룹 리스트 불러오기")
    @GetMapping()
    public BaseResponse<List<RoomRes>> getList(@Parameter(hidden = true) @AuthUser Integer userId ) {


        return new BaseResponse(Status.SUCCESS,roomService.getRoomList(userId));

    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
                    content = @Content(schema = @Schema(implementation = RoomUserLogRes.class))),
            @ApiResponse(responseCode = "404", description = "그룹의 멤버가 없습니다.")})
    @Operation(description = "그룹의 멤버의 미션기록 보기")
    @GetMapping("/user/mission-log")
    public BaseResponse<RoomUserLogRes> getRoomDetailByDate(@Parameter(hidden = true) @AuthUser Integer userId, @RequestBody RoomUserLogReq roomLogReq) {


        return new BaseResponse(Status.SUCCESS,roomService.getRoomUserLogList(userId,roomLogReq));

    }



}
