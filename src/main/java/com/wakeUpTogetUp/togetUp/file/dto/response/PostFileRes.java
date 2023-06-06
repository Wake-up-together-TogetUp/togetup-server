package com.wakeUpTogetUp.togetUp.file.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostFileRes {
    private List<String> imagePathList;
}
