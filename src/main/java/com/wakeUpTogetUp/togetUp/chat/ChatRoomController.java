package com.wakeUpTogetUp.togetUp.chat;

import com.wakeUpTogetUp.togetUp.chat.model.ChatRoom;
import com.wakeUpTogetUp.togetUp.chat.model.LoginInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/app/chat")
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomRepository2 chatRoomRepository2;

    @GetMapping("/room")
    public String rooms() {
        System.out.println("채팅");
        return "chat/room";
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAllRoom();
        chatRooms.stream().forEach(room -> room.setUserCount(chatRoomRepository.getUserCount(room.getRoomId())));
        System.out.println("채팅방 불렀음");
        return chatRooms;
    }

    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam() String name) {
        System.out.println("채팅룸");
        ChatRoom chatRoom = ChatRoom.create(name);
        return  chatRoomRepository.createChatRoom(name);//save(chatRoom);//;createChatRoom(name);
    }

    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        System.out.println(roomId+"roodId");

        return "chat/roomdetail";
    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }

    @GetMapping("/user")
    @ResponseBody
    public LoginInfo getUserInfo() {
      //  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = "혜민";//auth.getName();
        System.out.println(name+"name");
        return LoginInfo.builder().name(name).token(jwtTokenProvider.generateToken(name)).build();
    }
}
