package com.appraisers.app.assignments.domain.dto;

import lombok.Getter;
import lombok.Setter;

import static com.google.common.base.Preconditions.checkNotNull;

public class DomainComponentProjection {
    public DomainComponentProjection(Long id, boolean active) {
        checkNotNull(id);
        this.id = id;
        this.active = active;
    }

    @Getter
    private Long id;

    @Getter
    @Setter
    private boolean active;
}
