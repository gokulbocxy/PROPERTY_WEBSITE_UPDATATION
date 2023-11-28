package com.bocxy.Property.Controller;

import com.bocxy.Property.Entity.Allottee;
import com.bocxy.Property.Entity.OnHoldUser;
import com.bocxy.Property.Entity.RegisteredUser;
import com.bocxy.Property.JwtResponse;
import com.bocxy.Property.JwtUtils;
import com.bocxy.Property.Loggedinimp;
import com.bocxy.Property.Model.SignupRequest;
import com.bocxy.Property.Repository.OnHoldUserRepo;
import com.bocxy.Property.Repository.RegisteredUserRepo;
import com.bocxy.Property.Service.PropertyServiceImpl;
import com.bocxy.Property.UserDetailsImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api")
@Validated // Enable method-level validation
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;
  @Autowired
  RegisteredUserRepo userRepository;
  @Autowired
  OnHoldUserRepo onholdRepository;

    @Autowired
    static
    OnHoldUserRepo onholdRepositorystatic;
  @Autowired
  PasswordEncoder encoder;
  @Autowired
  JwtUtils jwtUtils;

    @Autowired
    private JavaMailSender javaMailSender;



    @CrossOrigin(origins = "http://localhost:4200")
  @RequestMapping(method = RequestMethod.POST, path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> authenticateUser(@RequestBody com.bocxy.Property.Model.LoginRequest loginRequest) throws Exception {
    String username=loginRequest.getUsername();
    String msg="";
    Loggedinimp loggedin=new Loggedinimp();
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()  ));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    msg=loggedin.Updateloggedin(username);

    return ResponseEntity.ok(new JwtResponse(jwt,
            userDetails.getId(),userDetails.getFirstname() , userDetails.getLastname(),
            userDetails.getEmail(),userDetails.getPhonenumber(),userDetails.getLoggedin(),userDetails.getUsername()));


  }

 
  @PostMapping("/register")
  public ResponseEntity<?> registerUser( @RequestBody SignupRequest signUpRequest) {
      if (userRepository.existsByEmail(signUpRequest.getEmail())) {
          return ResponseEntity
              .badRequest()
              .body("Error: Email is already in use!");
      }

      // Create new user's account
      RegisteredUser user = new RegisteredUser(signUpRequest.getUsername(),
          signUpRequest.getEmail(),
          encoder.encode(signUpRequest.getPassword()), signUpRequest.getFirstname(),
              signUpRequest.getLastname(),signUpRequest.getPhonenumber());
      userRepository.save(user);

      List<OnHoldUser> userr = onholdRepository.findByEmail(signUpRequest.getEmail());
      String otp = AuthController.OtpGenerator.generateOtp(6);
      for (OnHoldUser users : userr) {
          users.setOtp(otp);
          onholdRepository.delete(users);

      }

      return ResponseEntity.ok(("User registered successfully!"));
  }

    @PostMapping("/onholdregister")
    public ResponseEntity<?> onholdregisterUser( @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }
        // Create new user's account
        OnHoldUser user = new OnHoldUser(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()), signUpRequest.getFirstname(),
                signUpRequest.getLastname(),signUpRequest.getPhonenumber());
        onholdRepository.save(user);
        this.sendOtpToUser(signUpRequest.getEmail());
        return ResponseEntity.ok(("Please check for OTP!"));
    }


    public class OtpGenerator {
        public static String generateOtp(int length) {
            return RandomStringUtils.randomNumeric(length);
        }
    }

    public void sendOtpToUser(String email) {

        List<OnHoldUser> user = onholdRepository.findByEmail(email);
        String otp = AuthController.OtpGenerator.generateOtp(6);
        for (OnHoldUser users : user) {
            users.setOtp(otp);
            onholdRepository.save(users);
            sendOtpEmail(users.getEmail(), otp);
        }
    }

    public void sendOtpEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code for TNHB-Property Booking");
        message.setText("Hi! Your OTP code is: " + otp);

        javaMailSender.send(message);
    }



    //Verify OTP

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String otp = requestBody.get("otp");
        List<OnHoldUser> response = verifyOtpAndGetAllottee(email, otp);

        if (!response.isEmpty()) {

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP.");
        }
    }

    public List<OnHoldUser> verifyOtpAndGetAllottee(String email, String otp) {
        List<OnHoldUser> users = onholdRepository.findByEmail(email);
        List<OnHoldUser> responseList = new ArrayList<>();
        for (OnHoldUser user : users) {
            if (user.getOtp().equals(otp)) {
                responseList.add(user);
            }
        }
        return responseList;
    }



}