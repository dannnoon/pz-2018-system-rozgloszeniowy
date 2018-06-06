package com.pz.broadcast.services;

import com.pz.broadcast.entities.Log;
import com.pz.broadcast.repositories.FileRepository;
import com.pz.broadcast.repositories.LogRepository;
import com.pz.broadcast.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LogService {
    private final LogRepository logRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final String ACTION_DOWNLOAD = "download";
    private final String COMMENT_DOWNLOAD = "User downloaded file";

    @Autowired
    LogService(LogRepository logRepository, FileRepository fileRepository, UserRepository userRepository){
        this.logRepository = logRepository;
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
    }

    public boolean downloaded(long id){
        Log log = new Log(this.ACTION_DOWNLOAD, new Date(), this.COMMENT_DOWNLOAD);
        try {
            log.setUser(userRepository.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
            log.setFile(fileRepository.findById(id));
            logRepository.save(log);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
