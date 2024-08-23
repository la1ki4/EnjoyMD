package com.devolution.EnjoyMD.services;

import com.devolution.EnjoyMD.models.User;
import com.devolution.EnjoyMD.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@ConditionalOnProperty(name = "database.type", havingValue = "DB")
public class UserServiceDB implements UserService{

    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @Override
    public boolean registerUser(User user) {
        if(userRepository.findByEmail(user.getEmail()) != null){
            return false;
        }

        if(userRepository.findByUsername(user.getUsername()) != null){
            return false;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
}
