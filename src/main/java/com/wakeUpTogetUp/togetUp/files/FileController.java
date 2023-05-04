package com.wakeUpTogetUp.togetUp.files;


import java.util.List;

import com.wakeUpTogetUp.togetUp.common.exception.BaseException;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.common.exception.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/app/files")
public class FileController {

    @Autowired
    private final FileService fileService;

    public FileController(FileService fileService){
        this.fileService = fileService;
    }

    /**
     * upload 메소드
     * @param multipartFileList
     * @return
     * @throws Exception
     */
    @PostMapping("/avatar")
    public ResponseEntity<Object> uploadFilesAvatar(MultipartFile[] multipartFileList) throws Exception {
        // fileType이 없으면 Error Bad Request
        // 아바타 : avatarImage
        List<String> imagePathList = fileService.uploadFiles(multipartFileList, "avatar");

        return new ResponseEntity<Object>(imagePathList, HttpStatus.OK);
    }

    @PostMapping("/mission")
    public ResponseEntity<Object> uploadFilesMission(MultipartFile[] multipartFileList) throws Exception {
        // fileType이 없으면 Error Bad Request
        // 아바타 : avatarImage
        // 미션수행사진 : missionImage
        List<String> imagePathList = fileService.uploadFiles(multipartFileList, "mission");

        return new ResponseEntity<Object>(imagePathList, HttpStatus.OK);
    }

    @DeleteMapping("")
    public BaseResponse<Object> deleteFile(@RequestParam(value = "fileName") String fileName) {
        String result = fileService.deleteFile(fileName);

        if(result.equals("delete process success")) {
            return new BaseResponse<>(ResponseStatus.SUCCESS);
        } else {
            return new BaseResponse<>(new BaseException(ResponseStatus.FILE_NOT_FOUND));
        }
    }
}
