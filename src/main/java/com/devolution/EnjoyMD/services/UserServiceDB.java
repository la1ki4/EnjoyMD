package com.devolution.EnjoyMD.services;

import com.devolution.EnjoyMD.models.User;
import com.devolution.EnjoyMD.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserServiceDB implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @Override
    public boolean registerUser(User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            return false;
        }

        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            return false;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
}
