package com.wakeUpTogetUp.togetUp.api.avatar.model;

import com.wakeUpTogetUp.togetUp.api.avatar.AvatarTheme;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import org.hibernate.annotations.DynamicInsert;

@Setter
@Getter
@Entity
@Table(name="avatar")
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
    private Integer price;

    @Column(columnDefinition = "INT UNSIGNED")
    private Integer unlockLevel;

    // TODO : 더 좋은 방법 찾기
    @Column(columnDefinition = "TIMESTAMP")
    private String createdAt;

//    @PrePersist
//    void createdAt() {
//        this.createdAt = Timestamp.from(Instant.now());
//    }

}
