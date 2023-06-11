package com.wakeUpTogetUp.togetUp.chat.model;

import com.wakeUpTogetUp.togetUp.file.dto.response.PostFileRes;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
public class ChatRoom implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;
   @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    private String roomId;
    private String name;
    private String intro;
    private String password;
    private long userCount; // 채팅방 인원수
    private String groupProfileImgLink;


    public static ChatRoom create(String name,String intro,String password) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        chatRoom.intro=intro;
        chatRoom.password=password;
        return chatRoom;
    }

}
