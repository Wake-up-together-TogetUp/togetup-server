package com.wakeUpTogetUp.togetUp.users;

import com.wakeUpTogetUp.togetUp.common.ResponseStatus;
import com.wakeUpTogetUp.togetUp.common.exception.BaseException;
import com.wakeUpTogetUp.togetUp.users.dto.request.UserReq;
import com.wakeUpTogetUp.togetUp.users.dto.response.UserRes;
import com.wakeUpTogetUp.togetUp.users.model.User;
import com.wakeUpTogetUp.togetUp.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;


import java.util.List;



@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    @Transactional
    public  UserRes createUser(UserReq userReq){

        if(userRepository.countByEmail(userReq.getEmail())!=0)//db에 저장되어 있으면
        {
            new BaseException(ResponseStatus.BAD_REQUEST); //다른 메시지로 해야하나?
        }
        userReq.setPassword(bCryptPasswordEncoder.encode(userReq.getPassword()));
        User user =userReq.toEntity();
        user.setLoginType(LoginType.valueOf(userReq.getLoginType().toUpperCase()));
        //액세스 토큰과 jwtToken, 이외 정보들이 담긴 자바 객체를 다시 전송한다.
        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateAccessToken(savedUser.getId(), secretKey, expiredTimeMs);
        UserRes userRes = new UserRes(savedUser.getId(),jwtToken);

        return userRes;
    }





    //회원 전체 조회
    public  List<User> findMembers(){
        return userRepository.findAll();
    }




}
