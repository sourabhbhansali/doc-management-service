package com.doc.management.controller;

import com.doc.management.exception.ValidationException;
import com.doc.management.model.FileInfo;
import com.doc.management.response.GenericResponse;
import com.doc.management.service.DocManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/files")
@CrossOrigin
public class DocManagementController {

    @Autowired
    DocManagementService docManagementService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GenericResponse> uploadFile(
            @RequestParam("file") MultipartFile file) throws IOException {
        String message = "";
        try {
            docManagementService.save(file);
            message = "File Uploaded Successfully !!! " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(GenericResponse.builder().message(message).build());
        } catch (ValidationException e) {
            throw new ValidationException(e.getErrorCode(), e.getErrorMessage());
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<List<FileInfo>> getAllFiles() {
        List<FileInfo> fileInfos = null;
        try {
            fileInfos = docManagementService.getAllFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.OK).body(fileInfos);
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = docManagementService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}