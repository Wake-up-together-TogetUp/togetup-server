package com.wakeUpTogetUp.togetUp.api.mission.model;

//import com.wakeUpTogetUp.togetUp.api.mappingGroupMission.model.MappingGroupMission;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;

@ToString
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
    @Column(columnDefinition = "INT UNSIGNED")
    private int id;
    private String name;
    private String object;
    @Column(name ="created_at",columnDefinition = "Timestamp")
    private String createdAt;
    @Column(name = "updated_at" ,columnDefinition = "Timestamp")
    private String updatedAt;
    @Column(name = "is_active")
    private Boolean isActive = true;

//    @OneToMany(mappedBy = "mission")
//    private List<MappingGroupMission> mappingGroupMissions= new ArrayList<>();
}
