package com.appraisers.app.assignments.data;

import com.appraisers.app.assignments.domain.AssignmentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
//TODO pick the best option for the count
public interface AssignmentRequestRepository extends JpaRepository<AssignmentRequest, Long> {
    List<AssignmentRequest> findByDateCreatedBetween(Date from, Date to);
    int countByDateCreatedBetween(Date from, Date to);
    List<AssignmentRequest> findAllByDateCreatedAfterAndDateCreatedBefore(Date after, Date before);
    List<AssignmentRequest> findByDateCreatedAfterAndDateCreatedBefore(Date after, Date before);

    int countAllByDateCreatedGreaterThanEqualAndDateCreatedLessThan(Date from, Date to);

    @Query("select a from AssignmentRequest a where a.dateCreated >= :fromDate and a.dateCreated <=:toDate")
    List<AssignmentRequest> findAllWithDateCreatedBetween(
            @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);
}
