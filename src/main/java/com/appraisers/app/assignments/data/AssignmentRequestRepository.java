package com.appraisers.app.assignments.data;

import com.appraisers.app.assignments.domain.AssignmentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRequestRepository extends JpaRepository<AssignmentRequest, Long> {
}
