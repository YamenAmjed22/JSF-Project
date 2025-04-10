package org.example.jsfdemo.services;

import jakarta.ejb.Local;
import org.example.jsfdemo.entities.User;

import java.util.List;

@Local
public interface UserService {
    public void addUser(User newUser);
    User findUserByUsername(String username);
    List<User> findAllUsers();
    void updateUser(User loggedInUser);
}
