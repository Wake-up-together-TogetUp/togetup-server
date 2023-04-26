package com.wakeUpTogetUp.togetUp.routines;

import com.wakeUpTogetUp.togetUp.common.interfaces.CustomRepository;
import com.wakeUpTogetUp.togetUp.routines.model.Routine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoutineDao implements CustomRepository<Routine> {
    private final EntityManager em;
    @Override
    public void save(Routine routine) {
        em.persist(routine);
    }

    @Override
    public Optional<Routine> findById(Integer id) {
        return Optional.ofNullable(em.find(Routine.class, id));
    }
}