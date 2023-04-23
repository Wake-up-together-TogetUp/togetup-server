package com.wakeUpTogetUp.togetUp.users;

import com.wakeUpTogetUp.togetUp.common.BaseException;
import com.wakeUpTogetUp.togetUp.common.BaseResponseStatus;
import com.wakeUpTogetUp.togetUp.users.model.GetUserRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//import com.wakeUpTogetUp.togetUp.alram.model.Alarm;
import com.wakeUpTogetUp.togetUp.users.model.User;
import com.wakeUpTogetUp.togetUp.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

 //   private final UserEntityRepository userRepository;
 //   private final AlarmEntityRepository alarmEntityRepository;
    @Autowired
    private final BCryptPasswordEncoder encoder;
 //   private final UserCacheRepository redisRepository;



//    @Value("${jwt.secret-key}")
//    private String secretKey;
//
//    @Value("${jwt.token.expired-time-ms}")
//    private Long expiredTimeMs;


    public User loadUserByNickname(String nickName) throws UsernameNotFoundException {

        //db 연동 후 바꾸기
        UserEntity tempUser = new UserEntity();
        return User.fromEntity(tempUser);
//        return redisRepository.getUser(nickName).orElseGet(
//                () -> userRepository.findByUserNickName(nickName).map(User::fromEntity).orElseThrow(
//                        () -> new TogetUpApplicationException(ErrorCode.USER_NOT_FOUND, String.format("nickName is %s", nickName))
//                ));
    }

//    public String login(String nickName, String password) {
//        User savedUser = loadUserByNickname(nickName);
//       // redisRepository.setUser(savedUser);
//        if (!encoder.matches(password, savedUser.getPassword())) {
//            throw new TogetUpApplicationException(ErrorCode.INVALID_PASSWORD);
//        }
//        return JwtService.generateAccessToken(nickName, secretKey, expiredTimeMs);
//    }


    @Transactional
    public User join(String nickName, String password) {
        // check the userId not exist
//        userRepository.findByNickName(nickName).ifPresent(it -> {
//            throw new TogetUpApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("NickName is %s", userName));
//        });
//
 //       UserEntity savedUser = userRepository.save(UserEntity.of(nickName, encoder.encode(password)));
        //DB 연동 후 바꾸기
        UserEntity savedUser = new UserEntity();
        return User.fromEntity(savedUser);

    }

//Alarm 개발 후

//    @Transactional
//    public Page<Alarm> alarmList(Integer userId, Pageable pageable) {
//        return alarmEntityRepository.findAllByUserId(userId, pageable).map(Alarm::fromEntity);
//    }

    // 실험
    public GetUserRes getUser(){
        throw new BaseException(BaseResponseStatus.BAD_REQUEST);
    }
}
