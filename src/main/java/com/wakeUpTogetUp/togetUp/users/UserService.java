package com.wakeUpTogetUp.togetUp.users;

import com.wakeUpTogetUp.togetUp.users.model.User;
import com.wakeUpTogetUp.togetUp.users.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;


import java.io.IOException;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public  Integer createUser(UserForm form){
        User user =form.toEntity();
        userRepository.save(user);
        return user.getId();
    }



    //회원 전체 조회
    public  List<User> findMembers(){
        return userRepository.findAll();
    }




}
