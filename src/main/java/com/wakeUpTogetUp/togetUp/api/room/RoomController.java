package com.wakeUpTogetUp.togetUp.api.room;

import com.wakeUpTogetUp.togetUp.api.auth.AuthUser;
import com.wakeUpTogetUp.togetUp.api.room.dto.request.RoomReq;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.common.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@Tag(name = "그룹 (room)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/room")
public class RoomController {

    private final RoomService roomService;


    @Operation(description = "그룹과 그룹 알람 생성")
    @ResponseBody
    @PostMapping() //
    public BaseResponse<Status> create(@AuthUser Integer userId,  @RequestBody RoomReq roomReq ) {

        roomService.createRoom(userId,roomReq);
        return new BaseResponse(Status.SUCCESS);

    }



}
