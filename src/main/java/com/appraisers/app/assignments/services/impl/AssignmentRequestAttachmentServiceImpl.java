package com.appraisers.app.assignments.services.impl;

import com.appraisers.app.assignments.data.AssignmentRequestAttachmentRepository;
import com.appraisers.app.assignments.domain.AssignmentRequest;
import com.appraisers.app.assignments.domain.AssignmentRequestAttachment;
import com.appraisers.app.assignments.services.AssignmentRequestAttachmentService;
import com.appraisers.resources.dto.DocumentResponseData;
import com.appraisers.storage.StorageService;
import com.appraisers.storage.StoredItemDto;
import com.appraisers.storage.StoringItemDto;
import com.appraisers.storage.local.StorageType;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.NotSupportedException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class AssignmentRequestAttachmentServiceImpl implements AssignmentRequestAttachmentService {
    private static final String USER_DIR = "user.dir";
    private final static Logger LOGGER =
            LoggerFactory.getLogger(AssigmentRequestServiceImpl.class);

    @Autowired
    private AssignmentRequestAttachmentRepository repository;

    @Autowired
    private StorageService storageService;

    @Override
    public List<AssignmentRequestAttachment> create(AssignmentRequest assignmentRequest, List<MultipartFile> multipartFiles) throws Exception {
        checkNotNull(assignmentRequest);
        checkNotNull(multipartFiles);
        List<String> fileNames = multipartFiles.stream().map(MultipartFile::getOriginalFilename).collect(Collectors.toList());
        List<String> existingFileNames = repository.findAllByAssignmentRequest(assignmentRequest).stream().map(AssignmentRequestAttachment::getOriginalFileName).collect(Collectors.toList());
        List<String> repeatedFileNames = fileNames.stream().filter(existingFileNames::contains).collect(Collectors.toList());
        if (repeatedFileNames.isEmpty()) {
            List<AssignmentRequestAttachment> assignmentRequestAttachments = new ArrayList<>();
            for (MultipartFile mpf : multipartFiles) {
                AssignmentRequestAttachment assignmentRequestAttachment = getAssignmentRequestAttachment(assignmentRequest, mpf);

                //File gDriveFile = GDriveUtil.uploadFile(mpf, assignmentRequestAttachment.getAssignmentRequest().getIdentifier());

                //assignmentRequestAttachment.setStorageId(gDriveFile.getId());
                assignmentRequestAttachments.add(assignmentRequestAttachment);
            }
            return repository.saveAll(assignmentRequestAttachments);
        } else {
            String errorFileNames = repeatedFileNames.stream().map(f -> String.format("'%s'", f)).collect(Collectors.joining(", "));
            String errorMessage = String.format("There are repeated file names: [%s]", errorFileNames);
            throw new IllegalStateException(errorMessage);
        }
    }

    private AssignmentRequestAttachment getAssignmentRequestAttachment(AssignmentRequest assignmentRequest, MultipartFile mpf) {
        String errorMessage = String.format("Error saving file '{}'", mpf.getOriginalFilename());
        try {
            AssignmentRequestAttachment attachment = getAttachment(assignmentRequest, mpf);
            StoringItemDto storingItemDto = new StoringItemDto(mpf.getInputStream(), attachment.getAssignmentRequest().getIdentifier(), StorageType.ASSIGNMENT_REQUEST, attachment.getOriginalFileName(), mpf.getContentType());
            Optional<StoredItemDto> storedItemDto = storageService.create(storingItemDto);
            attachment.setSanitizedFileName(storedItemDto.get().getSanitizedFileName());
            attachment.setPath(storedItemDto.get().getSanitizedFileName());
            attachment.setStorageId(storedItemDto.get().getStorageId());

            return attachment;
        } catch (IOException | NotSupportedException e) {
            LOGGER.error(errorMessage, e);
            return null;
        }
    }

    private StoredItemDto store(String identifier, AssignmentRequestAttachment assignmentRequestAttachment, MultipartFile mpf) {
        String errorMessage = String.format("Error saving file '{}'", mpf.getOriginalFilename());
        try {
            String domainId = assignmentRequestAttachment.getAssignmentRequest().getIdentifier();
            StorageType storageType = StorageType.ASSIGNMENT_REQUEST;
            String origFileName = assignmentRequestAttachment.getOriginalFileName();
            String mime = mpf.getContentType();
            StoringItemDto storingItemDto = new StoringItemDto(mpf.getInputStream(), domainId, storageType, origFileName, mime);
            Optional<StoredItemDto> storedItemDto = storageService.create(storingItemDto);

            if (storedItemDto.isPresent()) {
                return storedItemDto.get();
            } else {
                LOGGER.error(errorMessage);
                return null;
            }
        } catch (IOException | NotSupportedException e) {
            LOGGER.error(errorMessage, e);
            return null;
        }
    }

    @Override
    public AssignmentRequestAttachment get(Long id) {
        checkNotNull(id);
        return repository.getOne(id);
    }

    @Override
    public Optional<DocumentResponseData> getResponseData(Long id) {
        AssignmentRequestAttachment assignmentRequestAttachment = get(id);
        MediaType mediaType = MediaType.valueOf(assignmentRequestAttachment.getFileType());
        final String additionalErrorMessage = assignmentRequestAttachment.getPath() + " was not found for id: " + assignmentRequestAttachment.getId();
        try {
            Optional<StoredItemDto> storedItemDto = storageService.get(assignmentRequestAttachment.getStorageId());
            if (storedItemDto.isPresent()) {
                return Optional.of(new DocumentResponseData(storedItemDto.get().getInputStream(), mediaType));
            } else {
                LOGGER.error(additionalErrorMessage);
                return Optional.empty();
            }
        } catch (Exception e) {
            LOGGER.error(additionalErrorMessage, e);
            return Optional.empty();
        }
    }

    private AssignmentRequestAttachment getAttachment(AssignmentRequest assignmentRequest, MultipartFile mpf) {
        AssignmentRequestAttachment assignmentRequestAttachment = new AssignmentRequestAttachment();
        assignmentRequestAttachment.setAssignmentRequest(assignmentRequest);
        assignmentRequestAttachment.setOriginalFileName(mpf.getOriginalFilename());
        assignmentRequestAttachment.setFileType(mpf.getContentType());
        assignmentRequestAttachment.setDescription("AssignmentRequestAttachment");
        return assignmentRequestAttachment;
    }
}