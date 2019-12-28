package com.appraisers.app.file.service;

import com.appraisers.app.FileService;
import com.appraisers.storage.cloud.CloudStorageService;
import com.appraisers.storage.local.LocalStorageService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {
    private LocalStorageService localStorageService;
    private CloudStorageService cloudStorageService;

    @Override
    public Optional<byte[]> get(Long id) {
        Optional<byte[]> cloudFile = cloudStorageService.get(id);
        if(cloudFile.isPresent()) {
            return cloudFile;
        } else  {
            return localStorageService.get(id);
        }
    }
}
