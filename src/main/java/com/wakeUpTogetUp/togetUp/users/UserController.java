package com.wakeUpTogetUp.togetUp.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;

//    jwt 구현 필요
//    @Autowired
//    private final JwtService jwtService;


    public UserController(UserProvider userProvider, UserService userService
//            , JwtService jwtService
    ) {
        this.userProvider = userProvider;
        this.userService = userService;
//        this.jwtService = jwtService;
    }

    /**
     * 특정 회원 조회 API (프로필)
     * [GET] /app/users/:userId/profile
     */
//    @ResponseBody
//    @GetMapping("/{userID}/profile") // (GET) 127.0.0.1:9000/app/users/:userId/profile
//    public BaseResponse<GetUserRes> getUserProfile(@PathVariable("userID") int userID) {
//        try {
//            GetUserRes getUserRes = userProvider.getUserProfile(userID);
//            return new BaseResponse<>(getUserRes);
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
}