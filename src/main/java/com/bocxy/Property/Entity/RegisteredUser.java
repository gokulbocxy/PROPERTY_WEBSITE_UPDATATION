package com.bocxy.Property.Entity;



import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "registeredusers")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    public String firstname;
    public String lastname;
    private String password;
    public String phonenumber;
    public String email;
    public String otp;
    private String loggedin;
    private String username;

    public RegisteredUser(String username ,String email,String password,String firstname, String lastname,String phonenumber) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;


    }
}