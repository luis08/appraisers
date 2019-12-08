package com.appraisers.app.assignments.services;

import com.appraisers.app.assignments.data.AssignmentRequestAttachmentRepository;
import com.appraisers.app.assignments.domain.AssignmentRequest;
import com.appraisers.app.assignments.domain.AssignmentRequestAttachment;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class AssignmentRequestAttachmentServiceImpl implements AssignmentRequestAttachmentService {
    private final static Logger LOGGER =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final String USER_DIR = "user.dir";


    @Autowired
    private AssignmentRequestAttachmentRepository repository;

    @Override
    public List<AssignmentRequestAttachment> create(AssignmentRequest assignmentRequest, List<MultipartFile> multipartFiles) {
        checkNotNull(assignmentRequest);
        checkNotNull(multipartFiles);
        List<AssignmentRequestAttachment> assignmentRequestAttachments = multipartFiles.stream()
                .map(mpf -> createAttachment(mpf, assignmentRequest))
                .collect(Collectors.toList());
        List<AssignmentRequestAttachment> saved = repository.saveAll(assignmentRequestAttachments);
        repository.flush();;
        return saved;
    }

    @Override
    public AssignmentRequestAttachment get(Long id) {
        checkNotNull(id);
        return repository.getOne(id);
    }

    private AssignmentRequestAttachment createAttachment(MultipartFile mpf, AssignmentRequest assignmentRequest) {
        String storedPath = getStoragePath(mpf.getOriginalFilename());
        AssignmentRequestAttachment assignmentRequestAttachment = new AssignmentRequestAttachment();
        assignmentRequestAttachment.setAssignmentRequest(assignmentRequest);
        assignmentRequestAttachment.setPath(storedPath);
        assignmentRequestAttachment.setOriginalFileName(mpf.getOriginalFilename());
        try {
            Files.copy(mpf.getInputStream(), Paths.get(storedPath));
        } catch (IOException e) {
            LOGGER.log(Level.ALL,"Error saving file: " + storedPath);
            LOGGER.log(Level.ALL,e.getMessage());
            return null;
        }

        return assignmentRequestAttachment;
    }

    private String getStoragePath(String fullPath) {
        FilenameUtils.getFullPath(fullPath);
        UUID uuid = UUID.randomUUID();
        String extension = FilenameUtils.getExtension(fullPath);
        String workingDirectory = System.getProperty(USER_DIR);
        String storagePath = FilenameUtils.normalize(workingDirectory.concat(File.separator).concat(uuid.toString()).concat(".").concat(extension));
        LOGGER.log(Level.ALL, "Storing: ".concat(storagePath));
        return storagePath;
    }
}
