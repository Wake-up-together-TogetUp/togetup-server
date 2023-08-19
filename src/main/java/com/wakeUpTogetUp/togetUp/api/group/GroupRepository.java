package com.wakeUpTogetUp.togetUp.api.group;


import com.wakeUpTogetUp.togetUp.api.group.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {



}
