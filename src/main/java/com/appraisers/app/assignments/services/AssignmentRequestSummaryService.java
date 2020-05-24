package com.appraisers.app.assignments.services;

import com.appraisers.app.assignments.dto.AssignmentRequestSummaryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AssignmentRequestSummaryService {
    Page<AssignmentRequestSummaryDto> findAll(Pageable pageable);
}
