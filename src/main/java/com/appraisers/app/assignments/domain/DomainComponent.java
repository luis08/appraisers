package com.appraisers.app.assignments.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class DomainComponent {

    @Getter
    @Setter
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(name = "date_created", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @Getter
    @Setter
    @Column(name = "date_modified")
    private Date dateModified;

    @Getter
    @Setter
    @Column
    private boolean active;

    @Getter
    @Setter
    @Column(name = "inactive_date")
    private Date inactiveDate;
}
