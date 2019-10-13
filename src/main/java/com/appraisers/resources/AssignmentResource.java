package com.appraisers.resources;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.appraisers.app.assignments.dto.AssingmentDto;

@RestController
public class AssignmentResource {
    @PostMapping("assignment/save")
    public AssingmentDto save() {
        return new AssingmentDto();
    }

    @RequestMapping("assignment")
    public AssingmentDto get() {
        return new AssingmentDto();
    }
}
