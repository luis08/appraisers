package com.appraisers.app.gbuckets;


import com.appraisers.app.assignments.dto.GoogleUploadItemDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.Nullable;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

@Service
public class GDrive {

    public static final String FOLDER_MIMETYPE = "application/vnd.google-apps.folder";

    private RestTemplate restTemplate;

    public GDrive(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        this.restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    public String getAccessToken() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
            map.add("refresh_token", GDrivePropertyUtil.gdriveApiRefreshToken);
            map.add("client_id", GDrivePropertyUtil.gdriveApiClientId);
            map.add("client_secret", GDrivePropertyUtil.gdriveClientSecret);
            map.add("grant_type", "refresh_token");
            map.add("redirect_uri", "https://google.com");

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

            ResponseEntity<AccessTokenRequest> response = this.restTemplate.postForEntity(GDrivePropertyUtil.gdriveTokenUrl, request, AccessTokenRequest.class);

            AccessTokenRequest parsedResponse = response.getBody();

            if (parsedResponse.getError() != null && parsedResponse.getError().equals("invalid_grant")) {
                return null;
            } else {
                return parsedResponse.getAccessToken();
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public GDriveCommonResponse createFolder(String folderName) throws JsonProcessingException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(this.getAccessToken());

            headers.setContentType(MediaType.APPLICATION_JSON);

            ObjectMapper jsonMapper = new ObjectMapper();
            HashMap<String, Object> data = new HashMap<String, Object>();
            data.put("mimeType", FOLDER_MIMETYPE);
            data.put("name", folderName);

            HttpEntity<String> entity = new HttpEntity<String>(jsonMapper.writeValueAsString(data), headers);

            ResponseEntity<GDriveCommonResponse> response = this.restTemplate.postForEntity("https://www.googleapis.com/drive/v3/files", entity, GDriveCommonResponse.class);

            return response.getBody();
        } catch (Exception ex) {
            return null;
        }
    }

    public GDriveCommonResponse[] listFiles() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(this.getAccessToken());

            HttpEntity<String> entity = new HttpEntity<String>(null, headers);

            ResponseEntity<GDriveListFilesResponse> response = this.restTemplate.exchange("https://www.googleapis.com/drive/v3/files", HttpMethod.GET, entity, GDriveListFilesResponse.class);

            return response.getBody().getFiles();
        } catch (Exception ex) {
            return null;
        }
    }

    public GDriveCommonResponse doesFolderExist(String folderName) {
        GDriveCommonResponse[] driveEntities = this.listFiles();
        if (driveEntities != null) {
            boolean exists = Arrays.stream(driveEntities).anyMatch(entity -> entity.getName().equals(folderName) && entity.getMimeType().equals(FOLDER_MIMETYPE));
            if (exists) {
                return Arrays.stream(driveEntities).filter(entity -> entity.getName().equals(folderName) && entity.getMimeType().equals(FOLDER_MIMETYPE)).findFirst().get();
            }
        }
        return null;
    }

    public GDriveCommonResponse uploadFile(MultipartFile multipartFile, String folderName) throws IOException {
        try {
            String contentType = multipartFile.getContentType();
            String originalFilename = multipartFile.getOriginalFilename();
            byte[] byteArray = multipartFile.getBytes();
            String fileName = multipartFile.getName();
            GoogleUploadItemDto googleUploadItemDto = new GoogleUploadItemDto(contentType, originalFilename, fileName, folderName, byteArray);
            return uploadFile(googleUploadItemDto);
        } catch (Exception ex) {
            return null;
        }
    }

    @Nullable
    public GDriveCommonResponse uploadFile(GoogleUploadItemDto googleUploadItemDto) throws JsonProcessingException {
        GDriveCommonResponse folderId = doesFolderExist(googleUploadItemDto.getFolderName());

        if (folderId == null) {
            folderId = createFolder(googleUploadItemDto.getFolderName());
        }

        if (folderId != null) {

            ObjectMapper mapper = new ObjectMapper();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(this.getAccessToken());
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.setAccept(Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON_UTF8, MediaType.APPLICATION_OCTET_STREAM}));

            GDriveMetadataRequest metadata = new GDriveMetadataRequest();

            metadata.setMimeType(googleUploadItemDto.getContentType());
            metadata.setName(googleUploadItemDto.getOriginalFilename());
            metadata.setParents(new String[]{folderId.getId()});

            MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

            HttpHeaders metadataHeaders = new HttpHeaders();
            metadataHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> metadataEntity = new HttpEntity<>(mapper.writeValueAsString(metadata), metadataHeaders);

            map.set("metadata", metadataEntity);

            ByteArrayResource contentsAsResource = new ByteArrayResource(googleUploadItemDto.getByteArray()) {
                @Override
                public String getFilename() {
                    return googleUploadItemDto.getFileName();
                }
            };
            map.set("file", contentsAsResource);

            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(map, headers);

            ResponseEntity<GDriveCommonResponse> response = this.restTemplate.exchange("https://www.googleapis.com/upload/drive/v3/files?uploadType=multipart", HttpMethod.POST, entity, GDriveCommonResponse.class);

            GDriveCommonResponse body = response.getBody();
            body.setFolderId(folderId.getId());
            return body;
        }
        return null;
    }

}
