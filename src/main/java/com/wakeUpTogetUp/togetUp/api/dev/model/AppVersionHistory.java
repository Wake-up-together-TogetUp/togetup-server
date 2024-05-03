package com.wakeUpTogetUp.togetUp.api.dev.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "app_version_history")
@Getter
public class AppVersionHistory {

    @Id
    @Column(nullable = false)
    private String version;

    @Column(nullable = false)
    private String appStoreUrl;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
