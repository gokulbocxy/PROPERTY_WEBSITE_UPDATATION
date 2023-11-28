package com.bocxy.Property;

import com.bocxy.Property.Entity.RegisteredUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Objects;

@Data
public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private final Long id;
  private final String firstname;
  private final String lastname;
  private final String email;
  private String loggedin;
  private final String username;
  private String phonenumber;


  @JsonIgnore
  private final String password;


  public UserDetailsImpl(Long id, String firstname, String lastname, String email, String loggedin, String username,String phonenumber, String password) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.loggedin = loggedin;
    this.username = username;
    this.password = password;
    this.phonenumber = phonenumber;
  }

  public static UserDetailsImpl build(RegisteredUser user) {


    return new UserDetailsImpl(
            user.getId(),
            user.getFirstname(),
            user.getLastname(),
            user.getEmail(),
            user.getLoggedin(),
            user.getUsername(),user.getPhonenumber(), user.getPassword()
    );
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }




  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserDetailsImpl)) return false;
    UserDetailsImpl that = (UserDetailsImpl) o;
    return username.equals(that.username) &&

            password.equals(that.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password);
  }

}