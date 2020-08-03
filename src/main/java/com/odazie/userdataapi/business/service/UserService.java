package com.odazie.userdataapi.business.service;

import com.odazie.userdataapi.business.model.UserDetails;
import com.odazie.userdataapi.data.entity.User;
import com.odazie.userdataapi.data.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void userCreationDetails(User user){
        user.setCreatedAt(LocalDate.now());
        user.setUpdatedAt(LocalDate.now());
        user.setCreatedBy(user.getFirstName());
        user.setUpdatedBy(user.getFirstName());
    }

    public void updateUser(User userDetails, User user){
        user.setEmailAddress(userDetails.getEmailAddress());
        user.setLastName(userDetails.getLastName());
        user.setFirstName(userDetails.getFirstName());
        user.setUpdatedAt(LocalDate.now());
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
