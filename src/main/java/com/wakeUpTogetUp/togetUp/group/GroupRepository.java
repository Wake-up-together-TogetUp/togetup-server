package com.wakeUpTogetUp.togetUp.group;


import com.wakeUpTogetUp.togetUp.group.model.Group;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

}
