package com.bocxy.Property.Model;

import lombok.Data;

@Data
public class LoginRequest {


  private String username;
  private String password;


  public LoginRequest(String username, String password) {
    super();
    this.username = username;
    this.password = password;


  }




}
