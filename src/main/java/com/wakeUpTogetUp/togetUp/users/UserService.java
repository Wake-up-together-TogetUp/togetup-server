package com.wakeUpTogetUp.togetUp.users;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import com.wakeUpTogetUp.togetUp.common.ResponseStatus;
import com.wakeUpTogetUp.togetUp.common.exception.BaseException;
import com.wakeUpTogetUp.togetUp.group.dto.response.GroupRes;
import com.wakeUpTogetUp.togetUp.group.model.Group;
import com.wakeUpTogetUp.togetUp.users.dto.request.LoginReq;
import com.wakeUpTogetUp.togetUp.users.dto.request.PatchUserReq;
import com.wakeUpTogetUp.togetUp.users.dto.request.UserReq;
import com.wakeUpTogetUp.togetUp.users.dto.response.UserInfoRes;
import com.wakeUpTogetUp.togetUp.users.dto.response.UserRes;
import com.wakeUpTogetUp.togetUp.users.model.User;
import com.wakeUpTogetUp.togetUp.utils.JwtService;
import com.wakeUpTogetUp.togetUp.utils.mappers.GroupMapper;
import com.wakeUpTogetUp.togetUp.utils.mappers.UserMapper;
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

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    /**
     * 회원가입
     * @param userReq
     * @return
     */
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
        //String jwtToken = jwtService.generateAccessToken(savedUser.getId(), secretKey, expiredTimeMs);
        UserRes userRes = new UserRes(savedUser.getId());//,jwtToken);

        return userRes;
    }
    /**
     * 토큰 만들기
     */
    public String createToken(LoginReq loginReq) {
        User user = userRepository.findByEmail(loginReq.getEmail());
              //  .orElseThrow(IllegalArgumentException::new);
        //비밀번호 확인 등의 유효성 검사 진행
        return jwtService.generateAccessToken(user.getId(), secretKey, expiredTimeMs);//createToken(user.getName());
    }
    /**
     * 모든 유저 찾기
     * @return
     */
    public List<UserRes> getUserAll() {

        //모든 유저 가져오기
        List<User> userList= userRepository.findAll();

        // dto 매핑
        ArrayList<UserRes> userResList = new ArrayList<>();
        for(User user : userList) {
            userResList.add(UserMapper.INSTANCE.toUserRes(user));
        }

        return userResList;
    }

    public UserInfoRes getUser(Integer userId) {

        //유저 한명 가져오기
        Optional<User> user= userRepository.findById(userId);

        // dto 매핑
        UserInfoRes userInfoRes = UserMapper.INSTANCE.toUserInfoRes(user.get());

        return userInfoRes;
    }


    @Transactional
    public UserInfoRes editUser(Integer userId, PatchUserReq patchUserReq){
        // 유저 수정
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId)
                .orElseThrow(
                        () -> new BaseException(ResponseStatus.INVALID_USER_ID)
                ));
        user.get().setUserName(patchUserReq.getUsername());
        user.get().setStatusMessage(patchUserReq.getStatusMessage());
        //저장
        User modifiedUser =userRepository.save(user.get());

        //반환
        UserInfoRes userInfoRes = UserMapper.INSTANCE.toUserInfoRes(modifiedUser);

        return userInfoRes;
    }

    @Transactional
    public void deleteUser(Integer userId) {
       //TODO deleted칼럼을 추가?
        userRepository.deleteById(userId);


    }



    //회원 전체 조회
//    public  List<User> findMembers(){
//        return userRepository.findAll();
//    }




}
