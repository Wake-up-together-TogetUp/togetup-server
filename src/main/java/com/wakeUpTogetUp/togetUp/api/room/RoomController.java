package com.wakeUpTogetUp.togetUp.api.room;

import com.wakeUpTogetUp.togetUp.api.auth.AuthUser;
import com.wakeUpTogetUp.togetUp.api.mission.MissionLogRepository;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionLog;
import com.wakeUpTogetUp.togetUp.api.room.dto.request.RoomReq;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomRes;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.common.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        return new BaseResponse(Status.SUCCESS);

    }


//    @ResponseBody
//    @GetMapping()
//    public BaseResponse<List<RoomRes>> get(@AuthUser Integer userId){
//       //List<RoomRes> roomList = roomService.getRoomList(userId);
//      // return new BaseResponse<>(Status.SUCCESS,roomList);
//    }

    @GetMapping

    public BaseResponse<List<RoomRes>> test(@AuthUser Integer userId ) {

       // List<Integer> roomIds= new ArrayList<>();
       // roomIds.add(6);
        //roomIds.add(7);
        //System.out.println("룸아이디" + roomIds.get(0).toString());
       // List<MissionLog> missionLogs =missionLogRepository.findMostRecentMissionLogsByRoomIds(roomIds);
        //List<MissionLog> missionLogs =missionLogRepository.findMostRecentMissionLogsByRoomIds(roomIds);

      //  System.out.println(missionLogs.toString() +" 여기");

        return new BaseResponse(Status.SUCCESS,roomService.getRoomList2(userId));

    }



}
