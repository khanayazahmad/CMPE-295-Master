package com.sjsu.masters.webservice.controller;

import com.sjsu.masters.webservice.contract.ErrorResponse;
import com.sjsu.masters.webservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private ErrorResponse errorResponse;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody Map<String, Object> body)
    {
        try {
//            System.out.println("Request received in login.");
            String username = (String) body.get("username");
            String password = (String) body.get("password");

            if (!username.trim().equals("") && !password.trim().equals(" "))
                return authService.login(username, password);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        errorResponse = new ErrorResponse(400, "Bad Request", "Invalid username or password!");
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<?> signUp(@RequestBody Map<String,Object> body)
    {
        try {
            String firstName = (String) body.get("firstName");
            String lastName = (String) body.get("lastName");
            String email = (String) body.get("email");
            String phone = (String) body.get("phone");
            String password = (String) body.get("password");

            if(!firstName.equals("") && !email.equals("") && !password.equals(""))
                return authService.signUp(firstName,lastName,email,phone,password);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        errorResponse = new ErrorResponse(400, "Server Error", "Error while saving user.");
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
    }

}
