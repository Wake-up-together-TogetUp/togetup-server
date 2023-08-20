package com.wakeUpTogetUp.togetUp.api.room.model;


import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "room")//예약어를 피해서 작성해야함. group은 예약어임.
@Getter
@Setter
@NoArgsConstructor()
@DynamicInsert
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id=null;


    @Column(name = "name",length = 10)
    private String name;

    @Column(name = "intro",length = 30)
    private String intro;

    @Column(name ="mission",length = 20)
    private String mission;

    @Column(name = "group_icon")
    private String group_icon;


    private String topic;
    /**
     * todo uuid
     */
    @Column(name = "invitation_code",length = 50)
    private String invitationCode;

//    @OneToMany(mappedBy = "Room")
//    private List<MappingGroupUser> mappingGroupUsers= new ArrayList<>();


    @Column(name = "createdAt")
    private Timestamp createdAt;

    @Column(name = "updatedAt")
    private Timestamp updatedAt;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

    @Builder
    public Room(Integer id, String name, String intro, String mission, String topic, String invitationCode, String groupIcon) {
        this.id = id;
        this.name = name;
        this.intro = intro;
        this.mission = mission;
        this.topic = topic;
        this.invitationCode = invitationCode;
        this.group_icon=groupIcon;
    }

//    @Builder
//    public Group(Integer id, String name,String intro,String groupProfileImgLink, String topic) {
//
//        this.id = id;
//        this.name=name;
//
//        this.intro = intro;
//        this.groupProfileImgLink = groupProfileImgLink;
//        this.topic = topic;
//
//    }

}


