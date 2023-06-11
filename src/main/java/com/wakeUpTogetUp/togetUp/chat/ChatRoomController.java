package com.wakeUpTogetUp.togetUp.chat;

import com.wakeUpTogetUp.togetUp.chat.dto.request.PostChatRoomReq;
import com.wakeUpTogetUp.togetUp.chat.model.ChatRoom;
import com.wakeUpTogetUp.togetUp.chat.model.LoginInfo;
import com.wakeUpTogetUp.togetUp.common.Status;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.file.FileService;
import com.wakeUpTogetUp.togetUp.group.dto.response.GroupRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
@RequiredArgsConstructor
@Controller
@RequestMapping("/app/chat")
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ChatRoomRepository2 chatRoomRepository2;
    private final FileService fileService;
    @GetMapping("/room")
    public String rooms() {

        return "chat/room";
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAllRoom();
        chatRooms.stream().forEach(room -> room.setUserCount(chatRoomRepository.getUserCount(room.getRoomId())));

        return chatRooms;
    }

    /**
     *
     * @param postChatRoomReq
     * @return
     * @throws Exception
     */
    @PostMapping("/room")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<List<ChatRoom>> createRoom(@RequestBody PostChatRoomReq postChatRoomReq) throws Exception {
       // String fils = fileService.uploadFile(files, "mission");
        ChatRoom chatRoom= chatRoomRepository.createChatRoom(postChatRoomReq);
      //  chatRoom.setGroupProfileImgLink(fils);
        return  new BaseResponse(Status.SUCCESS,chatRoom);//save(chatRoom);//;createChatRoom(name);
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
