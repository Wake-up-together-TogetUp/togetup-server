package com.wakeUpTogetUp.togetUp.api.users;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/app/users")
public class UserController  {

    private final UserService userService;
    private final UserRepository userRepository;


    //TODO 알림 받을지 여부 업데이트


}





