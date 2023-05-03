package com.wakeUpTogetUp.togetUp.mappingGroupUser;

import com.wakeUpTogetUp.togetUp.common.BaseException;
import com.wakeUpTogetUp.togetUp.common.BaseResponse;
import com.wakeUpTogetUp.togetUp.common.BaseResponseStatus;
import com.wakeUpTogetUp.togetUp.group.GroupDto;
import com.wakeUpTogetUp.togetUp.group.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/app/mapping/group/user")
public class MappingGroupUserController {
    //user하고 , 등등 세팅

    private final MappingGroupUserService MappinggroupUserService;

    @ResponseBody
    @PostMapping()
    public BaseResponse<BaseResponseStatus> create(@RequestBody GroupUserDto dto) {
        try {


            MappinggroupUserService.createGroupUser(dto);
            return new BaseResponse(BaseResponseStatus.SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
