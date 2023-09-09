package com.wakeUpTogetUp.togetUp.api.mission.model;

import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmToken;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Table(name = "mission")
@DynamicInsert
@Getter
@Setter
@NoArgsConstructor
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Integer id;

    private String name;

    @Column(name ="created_at", columnDefinition = "Timestamp")
    private String createdAt;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @OneToMany(mappedBy = "mission")
    @Column(name = "mission_object")
    private List<MissionObject> missionObjectList = new ArrayList<>();
}
