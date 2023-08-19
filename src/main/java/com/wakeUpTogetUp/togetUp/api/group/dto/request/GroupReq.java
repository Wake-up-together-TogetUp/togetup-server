package com.wakeUpTogetUp.togetUp.api.group.dto.request;


import com.wakeUpTogetUp.togetUp.api.group.model.Room;
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



    public Room toEntity(String topic){
        return Room.builder()
                .id(id)
                .name(name)
                .intro(intro)
                .topic(topic)
                .build();
    }

}
