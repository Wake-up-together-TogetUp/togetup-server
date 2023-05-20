package com.wakeUpTogetUp.togetUp.users;

import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.config.annotation.NoAuth;
import com.wakeUpTogetUp.togetUp.users.dto.request.LoginReq;
import com.wakeUpTogetUp.togetUp.users.dto.request.PatchUserReq;
import com.wakeUpTogetUp.togetUp.users.dto.request.SocialLoginReq;
import com.wakeUpTogetUp.togetUp.users.dto.request.UserReq;
import com.wakeUpTogetUp.togetUp.users.dto.response.UserInfoRes;
import com.wakeUpTogetUp.togetUp.users.dto.response.UserRes;
import com.wakeUpTogetUp.togetUp.users.dto.response.UserTokenRes;
import com.wakeUpTogetUp.togetUp.users.oauth.GetSocialOAuthRes;
import com.wakeUpTogetUp.togetUp.users.oauth.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/app/users")
public class UserController  {

    private final UserService userService;
    private final OAuthService oAuthService;
    private final UserRepository userRepository;
    /**
     * 일반 회원가입
     * @param userReq
     * @return
     */
    @NoAuth
    @ResponseBody
    @PostMapping() //
    public BaseResponse<UserRes> join(@RequestBody UserReq userReq) {
        try {

            UserRes userRes=userService.createUser(userReq);
            return new BaseResponse<>(Status.SUCCESS,userRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    /**
     * 소셜 회원가입 & 로그인
     * @params SocialLoginReq
     * @return
     */
    @NoAuth
    @ResponseBody
    @PostMapping("/social") //
    public BaseResponse<UserTokenRes> oauthLogin(@RequestBody SocialLoginReq socialLoginReq) {
        try {

           UserTokenRes userTokenRes= userService.socialLogin(socialLoginReq);
            return new BaseResponse<>(Status.SUCCESS,userTokenRes);


        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     *
     * @param loginReq
     * @return
     */
    @NoAuth
    @PostMapping("/login")
    public BaseResponse<UserTokenRes> login(@RequestBody LoginReq loginReq) {

        String token = userService.createToken(loginReq);
        //TODO : refactor
        Integer id = userRepository.findByEmail(loginReq.getEmail()).getId();
        return new BaseResponse<>(Status.SUCCESS,new UserTokenRes(id,token, "bearer"));
    }


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
    @PatchMapping("{userId}")
    @ResponseBody
    public BaseResponse<UserInfoRes> updateUser(
            @PathVariable Integer userId,
            @RequestBody PatchUserReq patchUserReq
    ) {

        UserInfoRes userInfoRes = userService.editUser(userId, patchUserReq);
        return new BaseResponse<>(Status.SUCCESS, userInfoRes);
    }

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

        return new BaseResponse(Status.SUCCESS);
    }


    /**
     * 유저 소셜 로그인으로 리다이렉트 해주는 url
     * [GET] /accounts/auth
     * @return void
     */
   // @NoAuth
    @GetMapping("/auth/{socialLoginType}") //GOOGLE이 들어올 것이다.
    @ResponseBody
    public BaseResponse<String> socialLoginRedirect(@PathVariable(name="socialLoginType") String SocialLoginPath) throws IOException {
        LoginType socialLoginType= LoginType.valueOf(SocialLoginPath.toUpperCase());
        String  redirectURL= oAuthService.request(socialLoginType);

        return new BaseResponse<>(Status.SUCCESS,redirectURL);
    }
    /**
     * Social Login API Server 요청에 의한 callback 을 처리
     * @param socialLoginPath (GOOGLE, FACEBOOK, NAVER, KAKAO)
     * @param code API Server 로부터 넘어오는 code
     * @return SNS Login 요청 결과로 받은 Json 형태의 java 객체 (access_token, jwt_token, user_num 등)
     */


    @ResponseBody
    @GetMapping(value = "/auth/{socialLoginType}/callback")
    public BaseResponse<GetSocialOAuthRes> callback (
            @PathVariable(name = "socialLoginType") String socialLoginPath,
            @RequestParam(name = "code") String code)throws IOException,BaseException{
        System.out.println(">> 소셜 로그인 API 서버로부터 받은 code :"+ code);
        LoginType socialLoginType= LoginType.valueOf(socialLoginPath.toUpperCase());
        GetSocialOAuthRes getSocialOAuthRes=oAuthService.oAuthLogin(socialLoginType,code);

        return new BaseResponse<>(Status.SUCCESS,getSocialOAuthRes);
    }






}





