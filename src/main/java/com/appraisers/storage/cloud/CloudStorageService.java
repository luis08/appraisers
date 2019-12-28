package com.appraisers.storage.cloud;


import com.appraisers.app.FileService;

import java.util.Optional;

/**
 * Storage Service for cloud implementations.
 */
public interface CloudStorageService extends FileService {
    Optional<byte[]> get(String id, String type);
    Optional<String> create(Long localId, byte[] data);
}
