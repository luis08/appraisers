package com.appraisers.app.gdrive;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.UUID;

public class GDriveUtil {

    public static final String GOOGLE_Q = "name='%s' and mimeType='application/vnd.google-apps.folder'";

    public static File uploadFile(MultipartFile multipartFile, String folder) throws Exception {
        try {
            File fileMetadata = new File();
            fileMetadata.setName(multipartFile.getOriginalFilename());

            FileContent mediaContent = new FileContent(multipartFile.getContentType(), streamFile(multipartFile.getInputStream()));

            File driveFolder = createFolder(folder);
            fileMetadata.setParents(Collections.singletonList(driveFolder.getId()));

            File file = GDriveMain.getGService().files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();

            return file;
        } catch (Exception ex) {
            return null;
        }
    }

    public static File createFolder(String folderName) throws Exception {
        File fileMetadata = new File();
        fileMetadata.setName(folderName);
        fileMetadata.setMimeType("application/vnd.google-apps.folder");

        FileList possibleFolders = GDriveMain
                .getGService()
                .files()
                .list()
                .setQ(String.format(GOOGLE_Q, folderName))
                .execute();

        if (possibleFolders.getFiles().size() > 0) {
            return possibleFolders.getFiles().get(0);
        } else {
            File file = GDriveMain.getGService().files().create(fileMetadata)
                    .setFields("id")
                    .execute();

            return file;
        }
    }

    public static java.io.File streamFile (InputStream in) throws IOException {
        final java.io.File tempFile = java.io.File.createTempFile(UUID.randomUUID().toString(), ".tmp");
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }
        return tempFile;
    }

}
