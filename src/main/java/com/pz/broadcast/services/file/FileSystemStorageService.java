package com.pz.broadcast.services.file;

import com.pz.broadcast.dtos.FileData;
import com.pz.broadcast.services.FileService;
import com.pz.broadcast.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    private final FileService fileService;

    @Autowired
    public FileSystemStorageService(FileService fileService) {
        this.rootLocation = Paths.get("upload-dir");
        this.fileService = fileService;
    }

    @Override
    public FileData store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            String hashedFileName = FileUtils.hashFileName(file.getOriginalFilename());
            Files.copy(file.getInputStream(), this.rootLocation.resolve(hashedFileName));

            FileData fileData = new FileData();
            fileData.setFileHash(hashedFileName);
            fileData.setName(file.getOriginalFilename());
            fileData.setGroups(Collections.emptyList());
            fileData.setLink("");

            fileService.saveFile(fileData);

            return fileData;
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (FileAlreadyExistsException e) {
            System.out.println("Directory already exists.");
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}