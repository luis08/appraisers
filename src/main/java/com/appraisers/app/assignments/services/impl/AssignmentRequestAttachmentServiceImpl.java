package com.appraisers.app.assignments.services.impl;

import com.appraisers.app.assignments.data.AssignmentRequestAttachmentRepository;
import com.appraisers.app.assignments.domain.AssignmentRequest;
import com.appraisers.app.assignments.domain.AssignmentRequestAttachment;
import com.appraisers.app.assignments.dto.AssignmentRequestAttachmentSave;
import com.appraisers.app.assignments.services.AssignmentRequestAttachmentService;
import com.appraisers.app.gbuckets.GDrive;
import com.appraisers.app.gbuckets.GDriveCommonResponse;
import com.appraisers.app.gmail.GmailService;
import com.appraisers.resources.dto.DocumentResponseData;
import com.appraisers.storage.StorageService;
import com.appraisers.storage.StoredItemDto;
import com.appraisers.storage.StoringItemDto;
import com.appraisers.storage.local.StorageType;
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

    @Autowired
    private GDrive gDrive;

    @Autowired
    private GmailService gmailService;

    @Override
    public List<AssignmentRequestAttachmentSave> create(AssignmentRequest assignmentRequest, List<MultipartFile> multipartFiles) throws Exception {
        checkNotNull(assignmentRequest);
        checkNotNull(multipartFiles);
        List<String> fileNames = multipartFiles.stream().map(MultipartFile::getOriginalFilename).collect(Collectors.toList());
        List<String> existingFileNames = repository.findAllByAssignmentRequest(assignmentRequest).stream().map(AssignmentRequestAttachment::getOriginalFileName).collect(Collectors.toList());
        List<String> repeatedFileNames = fileNames.stream().filter(existingFileNames::contains).collect(Collectors.toList());
        if (repeatedFileNames.isEmpty()) {
            List<AssignmentRequestAttachmentSave> assignmentRequestAttachmentSaves = addAttachments(assignmentRequest, multipartFiles);
            return assignmentRequestAttachmentSaves;
        } else {
            String errorFileNames = repeatedFileNames.stream().map(f -> String.format("'%s'", f)).collect(Collectors.joining(", "));
            String errorMessage = String.format("There are repeated file names: [%s]", errorFileNames);
            throw new IllegalStateException(errorMessage);
        }
    }

    private List<AssignmentRequestAttachmentSave> addAttachments(AssignmentRequest assignmentRequest, List<MultipartFile> multipartFiles) throws IOException {
        List<AssignmentRequestAttachmentSave> assignmentRequestAttachments = new ArrayList<>();
        List<String> emailMessage = new ArrayList<>();
        boolean notifiedFolderCreation = false;
        for (MultipartFile mpf : multipartFiles) {
            AssignmentRequestAttachment assignmentRequestAttachment = getAssignmentRequestAttachment(assignmentRequest, mpf);

            String identifier = assignmentRequestAttachment.getAssignmentRequest().getIdentifier();
            GDriveCommonResponse uploadFileToGoogleDrive = gDrive.uploadFile(mpf, identifier);

            if (uploadFileToGoogleDrive != null) {
                if (!notifiedFolderCreation) {
                    notifiedFolderCreation = true;
                    emailMessage.add(getFolderCreatedMessage(uploadFileToGoogleDrive.getFolderId()));
                    assignmentRequestAttachment.setStorageId(uploadFileToGoogleDrive.getId());
                    assignmentRequestAttachments.add(new AssignmentRequestAttachmentSave(assignmentRequestAttachment, getFileUploadMessage(assignmentRequestAttachment)));
                } else {
                    emailMessage.add(getFailedUploadMessage(assignmentRequestAttachment));
                }
            } else {
                assignmentRequestAttachments.add(new AssignmentRequestAttachmentSave(null, getGoogleDriveNotAvailable(assignmentRequestAttachment, identifier)));
            }
        }
        return assignmentRequestAttachments;
    }

    private String getGoogleDriveNotAvailable(AssignmentRequestAttachment assignmentRequestAttachment, String identifier) {
        return String.format("Google Drive not available for [%s] on Assignment Request [%s].  The file was not uploaded.  Please follow up with the request.",
                assignmentRequestAttachment.getOriginalFileName(), identifier);
    }

    private String getFileUploadMessage(AssignmentRequestAttachment assignmentRequestAttachment) {
        return String.format("Uploaded file [%s]", assignmentRequestAttachment.getOriginalFileName());
    }

    private String getFailedUploadMessage(AssignmentRequestAttachment assignmentRequestAttachment) {
        return String.format("Failed uploading file [%s]", assignmentRequestAttachment.getOriginalFileName());
    }

    private String getFolderCreatedMessage(String folderName) {
        return String.format("New folder created: https://drive.google.com/drive/folders/%s", folderName);
    }

    private AssignmentRequestAttachment getAssignmentRequestAttachment(AssignmentRequest assignmentRequest, MultipartFile mpf) {
        String errorMessage = String.format("Error saving file '%s'", mpf.getOriginalFilename());
        try {
            AssignmentRequestAttachment attachment = getAttachment(assignmentRequest, mpf);
            StoringItemDto storingItemDto = new StoringItemDto(mpf.getInputStream(), attachment.getAssignmentRequest().getIdentifier(), StorageType.ASSIGNMENT_REQUEST, attachment.getOriginalFileName(), mpf.getContentType());
            Optional<StoredItemDto> storedItemDto = storageService.create(storingItemDto);
            attachment.setSanitizedFileName(storedItemDto.get().getSanitizedFileName());
            attachment.setPath(storedItemDto.get().getSanitizedFileName());
            attachment.setStorageId(storedItemDto.get().getStorageId());
            attachment = repository.save(attachment);
            return attachment;
        } catch (IOException | NotSupportedException e) {
            LOGGER.error(errorMessage, e);
            return null;
        }
    }

    private StoredItemDto store(String identifier, AssignmentRequestAttachment assignmentRequestAttachment, MultipartFile mpf) {
        String errorMessage = String.format("Error saving file '%s'", mpf.getOriginalFilename());
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