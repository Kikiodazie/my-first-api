package com.odazie.userdataapi.web;

import com.odazie.userdataapi.business.service.UserService;
import com.odazie.userdataapi.data.entity.User;
import com.odazie.userdataapi.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return (List<User>) this.userService.getUserRepository().findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUsersById(@PathVariable(value = "id") Long userId ) throws ResourceNotFoundException {
        User user = this.userService.getUserRepository()
                .findById(userId)
                .orElseThrow(() -> new  ResourceNotFoundException("User not found on :: " + userId));

        return ResponseEntity.ok().body(user);
    }


    @PostMapping("/users")
    public User createUser(@RequestBody User user){
        this.userService.userCreationDetails(user);
        return this.userService.getUserRepository().save(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId, @RequestBody User userDetails) throws ResourceNotFoundException{
        User user = this.userService
                .getUserRepository()
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));

        this.userService.updateUser(userDetails,user);

        final User updatedUser = this.userService.getUserRepository().save(user);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws Exception{
        User user = this.userService.getUserRepository()
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));

        this.userService.getUserRepository().delete(user);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }










}
