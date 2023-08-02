package com.wakeUpTogetUp.togetUp.group.model;


import com.wakeUpTogetUp.togetUp.mappingGroupUser.model.MappingGroupUser;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "group2")//예약어를 피해서 작성해야함. group은 예약어임.
@Getter
@Setter
@NoArgsConstructor() //access = AccessLevel.PROTECTED)
@DynamicInsert
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( columnDefinition = "INT UNSIGNED")
    private Integer id=null;


    @Column(name = "name",length = 20)
    private String name;


    @Column(name = "intro",length = 50)
    @ColumnDefault("'ToGetup 그룹방입니다.'")
    private String intro;

    @Column(name ="groupMission",length = 30)
    private String groupMission;

    @Column(name = "groupProfileImgLink",columnDefinition = "TEXT")
    private String groupProfileImgLink;


    private String topic;
    /**
     * todo uuid
     */
    @Column(name = "invitationCode",length = 50)
    private String invitationCode;

    @OneToMany(mappedBy = "group",cascade = CascadeType.ALL)
    private List<MappingGroupUser> mappingGroupUsers= new ArrayList<>();


    /**
     * 어디다 쓰는거였지
     */
    @Column(name="state",columnDefinition = "TINYINT", length=1)
    @ColumnDefault("1")
    private int state;

    @Column(name = "createdAt")
    private Timestamp createdAt;

    @Column(name = "updatedAt")
    private Timestamp updatedAt;
    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

    @Builder
    public Group(Integer id, String name, String intro, String groupMission, String topic, String invitationCode,String groupProfileImgLink) {
        this.id = id;
        this.name = name;
        this.intro = intro;
        this.groupMission = groupMission;
        this.topic = topic;
        this.invitationCode = invitationCode;
        this.groupProfileImgLink=groupProfileImgLink;
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


