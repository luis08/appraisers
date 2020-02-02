package com.appraisers.app.gdrive;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.File;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class GDriveUtil {

    public static File uploadFile(MultipartFile multipartFile) throws Exception {
        try {
            File fileMetadata = new File();
            fileMetadata.setName(multipartFile.getOriginalFilename());

            FileContent mediaContent = new FileContent(multipartFile.getContentType(), streamFile(multipartFile.getInputStream()));
            File file = GDriveMain.getGService().files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();

            return file;
        } catch (Exception ex) {
            return null;
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
