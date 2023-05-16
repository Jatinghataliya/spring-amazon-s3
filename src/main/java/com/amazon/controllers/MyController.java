package com.amazon.controllers;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazon.services.S3Service;
import com.amazonaws.services.s3.model.S3Object;

@RestController
public class MyController {
    @Autowired
    private S3Service s3Service;

    @PostMapping("/upload")
    public void uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            s3Service.uploadFile(file.getOriginalFilename(), file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/download/{key}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String key) {
        S3Object s3Object = s3Service.downloadFile(key);
        InputStream inputStream = s3Object.getObjectContent();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + key);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType(s3Object.getObjectMetadata().getContentType()))
                .body(new InputStreamResource(inputStream));
    }

    @DeleteMapping("/delete/{key}")
    public void deleteFile(@PathVariable String key) {
        s3Service.deleteFile(key);
    }
}