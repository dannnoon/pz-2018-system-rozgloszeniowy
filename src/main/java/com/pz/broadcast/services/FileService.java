package com.pz.broadcast.services;

import com.pz.broadcast.dtos.FileData;
import com.pz.broadcast.entities.File;
import com.pz.broadcast.entities.Log;
import com.pz.broadcast.entities.User;
import com.pz.broadcast.entities.UserGroup;
import com.pz.broadcast.repositories.FileRepository;
import com.pz.broadcast.repositories.LogRepository;
import com.pz.broadcast.repositories.UserGroupRepository;
import com.pz.broadcast.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {

    private final FileRepository fileRepository;
    private final LogRepository logRepository;
    private final UserGroupRepository userGroupRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FileService(FileRepository fileRepository, UserGroupRepository userGroupRepository, UserRepository userRepository, ModelMapper modelMapper, LogRepository logRepository) {
        this.fileRepository = fileRepository;
        this.userGroupRepository = userGroupRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.logRepository = logRepository;
    }


    public List<FileData> getAllUserFiles() {
        final Collection<UserGroup> userGroups = getUserGroups();

        if (userGroups == null)
            return null;

        final Collection<File> fileCollection = fileRepository.findAllByGroupsContains(userGroups);

        if (fileCollection == null || fileCollection.size() < 1)
            return null;

        return fileCollection.stream().map(item -> modelMapper.map(item, FileData.class)).collect(Collectors.toList());
    }

    public FileData getOneFile(long id) {
        final File file = fileRepository.findById(id);

        if (file == null)
            return null;

        return modelMapper.map(file, FileData.class);
    }

    public String downloadFile(long id){
        try {
            final Collection<UserGroup> userGroups = getUserGroups();

            File file = fileRepository.findByIdAndGroupsContains(id,userGroups);
            if (file == null)
                return null;
            return file.getLink();
        } catch (Exception e) {
            return null;
        }
    }

    public List<FileData> getNewFiles(){
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final String name = auth.getName();
        final User user = userRepository.findUserByEmail(name);
        List<FileData> fileDataList = getAllUserFiles();
        if (fileDataList == null)
            return null;
        List<FileData> response = new ArrayList<>();
        for (FileData item : fileDataList) {
            Collection<Log> log = logRepository.findAllByFileAndUser(modelMapper.map(item, File.class), user);
            if (log == null || log.isEmpty())
                response.add(item);
        }
        return response;
    }

    private Collection<UserGroup> getUserGroups(){
        try {
            final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            final String name = auth.getName();
            final User user = userRepository.findUserByEmail(name);
            final Collection<UserGroup> userGroups = userGroupRepository.findAllByUsersContains(user);
            return userGroups;
        } catch (Exception e){
            return null;
        }
    }

    public FileData saveFile(FileData fileData) {
        File fileEntity = modelMapper.map(fileData, File.class);
        fileEntity = fileRepository.save(fileEntity);
        return modelMapper.map(fileEntity, FileData.class);
    }
}
