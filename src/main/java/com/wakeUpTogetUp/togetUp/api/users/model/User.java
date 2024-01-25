package com.wakeUpTogetUp.togetUp.api.users.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wakeUpTogetUp.togetUp.api.auth.LoginType;
import com.wakeUpTogetUp.togetUp.api.avatar.model.Avatar;
import com.wakeUpTogetUp.togetUp.api.room.model.RoomUser;
import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmToken;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Setter
@Getter
@Entity
@SQLDelete(sql = "UPDATE user SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = false")
@Table(name = "user")
@NoArgsConstructor
@DynamicInsert
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT UNSIGNED")
    private Integer id = null;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "social_id")
    private String socialId;

    @Column(name = "login_type", length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private LoginType loginType;

    @Column(name = "agree_push")
    private boolean agreePush;

    @Column(name = "level")
    private Integer level;

    @Column(name = "experience")
    private Integer expPoint;

    @Column(name = "point")
    private Integer point;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "is_deleted")
    private boolean isDeleted;


    @OneToMany(mappedBy = "user")
    private List<FcmToken> fcmToken = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserAvatar> userAvatar = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<RoomUser> roomUsers = new ArrayList<>();

    @PrePersist
    void registeredAt() {
        this.createdAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }


    @Builder
    public User(Integer id, String socialId, String name, String email, LoginType loginType,
                int level, int expPoint, int point
    ) {
        this.id = id;
        this.socialId = socialId;
        this.name = name;
        this.email = email;
        this.loginType = loginType;
        this.level = level;
        this.expPoint = expPoint;
        this.point = point;
    }

    public void gainExpPoint(int expPoint) {
        this.setExpPoint(this.getExpPoint() + expPoint);
    }

    public boolean checkUserLevelUpAvailable(int threshold) {
        return this.getExpPoint() >= threshold;
    }

    public void progress() {
        int threshold = calculateLevelUpThreshold();

        if (checkUserLevelUpAvailable(threshold)) {
            levelUp(threshold);
        }
    }

    public void levelUp(int threshold) {
        // 레벨 1 증가, 경험치 초기화, 포인트 증가
        this.setLevel(this.getLevel() + 1);
        this.setExpPoint(this.getExpPoint() - threshold);
        this.setPoint(this.getPoint() + 25);
    }

    public int calculateLevelUpThreshold() {
        return 10 + 16 * (level - 1);
    }

    public double calculateExpPercentage() {
        double expPercentage = ((double) expPoint / calculateLevelUpThreshold()) * 100.0;
        return Math.round(expPercentage * 100.0) / 100.0;
    }

    public void purchaseAvatar(Avatar avatar) {
        if (this.getPoint() < avatar.getPrice()) {
            throw new BaseException(Status.USER_POINT_LACKED);
        } else {
            this.addUserPoint(-avatar.getPrice());
        }
    }

    public void addUserPoint(int amount) {
        this.setPoint(this.point + amount);
    }
}
