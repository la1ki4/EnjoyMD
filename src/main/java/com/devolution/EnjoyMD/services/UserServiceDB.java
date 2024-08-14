package com.devolution.EnjoyMD.services;

import com.devolution.EnjoyMD.models.User;
import com.devolution.EnjoyMD.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@ConditionalOnProperty(name = "database.type", havingValue = "DB")
public class UserServiceDB implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id).orElse(new User());
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        Optional<User> byId = userRepository.findById(id);
        byId.ifPresent(userRepository::delete);
    }
}
