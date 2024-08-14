package com.devolution.EnjoyMD.services;

import com.devolution.EnjoyMD.models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(Integer id);

    void addUser(User user);

    void deleteUser(Integer id);
}
