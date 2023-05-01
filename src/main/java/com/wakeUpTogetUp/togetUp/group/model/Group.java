package com.wakeUpTogetUp.togetUp.group.model;


import com.wakeUpTogetUp.togetUp.mappingGroupUser.model.MappingGroupUser;
import com.wakeUpTogetUp.togetUp.users.LoginType;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "group2")//예약어를 피해서 작성해야함. group은 예약어임.
@Getter
@Setter
@NoArgsConstructor()//access = AccessLevel.PROTECTED)
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id=null;


    @Column(name = "name",length = 10)
    private String name;

    @Column(name = "intro",length = 50)
    private String intro;

    @Column(name = "groupProfileImgLink",columnDefinition = "TEXT")
    private String groupProfileImgLink;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "group")
    private List<MappingGroupUser> mappingGroupUsers= new ArrayList<>();


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



}


