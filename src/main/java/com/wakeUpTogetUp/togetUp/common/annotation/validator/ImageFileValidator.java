package com.wakeUpTogetUp.togetUp.common.annotation.validator;

import com.wakeUpTogetUp.togetUp.utils.FileValidator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class ImageFileValidator implements ConstraintValidator<ImageFile, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null) {
            return false;
        }

        return FileValidator.isValidImageFile(file);
    }
}
