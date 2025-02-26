package org.example.jsfdemo.beans;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import org.example.jsfdemo.entities.Attachment;
import org.example.jsfdemo.entities.User;
import org.example.jsfdemo.services.UserService;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class RepeatBean implements Serializable {
    @EJB
    private UserService userService;
    List<User> users;

    @PostConstruct
    public void init() {
       users = userService.findAllUsers();
    }


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
