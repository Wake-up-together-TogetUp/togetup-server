package com.wakeUpTogetUp.togetUp.api.room;

import com.wakeUpTogetUp.togetUp.api.room.dto.request.GroupReq;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.GroupRes;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.common.Status;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@Tag(name = "그룹")
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
    public BaseResponse<Status> create(@RequestBody GroupReq groupReq) {
        try {

            groupService.createGroup(groupReq);
            return new BaseResponse(Status.SUCCESS);
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

        return new BaseResponse<>(Status.SUCCESS, GroupResList);
    }




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

        return new BaseResponse<>(Status.SUCCESS, groupRes);
    }

    /**
     * 그룹 삭제
     * @param groupId
     * @return
     */

    @DeleteMapping("{groupId}")
    @ResponseBody
    public BaseResponse<Status> deleteGroup(
            @PathVariable @Valid Integer groupId
    ) {

        groupService.deleteGroup(groupId);

        return new BaseResponse(Status.SUCCESS);
    }

}
