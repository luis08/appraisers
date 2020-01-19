package com.appraisers.storage.local;

import com.appraisers.app.FileService;

import java.util.Optional;

public interface LocalStorageService extends FileService {
    Optional<byte[]> get(Long id);
}
