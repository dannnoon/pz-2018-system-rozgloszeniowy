package com.pz.broadcast.repositories;

import com.pz.broadcast.entities.File;
import com.pz.broadcast.entities.UserGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface FileRepository extends CrudRepository<File, Long> {
    Collection<File> findAllByGroupsContains(Collection<UserGroup> group);
    File findById(long id);
    File findByIdAndGroupsContains(long id, Collection<UserGroup> groups);
}
