package com.appraisers.resources;

import com.appraisers.app.assignments.service.AssingmentRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.appraisers.app.assignments.dto.AssingmentRequestDto;

@RestController
public class AssignmentResource {
    @Autowired
    private AssingmentRequestService assingmentRequestService;

    @RequestMapping(value = "assignment/save", method = RequestMethod.POST)
    public Integer save(AssingmentRequestDto dto) {
        return assingmentRequestService.save(dto);
    }

    @RequestMapping("assignment")
    public AssingmentRequestDto get() {
        return new AssingmentRequestDto();
    }
}
