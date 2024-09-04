package com.wakeUpTogetUp.togetUp.api.notification;

import com.wakeUpTogetUp.togetUp.api.auth.AuthUser;
import com.wakeUpTogetUp.togetUp.api.notification.dto.request.BroadCastNotificationReq;
import com.wakeUpTogetUp.togetUp.api.notification.dto.response.NotificationListRes;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Notification", description = "알림")
@RestController
@RequiredArgsConstructor
@RequestMapping("/app/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "전체 유저에게 fcm 노티 전송")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.")})
    @PostMapping("/broadcast")
    public BaseResponse<Status> sendNotificationToAllUsers(
            @Valid @RequestBody BroadCastNotificationReq broadCastNotificationReq
    ) {
        notificationService.sendNotificationToAllUsers(broadCastNotificationReq.getTitle(), broadCastNotificationReq.getBody());

        return new BaseResponse(Status.SUCCESS);
    }

    @Operation(summary = "notifiaction list 가져오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.")})
    @GetMapping()
    public BaseResponse<NotificationListRes> getNotificationList(@Parameter(hidden = true) @AuthUser Integer userId
    ) {

        return new BaseResponse(Status.SUCCESS, notificationService.getList(userId));
    }

    @Operation(summary = "notifiaction isRead 를 true로 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.")})
    @PatchMapping
    public BaseResponse<Status> readNotification(@Parameter(hidden = true) @AuthUser Integer userId, @Parameter(example = "1") @RequestParam Integer notificationId
    ) {
        notificationService.updateIsReadToTrue(userId, notificationId);
        return new BaseResponse(Status.SUCCESS);
    }

    @Operation(summary = "notifiaction을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.")})
    @DeleteMapping
    public BaseResponse<Status> deleteNotification(@Parameter(hidden = true) @AuthUser Integer userId, @Parameter(example = "1") @RequestParam Integer notificationId
    ) {
        notificationService.delete(userId, notificationId);
        return new BaseResponse(Status.SUCCESS);
    }


}
