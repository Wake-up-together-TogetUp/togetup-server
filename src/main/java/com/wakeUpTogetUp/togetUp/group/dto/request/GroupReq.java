package com.wakeUpTogetUp.togetUp.group.dto.request;


import com.wakeUpTogetUp.togetUp.group.model.Group;
import lombok.*;




@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GroupReq {

    private Integer id;
    private String name;
    private String intro;
    private String groupProfileImgLink;
    private String password;


    public Group toEntity(){
        return Group.builder()
                .id(id)
                .name(name)
                .password(password)
                .intro(intro)
                .groupProfileImgLink(groupProfileImgLink)
                .build();
    }

}
