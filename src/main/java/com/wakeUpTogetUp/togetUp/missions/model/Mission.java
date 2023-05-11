package com.wakeUpTogetUp.togetUp.missions.model;

import com.wakeUpTogetUp.togetUp.mappingGroupMission.model.MappingGroupMission;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mission")
@DynamicInsert
@NoArgsConstructor
@Getter
@Setter
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
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private boolean isActivated;

    @OneToMany(mappedBy = "mission")
    private List<MappingGroupMission> mappingGroupMissions= new ArrayList<>();
}
