package com.wakeUpTogetUp.togetUp.api.users;

import com.wakeUpTogetUp.togetUp.api.users.dto.request.LoginReq;
import com.wakeUpTogetUp.togetUp.api.users.dto.request.PatchUserReq;
import com.wakeUpTogetUp.togetUp.api.users.dto.request.SocialLoginReq;
import com.wakeUpTogetUp.togetUp.api.users.dto.request.UserReq;
import com.wakeUpTogetUp.togetUp.api.users.model.User;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.api.users.dto.response.UserInfoRes;
import com.wakeUpTogetUp.togetUp.api.users.dto.response.UserRes;
import com.wakeUpTogetUp.togetUp.api.users.dto.response.UserTokenRes;
import com.wakeUpTogetUp.togetUp.utils.JwtService;
import com.wakeUpTogetUp.togetUp.utils.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;






}
