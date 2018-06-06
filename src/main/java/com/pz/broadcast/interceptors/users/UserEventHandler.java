package com.pz.broadcast.interceptors.users;

import com.pz.broadcast.entities.User;
import com.pz.broadcast.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.Optional;

@Component
@RepositoryEventHandler(User.class)
public class UserEventHandler {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    @Autowired
    public UserEventHandler(UserRepository userRepository, EntityManager entityManager) {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }


    @HandleBeforeCreate
    public void handleUserCreate(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    @HandleBeforeSave
    public void handleUserUpdate(User user) {
        if (user.getPassword() == null || user.getPassword().equals("")) {
            entityManager.detach(user);
            final Optional<User> storedUser = userRepository.findById(user.getId());

            storedUser.ifPresent(stored -> user.setPassword(stored.getPassword()));
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
    }
}
