package com.appraisers.resources;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.appraisers.app.assignments.dto.AssingmentRequestDto;

@RestController
public class AssignmentResource {
    @PostMapping("assignment/save")
    public AssingmentRequestDto save() {
        return new AssingmentRequestDto();
    }

    @RequestMapping("assignment")
    public AssingmentRequestDto get() {
        return new AssingmentRequestDto();
    }
}
