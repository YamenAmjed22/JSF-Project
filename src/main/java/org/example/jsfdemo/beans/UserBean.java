package org.example.jsfdemo.beans;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.servlet.http.Part;
import org.example.jsfdemo.entities.Attachment;
import org.example.jsfdemo.entities.User;
import org.example.jsfdemo.services.AttachmentService;
import org.example.jsfdemo.services.UserService;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import java.io.IOException;
import java.io.Serializable;

import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@SessionScoped
public class UserBean implements Serializable {

    private Attachment attachment;
    @EJB
    private UserService userService;

    @EJB
    private AttachmentService attachmentService;

    private UploadedFile uploadedFile;
    private static final String UPLOAD_DIRECTORY = "D:/Projects/JSFDemo-main/src/main/webapp/Imgs/"; // Server folder to store images


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
        attachment = new Attachment();
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


        // Calculate age if birthdate is provided
        if (user.getBirthdate() != null) {
            int age = (int) ChronoUnit.YEARS.between(user.getBirthdate(), LocalDate.now());
            if (age < 18){
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "This age must be more than 18 ", null));
                    return;
            }
            else{
                user.setAge(age);

            }
        }

        // Save user
        // Assign attachment to user
        user.setAttach(attachment);
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

    public void upload(FileUploadEvent event) {
        this.uploadedFile = event.getFile();
        if (uploadedFile != null) {
            try {
                // Ensure directory exists
                File uploadDir = new File(UPLOAD_DIRECTORY);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // Save file with unique name
                String fileName = System.currentTimeMillis() + "_" + uploadedFile.getFileName();
                File savedFile = new File(uploadDir, fileName);

                // Save file to server
                try (InputStream input = uploadedFile.getInputStream()) {
                    Files.copy(input, savedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                // Save attachment to database
                 attachment = new Attachment();
                attachment.setFileName(fileName);
                attachment.setFullPath(savedFile.getPath());

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully!", null));

            } catch (IOException e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload failed!", null));
                e.printStackTrace();
            }
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

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }
}
