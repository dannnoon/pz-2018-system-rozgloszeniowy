package com.pz.broadcast.controllers;

import com.pz.broadcast.dtos.FileData;
import com.pz.broadcast.services.file.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping(value = "/file")
public class FileController {

    private final StorageService storageService;

    @Autowired
    public FileController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping(value = "/upload", produces = "application/json")
    public FileData handleFileUpload(@RequestPart("file") MultipartFile file) {
        return storageService.store(file);
    }
}
