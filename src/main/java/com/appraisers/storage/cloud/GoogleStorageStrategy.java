package com.appraisers.storage.cloud.impl;

import com.appraisers.storage.*;
import com.appraisers.storage.dto.PlatformStoringItemDto;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class GoogleStorageStrategy implements PlatformStorageStrategy {
    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private final static Logger LOGGER =
            LoggerFactory.getLogger(GoogleStorageStrategy.class);
    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "credentials.json";
    public static final String EMAIL_ADDRESS = "lsanchez08@gmail.com";

    @Nullable
    private List<File> getFiles() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        //Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, )
        try {
            Drive service = getDriveService();
            FileList result = service.files().list()
                    .setPageSize(10)
                    .setFields("nextPageToken, files(id, name)")
                    .execute();
            List<File> files = result.getFiles();
            if (files == null || files.isEmpty()) {
                System.out.println("No files found.");
            } else {
                System.out.println("Files:");
            }
            return files;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public StoredItemDto create(PlatformStoringItemDto dto) throws IOException {
        try {
            File fileMetadata = new File();
            fileMetadata.setName(dto.getSanitizedFileName());
            //String fullPath = dto.getPath().concat("/").concat(dto.getSanitizedFileName());
            String fullPath = getFullPath(dto);
            java.io.File filePath = new java.io.File(fullPath);
            FileContent mediaContent = new FileContent(dto.getMimeType(), filePath);
            File file = getDriveService().files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();
            StoredItemDto storedItemDto = new StoredItemDto(null, dto.getSanitizedFileName(), file.getId());
            return storedItemDto;
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getFullPath(PlatformStoringItemDto dto) {
        return Arrays.asList(dto.getPath()).stream().collect(Collectors.joining("/"));
        ///return Paths.get("", dto.getPath()).toAbsolutePath().toString();
    }


    private Drive getDriveService() throws Exception {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }


    /**
     * Creates an authorized Credential object.
     *
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws Exception {
        // Load client secrets.
        String  homePath = Paths.get(".").normalize().toAbsolutePath().toString();
        String path = Paths.get(homePath, CREDENTIALS_FILE_PATH).toAbsolutePath().toString();
        LOGGER.debug(path);
        //InputStream in =  GoogleStorageStrategy.class.getResourceAsStream(path);
        InputStream in = new FileInputStream(path);;
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }


    @Override
    public boolean isSupported() {
        return false;
    }


    @Override
    public StoredItemDto get(String id) {
        return null;
    }
}
