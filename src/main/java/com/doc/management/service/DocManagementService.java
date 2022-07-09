package com.doc.management.service;

import com.doc.management.constants.DocConstant;
import com.doc.management.controller.DocManagementController;
import com.doc.management.exception.ValidationException;
import com.doc.management.model.FileInfo;
import com.doc.management.utils.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
public class DocManagementService {

    private final Path uploadPath = Paths.get(DocConstant.UPLOAD_FILE_DIRECTORY);

    @Value("#{'${supported.file.extension}'.split(',')}")
    private List<String> fileExtensions;

    public void save(MultipartFile file) throws IOException {
        try {
            String fileName = file.getOriginalFilename();
            String fileExtension = fileName.substring(
                    fileName.indexOf(".") != -1 ? fileName.indexOf(".") + 1 : 0, fileName.length());
            if (!fileExtensions.contains(fileExtension)) {
                throw new ValidationException(HttpStatus.BAD_REQUEST.value(), "Supported file formats are :: "+fileExtensions.toString());
            }
            Files.copy(file.getInputStream(), uploadPath.resolve(file.getOriginalFilename()));
            saveMetadata( FileInfo.builder().fileName(fileName)
                    .createdBy("Admin").createdOn(Util.getCurrentDate()).fileType("Annual Report").build());
        } catch (ValidationException ve) {
            throw ve;
        } catch (IOException e) {
            throw e;
        }
    }

    public void saveMetadata(FileInfo fileInfo) {
        try {
            List<FileInfo> allFiles = new ArrayList<>();
            List<FileInfo> availableFiles = getAllFiles();
            if (CollectionUtils.isEmpty(availableFiles)) {
                availableFiles = new ArrayList<>();
            }
            allFiles.addAll(availableFiles);
            fileInfo.setFileUrl(MvcUriComponentsBuilder
                    .fromMethodName(DocManagementController.class,
                            "getFile", "/" + fileInfo.getFileName()).build().toString());
            allFiles.add(fileInfo);
            JSONArray jsonArray = new JSONArray();
            allFiles.stream().forEach(fileInfo1 -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("fileName", fileInfo1.getFileName());
                jsonObject.put("fileType", fileInfo1.getFileType());
                jsonObject.put("fileUrl", fileInfo1.getFileUrl());
                jsonObject.put("createdBy", fileInfo1.getCreatedBy());
                jsonObject.put("createdOn", fileInfo1.getCreatedOn());
                jsonArray.add(jsonObject);
            });
            try (FileWriter fileWriter = new FileWriter("src/main/resources/data.json")) {
                fileWriter.write(jsonArray.toJSONString());
                fileWriter.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<FileInfo> getAllFiles() throws Exception {
        File file = new File("src/main/resources/data.json");
        if (!file.exists() || file.length() == 0L) {
            return Collections.emptyList();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        FileInfo[] fileInfos = objectMapper.readValue(file, FileInfo[].class);
        return fileInfos != null ? Arrays.asList(fileInfos) : Collections.emptyList();
    }

    public Resource load(String filename) {
        try {
            Path file = uploadPath.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    /*public Stream<Path> loadAll() {
        try {
            return Files.walk(this.uploadPath, 1)
                    .filter(path -> !path.equals(this.uploadPath))
                    .map(this.uploadPath::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }*/
}
