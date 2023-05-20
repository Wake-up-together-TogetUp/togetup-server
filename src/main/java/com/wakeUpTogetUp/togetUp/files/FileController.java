package com.wakeUpTogetUp.togetUp.files;


import java.util.List;

import com.wakeUpTogetUp.togetUp.exception.BaseException;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.common.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/app/files")
public class FileController {
    @Autowired
    private FileService fileService;

    /**
     * upload 메소드
     * @param multipartFileList
     * @return
     * @throws Exception
     */
    @PostMapping("")
    public ResponseEntity<Object> uploadFiles(
            MultipartFile[] multipartFileList,
            @RequestParam String type
    ) throws Exception {
        // avatar, group, mission
        if(type.equals("avatar") || type.equals("group") || type.equals("mission") ) {
            List<String> imagePathList = fileService.uploadFiles(multipartFileList, type);

            return new ResponseEntity<>(imagePathList, HttpStatus.OK);
        } else
            throw new BaseException(ResponseStatus.BAD_REQUEST_PARAM);
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
