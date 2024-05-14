package com.wakeUpTogetUp.togetUp.api.users.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wakeUpTogetUp.togetUp.api.auth.LoginType;
import com.wakeUpTogetUp.togetUp.api.avatar.model.UserAvatar;
import com.wakeUpTogetUp.togetUp.api.room.model.RoomUser;
import com.wakeUpTogetUp.togetUp.api.users.fcmToken.FcmToken;
import com.wakeUpTogetUp.togetUp.api.users.domain.model.UserProgressResult;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
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
import javax.persistence.Transient;
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

    @Column(name = "exp_point")
    private Integer expPoint;

    @Transient
    private double expPercentage;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<FcmToken> fcmToken = new ArrayList<>();

    @JsonIgnore
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
    public User(Integer id, String socialId, String name, String email, LoginType loginType, int level, int expPoint) {
        this.id = id;
        this.socialId = socialId;
        this.name = name;
        this.email = email;
        this.loginType = loginType;
        this.level = level;
        this.expPoint = expPoint;
    }

    @PostConstruct
    private void initExpPercentage() {
        updateExpPercentage(UserProgressCalculator.calculateLevelUpThreshold(this));
    }

    public UserProgressResult progress(int gainedExpPoint) {
        gainExpPoint(gainedExpPoint);

        int currentThreshold = UserProgressCalculator.calculateLevelUpThreshold(this);
        int currentLevel = this.level;

        if (isUserLevelUpAvailable(currentThreshold)) {
            levelUp(currentThreshold);

            int newThreshold = UserProgressCalculator.calculateLevelUpThreshold(this);
            updateExpPercentage(newThreshold);
        } else {
            updateExpPercentage(currentThreshold);
        }

        boolean isUserLevelUp = getLevel() > currentLevel;
        return new UserProgressResult(isUserLevelUp);
    }

    private void gainExpPoint(int point) {
        this.expPoint = this.expPoint + point;
    }

    private boolean isUserLevelUpAvailable(int threshold) {
        return this.expPoint >= threshold;
    }

    private void levelUp(int threshold) {
        this.level++;
        this.expPoint -= threshold;
    }

    private void updateExpPercentage(int threshold) {
        this.expPercentage = calculateExpPercentage(threshold);
    }

    private double calculateExpPercentage(int threshold) {
        double expPercentage = ((double) this.expPoint / threshold) * 100.0;
        return Math.round(expPercentage * 100.0) / 100.0;
    }

    public void changeAgreePush(boolean agreePush) {
        this.setAgreePush(agreePush);
    }
}
