package com.wakeUpTogetUp.togetUp.alarms;

import com.wakeUpTogetUp.togetUp.alarms.model.Alarm;
import com.wakeUpTogetUp.togetUp.common.interfaces.CustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AlarmDao implements CustomRepository<Alarm> {
    private final EntityManager em;
    @Override
    public void save(Alarm alarm) {
        em.persist(alarm);
    }

    @Override
    public Optional<Alarm> findById(Long id) {
        Alarm alarm = em.find(Alarm.class, id);
        return Optional.ofNullable(alarm);
    }

//    public List<Alarm> findByUserId(Long userId) {
//        List<Alarm> result = em.createQuery("select a from Alarm a where a.user.id = :userId", Alarm.class)
//                .setParameter("userId", userId)
//                .getResultList();
//
//        return result;
//    }
}
