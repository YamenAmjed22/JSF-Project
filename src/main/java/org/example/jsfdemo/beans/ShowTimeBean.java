package org.example.jsfdemo.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jdk.jfr.Name;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Named(value = "showTimeBean")
@SessionScoped
public class ShowTimeBean implements Serializable {

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    public String getCurrentTime() {
        return sdf.format(Calendar.getInstance().getTime());
    }

}
