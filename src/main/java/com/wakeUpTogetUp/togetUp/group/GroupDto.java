package com.wakeUpTogetUp.togetUp.group;


import com.wakeUpTogetUp.togetUp.group.model.Group;
import com.wakeUpTogetUp.togetUp.users.model.User;
import lombok.*;




@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GroupDto {

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
