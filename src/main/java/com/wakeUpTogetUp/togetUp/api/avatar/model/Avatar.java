package com.wakeUpTogetUp.togetUp.api.avatar.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name="avatar")
@NoArgsConstructor
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Integer id;

    private String theme;

    @Column(columnDefinition = "TEXT")
    private String avatarImgLink;

    @Column(columnDefinition = "INT UNSIGNED")
    private Integer price;

    @Column(name = "createdAt")
    private Timestamp createdAt;

    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
    }

}
