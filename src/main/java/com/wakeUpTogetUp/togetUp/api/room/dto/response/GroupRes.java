package com.wakeUpTogetUp.togetUp.api.room.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupRes {


    private Integer id;
    private String name;
    private String intro;
    private String groupProfileImgLink;
    private String password;
    private int state;
}
