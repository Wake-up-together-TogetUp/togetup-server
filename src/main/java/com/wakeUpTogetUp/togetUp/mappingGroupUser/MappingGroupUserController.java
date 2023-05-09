package com.wakeUpTogetUp.togetUp.mappingGroupUser;

import com.wakeUpTogetUp.togetUp.common.BaseException;
import com.wakeUpTogetUp.togetUp.common.BaseResponse;
import com.wakeUpTogetUp.togetUp.common.BaseResponseStatus;
import com.wakeUpTogetUp.togetUp.group.GroupDto;
import com.wakeUpTogetUp.togetUp.mappingGroupUser.dto.request.MappingGroupUserReq;
import com.wakeUpTogetUp.togetUp.group.GroupService;
import com.wakeUpTogetUp.togetUp.mappingGroupUser.dto.response.MappingGroupUserRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app/mapping/group/user")
public class MappingGroupUserController {
    //user하고 , 등등 세팅

    private final MappingGroupUserService mappinggroupUserService;

    /**
     * 사용자 그룹 가입
     * @param userId
     * @param groupId
     * @param mappingGroupUserReq
     * @return
     */

    @ResponseBody
    @PostMapping("/{userId}/{groupId}/registration")
    public BaseResponse create(@PathVariable("userId") Integer userId,@PathVariable("groupId")Integer groupId,@RequestBody MappingGroupUserReq mappingGroupUserReq) {
        try {

            //TODO : 그 그룹에 Host 유저가 있는지. (host는 한명)
            //TODO : 이미 가입한 그룹인지
            Integer groupUserId = mappinggroupUserService.createGroupUser(userId,groupId,mappingGroupUserReq);
            return new BaseResponse(BaseResponseStatus.SUCCESS,groupUserId);
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
        List<MappingGroupUserRes> mappingGroupUserResList = mappinggroupUserService.getUserByGroupId(groupId);

        return new BaseResponse<>(BaseResponseStatus.SUCCESS, mappingGroupUserResList);
    }
}
