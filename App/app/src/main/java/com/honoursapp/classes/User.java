package com.honoursapp.classes;

public class User {

    String name;
    String email;
    String pass;

    //Default Constructor
    public User(){

    }
    
    //Getters and setters 
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}