package com.wakeUpTogetUp.togetUp.mappingGroupUser;

import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.mappingGroupUser.dto.request.MappingGroupUserReq;
import com.wakeUpTogetUp.togetUp.mappingGroupUser.dto.response.MappingGroupUserRes;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/mapping/group/user")
public class MappingGroupUserController {
    //user하고 , 등등 세팅

    private final MappingGroupUserService mappingGroupUserService;

    /**
     * 사용자 그룹 가입
     * @param userId
     * @param groupId
     * @param mappingGroupUserReq
     * @return
     */

    @ResponseBody
    @PostMapping("/{userId}/{groupId}/registration")
    public BaseResponse create(@PathVariable("userId") Integer userId, @PathVariable("groupId")Integer groupId,@RequestBody MappingGroupUserReq mappingGroupUserReq) {
        try {

            //TODO : 그 그룹에 Host 유저가 있는지. (host는 한명)
            //TODO : 이미 가입한 그룹인지
            Integer groupUserId = mappingGroupUserService.createGroupUser(userId,groupId,mappingGroupUserReq);
            return new BaseResponse(Status.SUCCESS,groupUserId);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 어떤 그룹에 가입한 유저 불러오기
     * @param groupId
     * @return
     */
    @ResponseBody
    @GetMapping("{groupId}/user")
    public BaseResponse<List<MappingGroupUserRes>> GetUserInGroup(@PathVariable Integer groupId){
        List<MappingGroupUserRes> mappingGroupUserResList = mappingGroupUserService.getUserByGroupId(groupId);

        return new BaseResponse<>(Status.SUCCESS, mappingGroupUserResList);
    }

    /**
     * 유저가 가입한 그룹 불러오기
     * @param userId
     * @return
     */
    @ResponseBody
    @GetMapping("{userId}/group")
    public BaseResponse<List<MappingGroupUserRes>> GetGroupOfUser(@PathVariable Integer userId){
        List<MappingGroupUserRes> mappingGroupUserResList = mappingGroupUserService.getGroupByUserId(userId);

        return new BaseResponse<>(Status.SUCCESS, mappingGroupUserResList);
    }


    /**
     * 유저가 가입한 그룹 설정 수정  ex) 알림 끝
     * @param userId
     * @param groupId
     * @param mappingGroupUserReq
     * @return
     */
    @PatchMapping("{userId}/{groupId}")
    @ResponseBody
    public BaseResponse<MappingGroupUserRes> updateMappingGroupUser(
            @PathVariable Integer userId,
            @PathVariable Integer groupId,
            @RequestBody MappingGroupUserReq mappingGroupUserReq
    ) {


        MappingGroupUserRes mappingGroupUserRes = mappingGroupUserService.editMappingGroupUser(userId, groupId, mappingGroupUserReq);

        return new BaseResponse<>(Status.SUCCESS, mappingGroupUserRes);
    }

    /**
     * 그룹 탈퇴
     * @param userId
     * @param groupId
     * @return
     */
    @DeleteMapping("{userId}/{groupId}")
    @ResponseBody
    public BaseResponse<Status> deleteMappingGroupUser(
            @PathVariable @Valid Integer userId,
            @PathVariable @Valid Integer groupId
    ) {

        mappingGroupUserService.deleteMappingGroupUser(userId,groupId);

        return new BaseResponse(Status.SUCCESS);
    }

}
