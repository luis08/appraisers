package com.appraisers.storage.local;

import com.appraisers.app.assignments.domain.AssignmentRequestAttachment;
import com.appraisers.app.assignments.services.AssignmentRequestAttachmentService;
import com.appraisers.storage.PlatformStorageStrategy;
import com.appraisers.storage.StoredItemDto;
import com.appraisers.storage.dto.PlatformStoringItemDto;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

@Service
public class WindowsStorageService implements PlatformStorageStrategy {
    private static final String WINDOWS_OS = "win";

    @Autowired
    private AssignmentRequestAttachmentService attachmentService;

    public boolean supportsOS() {
        return System.getProperty("os.name").toLowerCase().contains(WINDOWS_OS);
    }

    private Optional<byte[]> get(Long id) {
        try {
            String path = attachmentService.get(id).getPath();
            return Optional.of(FileUtils.readFileToByteArray(new File(path)));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean isSupported() {
        return false;
    }

    @Override
    public StoredItemDto create(PlatformStoringItemDto dto) throws IOException {
        return null;
    }

    @Override
    public StoredItemDto get(String id) {
        checkState(Strings.isNotEmpty(id), "The file id cannot be null or empty");
        try {
            Long longId = Long.valueOf(id);
            AssignmentRequestAttachment attachment = attachmentService.get(longId);
            String path = attachment.getPath();
            FileInputStream inputStream = FileUtils.openInputStream(new File(path));
            String fileName = attachment.getSanitizedFileName();
            StoredItemDto storedItemDto = new StoredItemDto(inputStream, fileName,fileName);
            return storedItemDto;
        } catch (IOException e) {
            return null;
        }
    }
}
