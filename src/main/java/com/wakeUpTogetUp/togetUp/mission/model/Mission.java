package com.wakeUpTogetUp.togetUp.mission.model;

import com.wakeUpTogetUp.togetUp.mappingGroupMission.model.MappingGroupMission;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mission")
@DynamicInsert
@Getter
@Setter
@NoArgsConstructor
public class Mission {
    @Builder
    public Mission(String name, String object) {
        this.name = name;
        this.object = object;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String object;
    @Column(columnDefinition = "Timestamp")
    private String createdAt;
    @Column(columnDefinition = "Timestamp")
    private String updatedAt;
    private Boolean isActivated;

    @OneToMany(mappedBy = "mission")
    private List<MappingGroupMission> mappingGroupMissions= new ArrayList<>();
}
