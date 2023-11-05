package com.wakeUpTogetUp.togetUp.api.file;

import com.wakeUpTogetUp.togetUp.api.file.dto.response.PostFileRes;
import com.wakeUpTogetUp.togetUp.common.dto.BaseResponse;
import com.wakeUpTogetUp.togetUp.common.Status;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "파일(File)")
@RestController
@RequestMapping("/app/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    /**
     * upload 메소드
     *
     * @param type
     * @return
     * @throws Exception
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<PostFileRes> uploadFiles(
            @RequestPart final MultipartFile[] files,
            @RequestParam final String type
    ) throws Exception {
        return new BaseResponse(Status.SUCCESS, fileService.uploadFiles(files, type));
    }

    /**
     * 파일 삭제
     *
     * @param name
     * @return
     */
    @DeleteMapping("")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<Object> deleteFile(@RequestParam final String name) {
        fileService.deleteFile(name);
        return new BaseResponse<>(Status.SUCCESS);
    }
}
