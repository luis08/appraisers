package com.appraisers.app.assignments.domain.dto;

import com.appraisers.app.assignments.domain.DomainComponent;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

public class DomainComponentProjection {
    private final DomainComponent component;

    public DomainComponentProjection(DomainComponent component) {
        checkNotNull(component);
        this.component = component;
    }

    public Long getId() {
        return component.getId();
    }

    public boolean isActive() {
        return component.isActive();
    }

    public Date getDateCreated() {
        return component.getDateCreated();
    }
}
