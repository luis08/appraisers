package com.appraisers.app.assignments.data;

import com.appraisers.app.assignments.domain.AssignmentRequest;
import com.appraisers.app.assignments.domain.AssignmentRequestMutation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssignmentRequestMutationRepository extends JpaRepository<AssignmentRequestMutation, Long> {
    Optional<AssignmentRequestMutation> findFirstByAssignmentRequestOrderByDateCreatedDesc(AssignmentRequest assignmentRequest);
    List<AssignmentRequestMutation> findByAssignmentRequest(AssignmentRequest assignmentRequest);
    List<AssignmentRequestMutation> findByAssignmentRequestIn(List<AssignmentRequest> assignmentRequests);
}
