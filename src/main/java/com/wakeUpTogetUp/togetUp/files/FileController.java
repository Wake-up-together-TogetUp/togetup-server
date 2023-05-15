package com.wakeUpTogetUp.togetUp.files;


import java.util.List;

import com.wakeUpTogetUp.togetUp.common.exception.BaseException;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.common.ResponseStatus;
import org.hibernate.validator.constraints.ParameterScriptAssert;
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
    @PostMapping("")
    public ResponseEntity<Object> uploadFilesAvatar(
            MultipartFile[] multipartFileList,
            @RequestParam String type
    ) throws Exception {
        // avatar, group, mission
        if(type != "avatar" || type != "group" || type != "mission")
            throw new BaseException(ResponseStatus.BAD_REQUEST_PARAM);
        List<String> imagePathList = fileService.uploadFiles(multipartFileList, type);

        return new ResponseEntity<>(imagePathList, HttpStatus.OK);
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
