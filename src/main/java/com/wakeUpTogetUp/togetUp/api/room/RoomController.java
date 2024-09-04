package com.wakeUpTogetUp.togetUp.api.room;

import com.wakeUpTogetUp.togetUp.api.alarm.dto.request.AlarmCreateReq;
import com.wakeUpTogetUp.togetUp.api.auth.AuthUser;
import com.wakeUpTogetUp.togetUp.api.room.dto.request.RoomReq;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomDetailRes;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomInviteInfoRes;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomJoinRes;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomRes;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomUserMissionLogRes;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "그룹(Room)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/room")
public class RoomController {

  private final RoomService roomService;
  private final RoomQueryService roomQueryService;
  private final RoomQueryRepository roomQueryRepository;

  @Operation(summary = "그룹과 그룹 알람 생성")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "생성 되었습니다."),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 입니다."),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 방 입니다."),
      @ApiResponse(responseCode = "409", description = "이미 방에 들어간 멤버입니다.")
  })
  @ResponseBody
  @PostMapping()
  public BaseResponse<RoomJoinRes> create(@Parameter(hidden = true) @AuthUser Integer userId,
      @RequestBody RoomReq roomReq) {

    return new BaseResponse<>(Status.SUCCESS_CREATED, roomService.createRoom(userId, roomReq));

  }

  @Operation(summary = "그룹 알람 생성 및 그룹 참가")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다."),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 유저 입니다."),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 방 입니다."),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 미션 입니다."),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 미션 객체 입니다."),
      @ApiResponse(responseCode = "409", description = "이미 방에 들어간 멤버입니다.")
  })
  @ResponseBody
  @PostMapping("/join/{roomId}")
  public BaseResponse<RoomJoinRes> join(@Parameter(hidden = true) @AuthUser Integer userId,
      @Parameter(description = "룸 아이디", example = "1") @PathVariable Integer roomId,
      @RequestBody AlarmCreateReq alarmCreateReq) {

    return new BaseResponse<>(Status.SUCCESS,
        roomService.createAlarmAndJoinRoom(roomId, userId, alarmCreateReq));

  }

  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
          content = @Content(schema = @Schema(implementation = RoomRes.class))
      )})
  @Operation(summary = "그룹 리스트 불러오기")
  @GetMapping()
  public BaseResponse<List<RoomRes>> getList(@Parameter(hidden = true) @AuthUser Integer userId) {

    return new BaseResponse(Status.SUCCESS, roomQueryRepository.findRoomsOrderedByJoinTime(userId));

  }

  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
          content = @Content(schema = @Schema(implementation = RoomUserMissionLogRes.class))),
      @ApiResponse(responseCode = "404", description = "그룹의 해당 멤버가 없습니다."),
      @ApiResponse(responseCode = "500", description = "유저의 대표 아바타 정보를 가져오는데 실패했습니다."),

  })
  @Operation(summary = "그룹게시판의 미션기록 불러오기", description = "그룹의 멤버의 미션기록 보기")
  @GetMapping("/user/mission-log")
  public BaseResponse<RoomUserMissionLogRes> getRoomLogDetailByDate(
      @Parameter(hidden = true) @AuthUser Integer userId,
      @Parameter(description = "룸 아이디", example = "1") @RequestParam Integer roomId,
      @Parameter(description = "기록을 가져올 날의 LocalDate 값", example = "2023-09-20")
      @RequestParam("localDate")
      @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate
  ) {

    return new BaseResponse(Status.SUCCESS,
        roomQueryService.getRoomUserLogList(userId, roomId, localDate));

  }

  @Operation(summary = "그룹 나가기")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다."),
      @ApiResponse(responseCode = "404", description = "그룹의 멤버가 없습니다."),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 알람입니다.")
  })
  @DeleteMapping("{roomId}/member")
  public BaseResponse leaveRoom(@Parameter(hidden = true) @AuthUser Integer userId,
      @PathVariable Integer roomId) {

    roomService.leaveRoom(roomId, userId);
    return new BaseResponse(Status.SUCCESS);

  }


  @Operation(summary = "그룹 디테일 보기 ", description = "그룹 디테일 보기 (설정 화면)")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
          content = @Content(schema = @Schema(implementation = RoomDetailRes.class))),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 알람입니다.")})
  @GetMapping("/{roomId}")
  public BaseResponse<RoomDetailRes> getRoomDetail(
      @Parameter(hidden = true) @AuthUser Integer userId, @PathVariable Integer roomId) {

    return new BaseResponse(Status.SUCCESS, roomQueryService.getRoomDetail(roomId, userId));

  }

  @Operation(summary = "그룹 푸쉬 알림 설정")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다."),
      @ApiResponse(responseCode = "404", description = "그룹의 해당 멤버가 없습니다."),})
  @ResponseBody
  @PostMapping("/user/push/{roomId}")
  public BaseResponse updateAgreePush(@Parameter(hidden = true) @AuthUser Integer userId,
      @PathVariable Integer roomId,
      @Parameter(description = "알람동의 값", example = "true") @RequestParam() boolean agreePush) {

    roomService.updateAgreePush(roomId, userId, agreePush);
    return new BaseResponse(Status.SUCCESS);

  }


  @Operation(summary = "초대 받은 사람에게 보이는 그룹 정보 보기 ", description = "초대 받은 사람에게 보이는 앱 내 페이지")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.",
          content = @Content(schema = @Schema(implementation = RoomInviteInfoRes.class))),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 알람 입니다."),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 룸입니다.")
  })
  @GetMapping("/information")
  public BaseResponse<RoomInviteInfoRes> getRoomIntro(@RequestParam() String invitationCode) {

    return new BaseResponse(Status.SUCCESS, roomQueryService.getRoomInformation(invitationCode));

  }


}
