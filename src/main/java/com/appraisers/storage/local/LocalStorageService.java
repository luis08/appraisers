package com.appraisers.storage.local;

import java.util.Optional;

public interface LocalStorageService {
    Optional<byte[]> get(Long id);
}
