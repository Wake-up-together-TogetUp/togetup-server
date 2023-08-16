package com.wakeUpTogetUp.togetUp.api.group.dto.request;


import com.wakeUpTogetUp.togetUp.api.group.model.Group;
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



    public Group toEntity(String topic){
        return Group.builder()
                .id(id)
                .name(name)
                .intro(intro)
                .groupProfileImgLink(groupProfileImgLink)
                .topic(topic)
                .build();
    }

}
