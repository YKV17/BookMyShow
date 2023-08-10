package com.ykv17.bookmyshow.service;

import com.ykv17.bookmyshow.models.User;
import com.ykv17.bookmyshow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signUpUser(String email, String password, String name, String phoneNo, int age){
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPhoneNumber(phoneNo);
        user.setAge(age);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        return userRepository.save(user);
    }
}
