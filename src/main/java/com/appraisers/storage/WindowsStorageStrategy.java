package com.appraisers.storage;

import com.appraisers.storage.dto.PlatformStoringItemDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class WindowsStorageStrategy implements PlatformStorageStrategy {

    @Value("${com.appraisers.app.storage.assignment.request}")
    private String homePath;

    @Override
    public boolean isSupported() {
        return true;
        //return OperatingSystemStorageUtils.getPlatform() == Platform.WINDOWS;
    }

    @Override
    public StoredItemDto create(PlatformStoringItemDto dto) throws IOException {
    //        String containerPath = Paths.get(homePath, dto.getPath()).toAbsolutePath().toString();
    //
    //        if (!Files.isDirectory(Paths.get(containerPath))) {
    //            createDirectory(containerPath);
    //        }
    //
    //        Path finalPath = Paths.get(containerPath, dto.getSanitizedFileName());
    //        File file = new File(finalPath.toAbsolutePath().toString());
    //        Files.copy(dto.getInputStream(), file.toPath());
        return new StoredItemDto(null, dto.getSanitizedFileName(), dto.getSanitizedFileName());
    }

    @Override
    public StoredItemDto get(String id) {
        return null;
    }

    private String createDirectory(String fullPath) {
        File dirs = new File(fullPath);
        dirs.mkdirs();
        return fullPath;
    }
}
