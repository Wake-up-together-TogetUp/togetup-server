package com.wakeUpTogetUp.togetUp.api.room;

import com.wakeUpTogetUp.togetUp.api.alarm.model.Alarm;
import com.wakeUpTogetUp.togetUp.api.mission.model.MissionObject;
import com.wakeUpTogetUp.togetUp.api.room.dto.response.RoomRes;
import com.wakeUpTogetUp.togetUp.api.room.model.Room;
import com.wakeUpTogetUp.togetUp.api.room.model.RoomUser;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;

import java.util.List;
import java.util.Optional;

@Repository
public class RoomQueryRepositoryImpl implements RoomQueryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<RoomRes> findRoomsOrderedByJoinTime(Integer userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<RoomRes> cq = cb.createQuery(RoomRes.class);

        Root<RoomUser> roomUserRoot = cq.from(RoomUser.class);
        Join<RoomUser, Room> roomJoin = roomUserRoot.join("room");
        Join<RoomUser, Alarm> alarmJoin = roomJoin.join("alarms");
        Join<Alarm, MissionObject> missionObjectJoin = alarmJoin.join("missionObject");

        cq.select(cb.construct(
                        RoomRes.class,
                        roomJoin.get("id"),
                        missionObjectJoin.get("icon"),
                        roomJoin.get("name"),
                        missionObjectJoin.get("name"),
                        missionObjectJoin.get("kr")
                )).where(cb.equal(roomUserRoot.get("user").get("id"), userId))
                .orderBy(cb.desc(roomUserRoot.get("createdAt")));

        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public Optional<Room> findById(Integer roomId) {
        List<Room> rooms = entityManager.createQuery("SELECT r FROM Room r WHERE r.id = :roomId", Room.class)
                .setParameter("roomId", roomId)
                .getResultList();

        return rooms.stream().findFirst();
    }

    @Override
    public List<RoomUser> findAllByRoomIdOrderByUserIdAndUserName(Integer userId, Integer roomId) {
        return entityManager.createQuery(
                        "SELECT ru " +
                                "FROM RoomUser ru " +
                                "WHERE ru.room.id = :roomId " +
                                "ORDER BY CASE WHEN ru.user.id = :userId " +
                                "THEN 0 ELSE 1 END, ru.user.name ASC ", RoomUser.class)
                .setParameter("roomId", roomId)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public Optional<Room> findByInvitationCode(String invitationCode) {
        List<Room> rooms = entityManager.createQuery("SELECT r FROM Room r WHERE r.invitationCode = :invitationCode", Room.class)
                .setParameter("invitationCode", invitationCode)
                .getResultList();

        return rooms.stream().findFirst();
    }


}

