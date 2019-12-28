package com.appraisers.app.resources;

import com.appraisers.app.FileService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
public class FileResource {
    private FileService fileService;

    @GetMapping(value = "/image/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    ResponseEntity<byte[]> getImage(@PathVariable Long id) throws IOException {
        Optional<byte[]> file = fileService.get(id);
        if(file.isPresent()) {
            return ResponseEntity.ok().body(file.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
