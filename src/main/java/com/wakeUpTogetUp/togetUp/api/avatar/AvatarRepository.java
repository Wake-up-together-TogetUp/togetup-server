package com.wakeUpTogetUp.togetUp.api.avatar;

import com.wakeUpTogetUp.togetUp.api.avatar.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Integer> {

}
