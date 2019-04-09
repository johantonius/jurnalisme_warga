package com.project.jw.controller;

import com.project.jw.model.Role;
import com.project.jw.model.User;
import com.project.jw.repository.UserRepository;
import com.project.jw.services.MapValidationErrorService;
import com.project.jw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @GetMapping
    public Iterable <User> findAll(){
        return repository.findAll();
    }

    @PostMapping("/registerMedia")
    public ResponseEntity<?> registerMedia(@Valid @RequestBody User user, BindingResult result){
//                userValidator.validate(user, result);
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (result.hasErrors()) return null;

        user.setStatus(false);
        user.setRole(Role.Media);
        User newUser = userService.saveUser(user);
        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);

    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult result){
//                userValidator.validate(user, result);
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (result.hasErrors()) return null;

        user.setStatus(false);
        user.setRole(Role.User);
        User newUser = userService.saveUser(user);
        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);

    }

    @PutMapping("/update/{user_id}")
    public User updateUser(@PathVariable(value = "user_id") Long user_id,
                              @Valid @RequestBody User user) {

        User u = repository.findById(user_id)
                .orElseThrow(() -> new ResourceNotFoundException("User"+ "user_id"+ user_id));

        u.setAbout(user.getAbout());
        u.setPhone(user.getPhone());

        User updatedUser = repository.save(u);
        return updatedUser;
    }
}
