package com.bocxy.Property.Model;


import lombok.Data;

@Data
public class SignupRequest {

    private String  firstname;
    private String  lastname;
    private String email;
    public String  phonenumber;
    private String password;
    private String username;

    public SignupRequest(String firstname, String lastname, String email, String phonenumber, String password, String username) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phonenumber = phonenumber;
        this.password = password;
        this.username = username;
    }

}

