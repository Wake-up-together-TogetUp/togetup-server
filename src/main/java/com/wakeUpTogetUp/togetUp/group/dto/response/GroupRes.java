package com.wakeUpTogetUp.togetUp.group.dto.response;

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
