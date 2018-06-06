package com.pz.broadcast.repositories;

import com.pz.broadcast.entities.File;
import com.pz.broadcast.entities.Log;
import com.pz.broadcast.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface LogRepository extends CrudRepository<Log, Long> {
    Collection<Log> findAllByFileAndUser(File file, User user);
}
