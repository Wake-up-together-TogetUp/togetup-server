package com.wakeUpTogetUp.togetUp.api.avatar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

@Setter
@Getter
@Entity
@Table(name = "avatar")
@DynamicInsert
@NoArgsConstructor
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Integer id;

    @Enumerated(EnumType.STRING)
    private AvatarTheme theme;

    @Column(columnDefinition = "INT UNSIGNED")
    private Integer unlockLevel;

    @Column(columnDefinition = "TIMESTAMP")
    private String createdAt;

}
