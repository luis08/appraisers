package com.appraisers.app;

import org.springframework.stereotype.Service;

import java.util.Optional;


public interface FileService {
    Optional<byte[]> get(Long id);
}
