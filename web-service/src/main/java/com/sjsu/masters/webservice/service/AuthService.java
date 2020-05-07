package com.sjsu.masters.webservice.service;


import com.sjsu.masters.webservice.contract.AuthResponse;
import com.sjsu.masters.webservice.contract.ErrorResponse;
import com.sjsu.masters.webservice.dao.UserDao;
import com.sjsu.masters.webservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthResponse authResponse;

    @Autowired
    private ErrorResponse errorResponse;


    public static final Pattern VALID_EMAIL_ADDRESS_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> login(String email, String password)
    {
        try {
            User user = userDao.findByEmail(email);

            String userPass = user.getPassword();

            if(userPass.equals(password))
            {
                authResponse = new AuthResponse(user.getId(),user.getFirstName(),user.getLastName(),user.getEmail(),user.getPhone());
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(authResponse);
            }

            errorResponse = new ErrorResponse(400, "Bad Request", "Invalid username or password!");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> signUp(String firstName, String lastName, String email, String phone, String password)
    {
        try {

            User existingUser = userDao.findByEmail(email);

            if(existingUser!=null)
            {
                errorResponse = new ErrorResponse(409, "Duplicate Resource", "Account for email id already exists!");
                return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
            }

            String emailDomain = email.substring(email.lastIndexOf("@") + 1).toLowerCase();

            User newUser = new User(firstName,lastName,email,phone,password);

            User addedUser = userDao.store(newUser);

            authResponse = new AuthResponse(addedUser.getId(),firstName,lastName,email,phone);
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(authResponse);

        } catch (Exception e) {
            e.printStackTrace();
            errorResponse = new ErrorResponse(500, "Bad Request", "Error while saving user");
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(errorResponse);
        }
    }

    public static boolean isValidEmail(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_PATTERN.matcher(email);
        return matcher.find();
    }


}
