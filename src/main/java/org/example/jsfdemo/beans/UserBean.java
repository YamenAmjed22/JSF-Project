package org.example.jsfdemo.beans;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import org.example.jsfdemo.entities.User;
import org.example.jsfdemo.services.UserService;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import java.io.IOException;
import java.io.Serializable;

import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class UserBean implements Serializable {
    @EJB
    private UserService userService;
    private String username;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String age;
    private User user;
    private User loggedInUser;

    @PostConstruct
    public void init() {
        user = new User();
    }

    public void login() {
        User logingInUser = userService.findUserByUsername(username);

        if (logingInUser == null || !logingInUser.getPassword().equals(password)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid username or password.", null));
            return;
        }

        loggedInUser = logingInUser;
        exit("../views/Dashbord.xhtml");
    }
    public void logout() {
        loggedInUser = null;
        exit("../views/MainPage.xhtml");
    }

    // some methods

//    public void saveSingingUser(){
//        if (!ObjectUtil.isEmpty(user)) {
//            userService.addUser(user);
//        }
//    }



    public void saveSingingUser() {
        if (user == null || user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid user data.", null));
            return;
        }

        if (userService.findUserByUsername(user.getUsername()) != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "This username is already taken. Please choose another.", null));
            return;
        }

        if (user.getBirthdate() != null) {
            int age = (int) ChronoUnit.YEARS.between(user.getBirthdate(), LocalDate.now());
            user.setAge(age);
        }

        userService.addUser(user);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "User registered successfully!", null));

        user = new User(); // Reset form
        exit("../views/MainPage.xhtml");
    }


    public void goToSignIn() {
        exit("../views/SignIn.xhtml");
    }

    public void exit(String url) {
        FacesContext fc = FacesContext.getCurrentInstance();
        try {
            fc.getExternalContext().redirect(url);
        } catch (IOException ex) {
            Logger.getLogger(null).log(Level.SEVERE, null, ex);
        }
    }

    // getter and setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
