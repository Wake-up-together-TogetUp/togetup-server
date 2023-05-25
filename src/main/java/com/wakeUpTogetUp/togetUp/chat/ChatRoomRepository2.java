package com.wakeUpTogetUp.togetUp.chat;

import com.wakeUpTogetUp.togetUp.chat.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository2 extends JpaRepository<ChatRoom,String> {

   // List<ChatRoom> findChatRoomsByCustomer(UserIdDto customer);
    //List<ChatRoom> findChatRoomsByStore(UserIdDto store);
}