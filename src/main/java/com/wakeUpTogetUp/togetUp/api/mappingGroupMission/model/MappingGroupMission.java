//package com.wakeUpTogetUp.togetUp.api.mappingGroupMission.model;
//
//
//import com.wakeUpTogetUp.togetUp.api.group.model.Room;
//import com.wakeUpTogetUp.togetUp.api.mission.model.Mission;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.hibernate.annotations.ColumnDefault;
//
//import javax.persistence.*;
//import java.sql.Timestamp;
//import java.time.Instant;
//
//@Entity
//@Table(name = "mappingGroupMission")
//@Getter
//@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)//모든 정보를 다 가지고 있어야함. 무분별한 객체생성을 막음.
//public class MappingGroupMission {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id = null;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "groupId")
//    private Room room;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "missionId")
//    private Mission mission;
//
//    @Column(name = "name",length = 20)
//    private String groupMissionName;
//
//    @Column(name = "intro",length = 50)
//    private String intro;
//
//
//    @Column(name = "createdAt")
//    private Timestamp createdAt;
//
//    @Column(name = "updatedAt")
//    private Timestamp updatedAt;
//
//
//    @Column(name="isActivated",columnDefinition = "TINYINT", length=1)
//    @ColumnDefault("1")
//    private int isActivated;
//
//
//    @PrePersist
//    void createdAt() {
//        this.createdAt = Timestamp.from(Instant.now());
//    }
//
//    @PreUpdate
//    void updatedAt() {
//        this.updatedAt = Timestamp.from(Instant.now());
//    }
//
//
//
//
//
//}
