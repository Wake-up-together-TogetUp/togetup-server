package com.wakeUpTogetUp.togetUp.missions;

import com.wakeUpTogetUp.togetUp.common.interfaces.CustomRepository;
import com.wakeUpTogetUp.togetUp.missions.model.Mission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MissionDao implements CustomRepository<Mission> {
    private final EntityManager em;
    @Override
    public void save(Mission mission) {
        em.persist(mission);
    }

    @Override
    public Optional<Mission> findById(Integer id) {
        Mission mission = em.find(Mission.class, id);
        return Optional.ofNullable(mission);
    }
}
