package com.wakeUpTogetUp.togetUp.group;

import com.wakeUpTogetUp.togetUp.common.exception.BaseException;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.common.ResponseStatus;
import com.wakeUpTogetUp.togetUp.group.dto.request.GroupReq;
import com.wakeUpTogetUp.togetUp.group.dto.response.GroupRes;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/group")
public class GroupController {

    private final GroupService groupService;

    /**그룹생성
     *
     * @param groupReq
     * @return
     */
    @ResponseBody
    @PostMapping() //
    public BaseResponse<ResponseStatus> create(@RequestBody GroupReq groupReq) {
        try {

            groupService.createGroup(groupReq);
            return new BaseResponse(ResponseStatus.SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 모든 그룹 조회
     * @return
     */
    @ResponseBody
    @GetMapping()
    public BaseResponse<List<GroupRes>> getGroup(){
        List<GroupRes> GroupResList = groupService.getGroup();

        return new BaseResponse<>(ResponseStatus.SUCCESS, GroupResList);
    }

    /**
     *
     * @param name
     * @param intro
     * @return
     */
//    @ResponseBody
//    @GetMapping()
//    public BaseResponse<List<GroupRes>> GetGroupBySearch(@RequestParam String name,@RequestParam String intro){
//
//
//        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
//    }


    /**
     *
     * @param groupId
     * @param groupReq
     * @return
     */
    @PatchMapping("{groupId}")
    @ResponseBody
    public BaseResponse<GroupRes> updateGroup(
            @PathVariable Integer groupId,
            @RequestBody GroupReq groupReq
    ) {

//Todo : aws 사진
        GroupRes groupRes = groupService.editGroup( groupId, groupReq);

        return new BaseResponse<>(ResponseStatus.SUCCESS, groupRes);
    }

    /**
     * 그룹 삭제
     * @param groupId
     * @return
     */
    @DeleteMapping("{groupId}")
    @ResponseBody
    public BaseResponse<ResponseStatus> deleteGroup(
            @PathVariable @Valid Integer groupId
    ) {

        groupService.deleteGroup(groupId);

        return new BaseResponse(ResponseStatus.SUCCESS);
    }

}
