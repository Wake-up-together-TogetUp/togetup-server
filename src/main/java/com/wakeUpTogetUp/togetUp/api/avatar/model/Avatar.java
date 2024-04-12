package com.wakeUpTogetUp.togetUp.api.avatar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "avatar")
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Integer id;

    private String theme;

    private String themeKr;

    @Column(columnDefinition = "INT UNSIGNED")
    private Integer unlockLevel;

    @Column(columnDefinition = "TIMESTAMP")
    private String createdAt;

}
