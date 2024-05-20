package com.wakeUpTogetUp.togetUp.api.avatar.domain;

import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.exception.BaseException;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    @OneToMany(mappedBy = "avatar", fetch = FetchType.EAGER)
    private List<AvatarSpeech> avatarSpeeches;

    public String getDefaultSpeech() {
        return avatarSpeeches.stream()
                .filter(AvatarSpeech::isDefaultSpeech)
                .findAny()
                .map(AvatarSpeech::getSpeech)
                .orElseThrow(() -> new BaseException(Status.GET_AVATAR_DEFAULT_SPEECH_FAIL));
    }
}
