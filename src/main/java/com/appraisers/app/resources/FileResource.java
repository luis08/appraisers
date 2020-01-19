package com.appraisers.app.resources;

import com.appraisers.app.assignments.services.AssignmentRequestAttachmentService;
import com.appraisers.resources.dto.DocumentResponseData;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RestController
public class FileResource {

    @Autowired
    private AssignmentRequestAttachmentService attachmentService;

    @GetMapping(value = "/assignment-request-rb/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    byte[] getImage(@PathVariable Long id) throws IOException {
        Optional<DocumentResponseData> responseData = attachmentService.getResponseData(id);
        if (responseData.isPresent()) {
            return IOUtils.toByteArray(responseData.get().getInputStream());
        }
        return null;
    }

    @GetMapping(value = "/assignment-request-/{id}")
    public ResponseEntity getImageAsResponseEntity(HttpServletResponse response, @PathVariable Long id) throws IOException {
        Optional<DocumentResponseData> responseData = attachmentService.getResponseData(id);
        if (responseData.isPresent()) {
            byte[] bytes = IOUtils.toByteArray(responseData.get().getInputStream());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(responseData.get().getMediaType());
            return ResponseEntity.accepted().headers(headers).body(bytes);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
