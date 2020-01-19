package com.appraisers.storage.impl;

import com.appraisers.storage.local.StorageType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StoragePathService {

    @Value("${com.appraisers.app.storage.home}")
    private String homePath;

    @Value("${com.appraisers.app.storage.assignment.request}")
    private String assignmentRequestPath;

    public String getHomePath() {
        return this.homePath;
    }

    public String getStorageTypePath(StorageType storageType) {
        switch (storageType) {
            case ASSIGNMENT_REQUEST:
                return assignmentRequestPath;
            default:
                throw new IllegalArgumentException("Unsupported storage type;" + storageType);
        }

    }
}
