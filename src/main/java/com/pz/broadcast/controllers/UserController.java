package com.pz.broadcast.controllers;


import com.pz.broadcast.dtos.FileData;
import com.pz.broadcast.services.FileService;
import com.pz.broadcast.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final FileService fileService;
    private final LogService logService;

    @Autowired
    public UserController(FileService fileService, LogService logService) {
        this.fileService = fileService;
        this.logService = logService;
    }


    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public List<FileData> getFiles() {
        try {
             return fileService.getAllUserFiles();
        } catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value = "/file/{id}", method = RequestMethod.GET)
    public FileData getFile(@PathVariable long id) {
        try {
            return fileService.getOneFile(id);
        } catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value = "/file/{id}/download", method = RequestMethod.GET)
    public String downloadFile(@PathVariable long id) {
        try {
            String url = fileService.downloadFile(id);
            boolean logSaved = false;
            if (url != null)
                logSaved = logService.downloaded(id);
            if (!logSaved)
                return null;
            return url;
        } catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value = "/notif", method = RequestMethod.GET)
    public List<FileData> getNewFiles() {
        try {
            return fileService.getNewFiles();
        } catch (Exception e) {
            return null;
        }
    }

}
