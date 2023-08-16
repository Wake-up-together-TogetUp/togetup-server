package com.wakeUpTogetUp.togetUp.api.users;

import com.wakeUpTogetUp.togetUp.api.users.dto.request.LoginReq;
import com.wakeUpTogetUp.togetUp.api.users.dto.request.PatchUserReq;
import com.wakeUpTogetUp.togetUp.api.users.dto.request.SocialLoginReq;
import com.wakeUpTogetUp.togetUp.api.users.dto.request.UserReq;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.config.annotation.NoAuth;
import com.wakeUpTogetUp.togetUp.api.users.dto.response.UserInfoRes;
import com.wakeUpTogetUp.togetUp.api.users.dto.response.UserRes;
import com.wakeUpTogetUp.togetUp.api.users.dto.response.UserTokenRes;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/app/users")
public class UserController  {

    private final UserService userService;
    private final UserRepository userRepository;
    /**
     * 일반 회원가입
     * @param userReq
     * @return
     */
//    @NoAuth
//    @ResponseBody
//    @PostMapping() //
//    @Transactional()
//    public BaseResponse<UserRes> join(@RequestBody UserReq userReq) {
//        try {
//
//            UserRes userRes=userService.createUser(userReq);
//            return new BaseResponse<>(Status.SUCCESS,userRes);
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
    /**
     * 소셜 회원가입 & 로그인
     * @params SocialLoginReq
     * @return
     */
//    @NoAuth
//    @ResponseBody
//    @PostMapping("/social") //
//    public BaseResponse<UserTokenRes> oauthLogin(@RequestBody SocialLoginReq socialLoginReq) {
//        try {
//
//           UserTokenRes userTokenRes= userService.socialLogin(socialLoginReq);
//            return new BaseResponse<>(Status.SUCCESS,userTokenRes);
//
//
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }

    /**
     *
     * @param loginReq
     * @return
     */
//    @NoAuth
//    @PostMapping("/login")
//    public BaseResponse<UserTokenRes> login(@RequestBody LoginReq loginReq) {
//
//        String token = userService.createToken(loginReq);
//        //TODO : refactor
//        Integer id = userRepository.findByEmail(loginReq.getEmail()).getId();
//        return new BaseResponse<>(Status.SUCCESS,new UserTokenRes(id,token, "bearer"));
//    }


    /**
     *유저 전체 GET
     * @return
     */
    @NoAuth
    @ResponseBody
    @GetMapping("list")
    public BaseResponse<List<UserRes>> getUserAll(){
        List<UserRes> UserResList = userService.getUserAll();

        return new BaseResponse<>(Status.SUCCESS, UserResList);
    }


    /**
     * 유저 한명 get
     * @param userId
     * @return
     */

    @ResponseBody
    @GetMapping("{userId}")
    public BaseResponse<UserInfoRes> getUser(@PathVariable Integer userId){
        System.out.println("정보");
        UserInfoRes userInfoRes = userService.getUser(userId);
        return new BaseResponse<>(Status.SUCCESS, userInfoRes);
    }

    /**
     *
     * @param userId
     * @param patchUserReq
     * @return
     */
//    @PatchMapping("{userId}")
//    @ResponseBody
//    public BaseResponse<UserInfoRes> updateUser(
//            @PathVariable Integer userId,
//            @RequestBody PatchUserReq patchUserReq
//    ) {
//
//        UserInfoRes userInfoRes = userService.editUser(userId, patchUserReq);
//        return new BaseResponse<>(Status.SUCCESS, userInfoRes);
//    }

    /**
     * 유저삭제
     * @param userId
     * @return
     */
    @DeleteMapping("{userId}")
    @ResponseBody
    public BaseResponse<Status> deleteUser(
            @PathVariable @Valid Integer userId
    ) {

        userService.deleteUser(userId);

        return new BaseResponse<>(Status.SUCCESS);
    }


}





