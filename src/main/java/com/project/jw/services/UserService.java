package com.project.jw.services;

import com.project.jw.exception.UsernameAlreadyExistsException;
import com.project.jw.model.User;
import com.project.jw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser){
        try {
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

        newUser.setUsername(newUser.getUsername());
        return userRepository.save(newUser);
        }catch (Exception e){
            throw new UsernameAlreadyExistsException(newUser.getUsername()+"' already exists'");
        }
    }
}
