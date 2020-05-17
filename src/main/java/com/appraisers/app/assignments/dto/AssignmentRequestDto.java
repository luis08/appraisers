 package com.appraisers.app.assignments.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

public class AssignmentRequestDto extends AssignmentRequestBaseDto {

    @Getter
    @Setter
    private MultipartFile[] uploadingFiles;
}
