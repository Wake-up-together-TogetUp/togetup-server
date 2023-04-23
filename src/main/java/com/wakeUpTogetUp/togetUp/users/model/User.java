package com.wakeUpTogetUp.togetUp.users.model;

import com.wakeUpTogetUp.togetUp.login.oauth.entity.ProviderType;
import com.wakeUpTogetUp.togetUp.users.model.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements UserDetails {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private String statusMessage;
//    private String avatarImgLink;
//    private UserRole role;
    private Timestamp updatedAt;

    public static User fromEntity(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getUserName(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getStatusMessage(),
//                entity.getAvatarImgLink(),
//                entity.getRole(),
                entity.getUpdatedAt()
        );
    }

//    @Override
//    @JsonIgnore
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority(role.toString()));
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

//    @Override
//    @JsonIgnore
//    public boolean isAccountNonLocked() {
//        return removedAt == null;
//    }
//
//    @Override
//    @JsonIgnore
//    public boolean isCredentialsNonExpired() {
//        return removedAt == null;
//    }
//
//    @Override
//    @JsonIgnore
//    public boolean isEnabled() {
//        return removedAt == null;
//    }
}

