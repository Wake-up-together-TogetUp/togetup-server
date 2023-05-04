package com.wakeUpTogetUp.togetUp.avatar.model;

import com.wakeUpTogetUp.togetUp.users.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

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
    private Integer id = null;

//    @Column(name = "avatarPicLink",columnDefinition = "TEXT")
//    private String avatarPicLink;

    @Column(name = "hair",length = 30)
    private String hair;
    @Column(name = "top",length = 30)
    private String top;

    @Column(name = "bottom",length = 30)
    private String bottom;
    @Column(name = "effect",length = 30)
    private String effect;

    @Column(name = "createdAt")
    private Timestamp createdAt;

    @Column(name = "updatedAt")
    private Timestamp updatedAt;


    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(name="isActivated",columnDefinition = "TINYINT", length=1)
    @ColumnDefault("1")
    private int isActivated;

    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }


}
