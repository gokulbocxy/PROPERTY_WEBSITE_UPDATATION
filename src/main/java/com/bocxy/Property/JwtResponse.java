package com.bocxy.Property;

import lombok.Data;

@Data
public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private Long id;
  private String firstname;
  private String lastname;
  private String email;
  private String phonenumber;
  private String loggedin;
  private String username;

  public JwtResponse(String token, Long id, String firstname, String lastname, String email, String phonenumber,String loggedin,String username) {
    this.token = token;
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.phonenumber = phonenumber;
    this.loggedin = loggedin;
    this.username = username;
  }


}