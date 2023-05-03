package com.wakeUpTogetUp.togetUp.missions.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

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
}
